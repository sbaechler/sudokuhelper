package ch.zahw.students.sudokuhelper;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import android.util.Log;

/**
 * This class processes the image and notifies the activity if a Sudoku
 * has been found.
 * @author simon
 *
 */
public class SudokuTracker {
    private static final String TAG = "SudokuHelper::SudokuTracker";
    
    private static final int VIEW_MODE_THRESH = 3; 
    private Mat mIntermediateMat;
    private Mat mRgba;

    public SudokuTracker(int width, int height) {
        Log.d(TAG, "Sudoku Tracker initialized: " + width + "x" + height);
        mIntermediateMat = new Mat(height, width, CvType.CV_8UC4);
        mRgba = new Mat(height, width, CvType.CV_8UC4);
    }
    
    public Mat detect(Mat imageGray) {
        int maxValue = 255; 
        int blockSize = 61; 
        int meanOffset = 15; 
        Imgproc.adaptiveThreshold( 
            imageGray, 
            mIntermediateMat, 
            maxValue, 
            Imgproc.ADAPTIVE_THRESH_MEAN_C, 
            Imgproc.THRESH_BINARY, 
            blockSize, 
            meanOffset 
        ); 
        Imgproc.cvtColor(mIntermediateMat, mRgba, Imgproc.COLOR_GRAY2RGBA, 4 ); 
        return mRgba;
    }
    
    

}
