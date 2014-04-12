package ch.zahw.students.sudokuhelper;

import java.util.Arrays;

import android.util.Log;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;



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
    
    private static final int VIEW_MODE_THRESH = 3; 
    private Mat mIntermediateMat;
    private Mat mRgba;
    private int width;
    private int height;
    private int count = 0;
    

    public SudokuTracker(int width, int height) {
        Log.v(TAG, "Sudoku Tracker initialized: " + width + "x" + height);
        mIntermediateMat = new Mat(height, width, CvType.CV_8UC1);
        mRgba = new Mat(height, width, CvType.CV_8UC4);
        this.width = width;
        this.height = height;
    }
    
    /**
     * This is the main method called by the app.
     * @param imageGray - Grayscale Mat.
     * @return RGBA Mat
     */
    public Mat detect(Mat imageGray) {
        threshold(imageGray);
        Imgproc.cvtColor(mIntermediateMat, mRgba, Imgproc.COLOR_GRAY2RGBA, 4 ); 
//        int result = floodFill();
//        Log.v(TAG, "Biggest blob: "+ result);
        markLines();
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
        Imgproc.dilate(mIntermediateMat, mIntermediateMat, Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, new Size(3, 3)));
    }
    
//    private int floodFill(){
//        Mat mask = new Mat(height+2, width+2, CvType.CV_8UC1);
//        Point seedPoint = new Point(width/2,height/2);
//        return Imgproc.floodFill(mIntermediateMat, mask, seedPoint, new Scalar(64, 64, 64));
//    }
    
    private Scalar getColor(){
        int color = count % 3;
        Scalar scalar = null;
        count ++;
        switch(color) {
            case 0: scalar = new Scalar(255,0,0);
                    break;
            case 1: scalar = new Scalar(0,255,0);
                    break;
            case 2: scalar = new Scalar(0, 0, 255);
                    break;
        }
        return scalar;
    }
    
    private void markLines(){
        Mat lines = new Mat();
        int threshold = 70;
        int minLineSize = 300;
        int lineGap = 10;
        
        Imgproc.HoughLinesP(mIntermediateMat, lines, 1, Math.PI/90, threshold, minLineSize, lineGap);

        SquareFinder finder = new SquareFinder(lines);
        
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

        finder.drawEdges(mRgba);
    }
    

    
    
    

}
