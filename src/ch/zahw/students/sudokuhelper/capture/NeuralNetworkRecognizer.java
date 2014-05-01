package ch.zahw.students.sudokuhelper.capture;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.imgproc.Imgproc;
import org.opencv.ml.CvANN_MLP;


import android.content.Context;
import android.util.Log;


public class NeuralNetworkRecognizer implements Recognizer {
    private static final String TAG = "SudokuHelper::NeuralNetworkRecognizer";
    private static final int ATTRIBUTES = 256; // numbers of pixels per sample
    private static final int LABELS = 10; // number of distinct labels
    
    private Context context;
    private CvANN_MLP nnetwork;
    // private int debug = 0;
    private Mat scaled;
    
    
    public NeuralNetworkRecognizer(Context applicationContext) {
        this.context = applicationContext;
        this.scaled = new Mat(16,16, CvType.CV_8UC1);
        this.nnetwork = new CvANN_MLP();

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
    public int[] recognize(Mat candidate) {
        Mat classOut = new MatOfDouble();
        int maxIndex = 0;
        int secondaryIndex = 0;
        Mat linearized = preprocess(candidate);
        nnetwork.predict(linearized, classOut);
        double value;
        double maxValue = classOut.get(0,0)[0];
        double secondaryValue = classOut.get(0,0)[0];
        
        for(int i=1; i<LABELS; i++) {
            value = classOut.get(0, i)[0];
            if(value > maxValue){
                secondaryValue = maxValue;
                maxValue = value;
                secondaryIndex = maxIndex;
                maxIndex = i;
            }
        }        

        // TODO: Implement learning and train better.
        // The algorithm has issues recognizing the 7.
        if(maxIndex == 1) { 
            secondaryIndex = 7; 
        }
        
        Log.v(TAG, "Found label: " + maxIndex + " (" + maxValue + ")" + 
                " 2nd best: " + secondaryIndex + "(" + secondaryValue + ")");
        
        return new int[]{maxIndex, secondaryIndex};
    }
    
    @Override
    public void regognize(List<FieldCandidate> candidates) {
        for(FieldCandidate candidate: candidates){
            candidate.setContent(recognize(candidate.getImage()));
        }
    }

    
    
    /**
     * Preprocessing helper method.
     * @param candidate - input Mat, actual size.
     * @return 1x256 Pixel Mat to be sent to the neural network.
     */
    public Mat preprocess(Mat candidate){
        Mat reshaped = Mat.ones(1, ATTRIBUTES, CvType.CV_64F);
        double[] result = new double[ATTRIBUTES];

        // normalize and invert the matrix
        Imgproc.resize(candidate, scaled, scaled.size());
        Imgproc.adaptiveThreshold( 
                scaled, 
                scaled, 
                1, // maxValue
                Imgproc.ADAPTIVE_THRESH_MEAN_C, 
                Imgproc.THRESH_BINARY_INV, 
                9, // blockSize
                15 // meanOffset
            );
        // create a linear array from the 2-dimensional data.
        for(int y=0; y<16; y++) {
            for (int x=0; x<16; x++){
                result[y*16 + x] = scaled.get(y,x)[0];
            }
        }
        reshaped.put(0, 0, result);
//        if(debug < 1){
//            Log.v(TAG, "Scaled: " + scaled.dump());
//            Log.v(TAG, "Reshaped: " + reshaped.dump());
//            debug++;
//        }
        return reshaped;
    }

}
