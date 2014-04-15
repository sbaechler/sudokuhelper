package ch.zahw.students.sudokuhelper;

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
    
    private Mat mIntermediateMat;
    private Mat mRgba;
    private Mat lines;
    private int width;
    private int height;
    final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
    

    public SudokuTracker(int width, int height) {
        Log.v(TAG, "Sudoku Tracker initialized: " + width + "x" + height);
        mIntermediateMat = new Mat(height, width, CvType.CV_8UC1);
        mRgba = new Mat(height, width, CvType.CV_8UC4);
        lines = new Mat();
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
        try {
            markLines();  // Throws NoSudokuFoundException
            // TODO: Add another sanity check after the matrix transformation.
            tg.startTone(ToneGenerator.TONE_PROP_BEEP);
            // TODO: throw new SudokuFoundException and change activities
        } catch (NoSudokuFoundException e) {
            // Log.v(TAG, e.getMessage());
            System.gc();
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
    
    
    private void markLines() throws NoSudokuFoundException{
        
        int threshold = 60;
        int minLineSize = 250;
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
