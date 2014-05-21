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
import org.opencv.core.Rect;
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
 */
public class SudokuTracker {
    private static final String TAG = "SudokuHelper::SudokuTracker";
    private static final int RESULT_SIZE = 480;
    private static final int CW = 0;  // clockwise
    private static final int CCW = 1;  // counter clockwise
    
    private Mat mIntermediateMat;
    private Mat mIntermediate2Mat;
    private Mat mRgba;
    private Mat mRgbaSub;
    private Mat lines;
    private Mat transform;
    private Mat inverseTransform;
    private Mat mStraight;
    private int width;
    private int height;
    private Rect roi;
    private boolean foundCandidate = false;
    private DigitExtractor digitExtractor;
    final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
    

    public SudokuTracker(int width, int height, Context context) {
        if(height > width) {
            throw new IllegalArgumentException("Height is bigger than width");
        }
        Log.v(TAG, "Sudoku Tracker initialized: " + width + "x" + height);
        mIntermediateMat = new Mat(height, height, CvType.CV_8UC1);
        mIntermediate2Mat = new Mat(height, height, CvType.CV_8UC1);
        mRgba = new Mat(height, width, CvType.CV_8UC4);
        mStraight = new Mat(RESULT_SIZE, RESULT_SIZE, CvType.CV_8UC1);
//        digitExtractor = new DigitExtractor(context);
//        digitExtractor.setSource(mStraight);
        lines = new Mat();
        this.width = width;
        this.height = height;
        this.roi = new Rect(new Point((width-height)/2, 0), mIntermediateMat.size());
        mRgbaSub = mRgba.submat(roi);
    }
    
    public Mat getMStraight() {
        return mStraight;
    }
    public Mat getInverseTransformMat(){
        return inverseTransform;
    }
    
    public boolean hasFoundCandidate(){
        return foundCandidate;
    }
    public void setFoundCandidate(boolean candidateOk){
        foundCandidate = candidateOk;
    }
    public Rect getRoi(){
        return roi;
    }
    public Mat onlyRoi(Mat input){
        Log.v(TAG, "Roi: " + roi.x + ", " + roi.y + " " + roi.width  + " " +roi.height);
        Log.v(TAG, "Input Mat: "+ input.cols() + "x" + input.rows());
        return input.submat(roi);
    }
    
    /**
     * This is the main method called by the app on every preview image. 
     * It has to rotate the input image by 90° to process it
     * since OpenCV images always have landscape orientation.
     * If a candidate was found the numbers are extracted. If any of the extracting
     * methods fail, the current image is discarded.
     * @param imageGray - Grayscale Mat.
     * @return RGBA Mat, required by the onCameraFrame method.
     */
    public Mat detect(Mat imageGray) {
       
        Log.v(TAG, "Detecting input Mat: " + imageGray.cols() + "x" + imageGray.rows());
        // discard all the grayscale value and adaptively threshold the image into
        // a pure black & white image.
        threshold(imageGray.submat(roi), mIntermediateMat);
        // rotate the image 90° CCW
        Core.flip(mIntermediateMat.t(), mIntermediateMat, CCW);

        // blur the image a bit to get rid of noise.
        Imgproc.medianBlur(mIntermediateMat, mIntermediateMat, 3);
        Imgproc.cvtColor(mIntermediateMat, mRgbaSub, Imgproc.COLOR_GRAY2RGBA, 4 ); 
        if (!foundCandidate){
            try {
                // make the lines a bit wider to close potential gaps.
                dilate(mIntermediateMat, mIntermediate2Mat);
                List<Point> points = markLines();  // Throws NoSudokuFoundException
                tg.startTone(ToneGenerator.TONE_PROP_BEEP);
                foundCandidate = true;
                perspectiveTransform(points); // fills mStraight
                
            } catch (NoSudokuFoundException e) {
                // This exception can be thrown any time. 
                // The current frame is no good, abort and continue with the next one.
                Log.v(TAG, e.getMessage());
                System.gc();
                foundCandidate = false;
            }
        }
        // rotate the middle part of the image image 90° CW so it appears correct.
        Core.flip(mRgbaSub.t(), mRgbaSub, CW);
        return mRgba;
    }
    
    /**
     * Turn the passed grayscale Mat into a binary image. 
     */
    private void threshold(Mat source, Mat dest){
        int maxValue = 255; 
        int blockSize = 61; 
        int meanOffset = 15; 
        Imgproc.adaptiveThreshold( 
            source, 
            dest, 
            maxValue, 
            Imgproc.ADAPTIVE_THRESH_MEAN_C, 
            Imgproc.THRESH_BINARY_INV, 
            blockSize, 
            meanOffset 
        );
    }
    
    private void dilate(Mat source, Mat dest){
        // dillate the result to close gaps that might have occured.
        Imgproc.dilate(source, dest, 
            Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, new Size(3, 3))
        );
    }
    
    /**
     * Find all the lines in the image using a Hough transform algorithm.
     * Use the SquareFinder class to extract the corner points and locate
     * the Sudoku.
     * This method also draws the green lines onto the display Mat.
     * @return a list of the four corner points.
     * @throws NoSudokuFoundException
     */
    private List<Point> markLines() throws NoSudokuFoundException{
        
        int threshold = 60;
        int minLineSize = 250;
        int lineGap = 10;
        
        Imgproc.HoughLinesP(mIntermediate2Mat, lines, 1, Math.PI/90, 
                threshold, minLineSize, lineGap);

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

              Core.line(mRgbaSub, start, end, new Scalar(0,255,0), 3);           
        }
        List<Point> points = finder.findCornerPoints(mRgbaSub);
        finder = null;
        return points;
    }
    
    /**
     * Make the sudoku in the frame perfectly straight.
     * The inverse transform matrix is set here as well to print the result
     * onto the original frame.
     * @param points - The four corner points
     */
    private void perspectiveTransform(List<Point> points){
        Mat startM = Converters.vector_Point2f_to_Mat(points);
        Mat endM = Mat.zeros(4,2, CvType.CV_32F);
        endM.put(0,1, RESULT_SIZE);
        endM.put(1,0, RESULT_SIZE);
        endM.put(1,1, RESULT_SIZE);
        endM.put(2,0, RESULT_SIZE);       
//        Log.v(TAG, "Start Matrix: " + startM.dump());
//        Log.v(TAG, "End Matrix: " + endM.dump());
        Mat transformMat = Imgproc.getPerspectiveTransform(startM, endM);
        inverseTransform = Imgproc.getPerspectiveTransform(endM, startM);
        startM.release();
        endM.release();
        Imgproc.warpPerspective(mIntermediateMat, 
                mStraight,
                transformMat,
                new Size(RESULT_SIZE, RESULT_SIZE), 
                Imgproc.INTER_CUBIC);
        transformMat.release();
        Imgproc.threshold(mStraight, mStraight, 128, 255, Imgproc.THRESH_BINARY);
    }


    
    
    

}
