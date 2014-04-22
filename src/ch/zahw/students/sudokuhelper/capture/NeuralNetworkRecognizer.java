package ch.zahw.students.sudokuhelper.capture;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfFloat;
import org.opencv.imgproc.Imgproc;
import org.opencv.ml.CvANN_MLP;


import android.content.Context;
import android.util.Log;


public class NeuralNetworkRecognizer implements Recognizer {
    private static final String TAG = "SudokuHelper::NeuralNetworkRecognizer";
    private static final int ATTRIBUTES = 256; // numbers of pixels per sample
    private static final int LABELS = 10; // number of distinct labels
    
    private double minScore;
    private Context context;
    private CvANN_MLP nnetwork;
    
    
    public NeuralNetworkRecognizer(Context applicationContext) {
        this.context = applicationContext;
//        Mat layerSizes = new MatOfInt(new int[]{256,16,10});
//        layerSizes = layerSizes.t();
//        Log.v(TAG, "layerSizes: " + layerSizes.dump());
        nnetwork = new CvANN_MLP();

        // Hack for Android to allow loading a file into the neural network.
        File f = new File(context.getCacheDir(), "param.xml");
        if (!f.exists()) try {

            InputStream is = context.getAssets().open("param.xml");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            FileOutputStream fos = new FileOutputStream(f);
            fos.write(buffer);
            fos.close();
          } catch (Exception e) { throw new RuntimeException(e); }
        nnetwork.load(f.getAbsolutePath(), "DigitOCR");
    }

 
    @Override
    public int recognize(Mat candidate) {
        Mat classOut = new MatOfDouble();
        int maxIndex = 0;
        Mat linearized = preprocess(candidate);
        nnetwork.predict(linearized, classOut);
        double value;
        double maxValue = classOut.get(0,0)[0];
        
        for(int i=1; i<LABELS; i++) {
            value = classOut.get(0, i)[0];
            if(value > maxValue){
                maxValue = value;
                maxIndex = i;
            }
        }        
        Log.v(TAG, "Found label: " + maxIndex);
        return maxIndex;
    }
    
    @Override
    public void regognize(List<FieldCandidate> candidates) {
        // TODO Auto-generated method stub
        
    }

    
    
    /**
     * Preprocessing helper method.
     * @param candidate - input Mat, actual size.
     * @return 1x256 Pixel Mat to be sent to the neural network.
     */
    public Mat preprocess(Mat candidate){
        Mat scaled = new Mat(16,16, CvType.CV_8UC1);
        Imgproc.resize(candidate, scaled, scaled.size());
        double[] result = new double[ATTRIBUTES];
        // normalize and invert the matrix
        for(int y=0; y<16; y++) {
            for (int x=0; x<16; x++){
                double r = scaled.get(y,x)[0] > 0 ? 0.0 : 1.0;
                result[y*16+x] = r;
            }
        }
        Mat reshaped = new MatOfDouble(result);
        return reshaped.t();
    }

}
