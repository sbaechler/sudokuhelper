package ch.zahw.students.sudokuhelper.capture;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.util.Log;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.utils.Converters;



/**
 * This class processes the image and notifies the activity if a Sudoku
 * has been found.
 * Make sure OpenCV has initialized before instantiating this class.
 * (instantiate it in the onManagerConnected callback)
 * @author simon
 *
 */
public class SudokuTracker {
    private static final String TAG = "SudokuHelper::SudokuTracker";
    private static final int RESULT_SIZE = 540;
    
    private Mat mIntermediateMat;
    private Mat mRgba;
    private Mat lines;
    private Mat transform;
    private Mat mStraight;
    private int width;
    private int height;
    private boolean hasFoundCandidate = false;
    private DigitExtractor digitExtractor;
    final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
    

    public SudokuTracker(int width, int height, Context context) {
        Log.v(TAG, "Sudoku Tracker initialized: " + width + "x" + height);
        mIntermediateMat = new Mat(height, width, CvType.CV_8UC1);
        mRgba = new Mat(height, width, CvType.CV_8UC4);
        mStraight = new Mat(RESULT_SIZE, RESULT_SIZE, CvType.CV_8UC1);
//        digitExtractor = new DigitExtractor(context);
//        digitExtractor.setSource(mStraight);
        lines = new Mat();
        this.width = width;
        this.height = height;
    }
    
    public Mat getMStraight() {
        return mStraight;
    }
    
    /**
     * This is the main method called by the app.
     * @param imageGray - Grayscale Mat.
     * @return RGBA Mat
     */
    public Mat detect(Mat imageGray) {
        threshold(imageGray);
        Imgproc.cvtColor(mIntermediateMat, mRgba, Imgproc.COLOR_GRAY2RGBA, 4 ); 
        if (!hasFoundCandidate){
            try {
                Imgproc.medianBlur(mIntermediateMat, mIntermediateMat, 3);
                List<Point> points = markLines();  // Throws NoSudokuFoundException
                // TODO: Add another sanity check after the matrix transformation.
                tg.startTone(ToneGenerator.TONE_PROP_BEEP);
                hasFoundCandidate = true;
                // TODO: Start this in another thread.
                perspectiveTransform(points); // fills mStraight
                
                // TODO: throw new SudokuFoundException and change activities
            } catch (NoSudokuFoundException e) {
                // Log.v(TAG, e.getMessage());
                System.gc();
                hasFoundCandidate = false;
            }
        }
        return mRgba;
    }
    
    private void threshold(Mat imageGray){
        int maxValue = 255; 
        int blockSize = 61; 
        int meanOffset = 15; 
        Imgproc.adaptiveThreshold( 
            imageGray, 
            mIntermediateMat, 
            maxValue, 
            Imgproc.ADAPTIVE_THRESH_MEAN_C, 
            Imgproc.THRESH_BINARY_INV, 
            blockSize, 
            meanOffset 
        );
        // dillate the result to close gaps that might have occured.
        Imgproc.dilate(mIntermediateMat, mIntermediateMat, 
            Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, new Size(3, 3))
        );
    }
    
    
    private List<Point> markLines() throws NoSudokuFoundException{
        
        int threshold = 60;
        int minLineSize = 250;
        int lineGap = 10;
        
        Imgproc.HoughLinesP(mIntermediateMat, lines, 1, Math.PI/90, threshold, minLineSize, lineGap);

        SquareFinder finder = new SquareFinder(lines, width, height);
        
        for (int x = 0; x < lines.cols(); x++) 
        {
              double[] vec = lines.get(0, x);
              double x1 = vec[0], 
                     y1 = vec[1],
                     x2 = vec[2],
                     y2 = vec[3];
              Point start = new Point(x1, y1);
              Point end = new Point(x2, y2);

              Core.line(mRgba, start, end, new Scalar(0,255,0), 3);           
        }
        List<Point> points = finder.drawEdges(mRgba);
        finder = null;
        return points;
    }
    
    private void perspectiveTransform(List<Point> points){
        Mat startM = Converters.vector_Point2f_to_Mat(points);
        Mat endM = Mat.zeros(4,2, CvType.CV_32F);
        // Mat tempM = new Mat();
        endM.put(0,1, RESULT_SIZE);
        endM.put(1,0, RESULT_SIZE);
        endM.put(1,1, RESULT_SIZE);
        endM.put(2,0, RESULT_SIZE);       
//        Log.v(TAG, "Start Matrix: " + startM.dump());
//        Log.v(TAG, "End Matrix: " + endM.dump());
        Mat transformMat = Imgproc.getPerspectiveTransform(startM, endM);
        startM.release();
        endM.release();
        Imgproc.warpPerspective(mIntermediateMat, 
                mStraight,
                transformMat,
                new Size(RESULT_SIZE, RESULT_SIZE), 
                Imgproc.INTER_LINEAR);
        transformMat.release();
        Imgproc.threshold(mStraight, mStraight, 128, 255, Imgproc.THRESH_BINARY);
        // inverse the result, so text is black on white.
        // Core.bitwise_not(tempM, mStraight);
        // tempM.release();
    }


    
    
    

}
