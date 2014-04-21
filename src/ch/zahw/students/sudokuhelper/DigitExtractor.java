package ch.zahw.students.sudokuhelper;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import android.util.Log;



/**
 * This class takes care of extracting the digits from the sudoku and
 * passing them to the OCR class.
 * @author simon
 *
 */
public class DigitExtractor {
    private static final String TAG = "SudokuHelper::DigitExtractor";
    private static final double MIN_ASPECT_RATIO = 0.3;
    private Mat source;
    private int sudokuSize;
    private int fieldSize;
    private int borderWidth;
    
    
    public DigitExtractor() {
        // TODO Auto-generated constructor stub
    }
    
    /**
     * The default constructor. Takes the straightened Sudoku Mat as argument.
     * @param source
     */
    public DigitExtractor(Mat source){
        this.source = source;
        sudokuSize = source.cols();
        if(source.rows() != sudokuSize) {
            throw new IllegalArgumentException("Source Mat needs to be square");
        }
        borderWidth = sudokuSize/80;
        fieldSize = (int) Math.floor(sudokuSize/9);
    }
    
    public void extractDigits(){
        for (int row=0; row<9; row++){
            for (int col=0; col<9; col++) {
                Mat field = getFieldAt(row, col);
                drawBoundingBox(field, row, col);
            }
        }
    }
    
    
    /**
     * This method returns a submatrix which is a pointer to the source matrix.
     * @param row - Sudoku row (0-index)
     * @param col - Sudoku column (0-index)
     * @return Sub-Matrix containing the number
     */
    public Mat getFieldAt(int row, int col){
        int x, y, size;
        x = (fieldSize * col) + borderWidth;
        y = (fieldSize * row) + borderWidth;
        size = fieldSize - 2 * borderWidth;
        Rect selection = new Rect(x, y, size, size);
        return source.submat(selection);
    }
    
    /**
     * This method returns the bounding box for the given character.
     * @param field - the Sudoku field
     * @return the bounding box Rect.
     */
    public Rect getBoundingBoxFor(Mat field) {
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat tempField = new Mat(field.rows(), field.cols(), field.type());
        field.copyTo(tempField);
        Imgproc.findContours(tempField, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        // Log.v(TAG, "Contours size: " + contours.size());
        double maxArea = -1, 
               contourarea;
        double limitArea = field.cols() * field.rows() * 8/10;
        int maxAreaIdx = -1;
        for (int idx = 0; idx < contours.size(); idx++) {
            Mat contour = contours.get(idx);
            contourarea = Imgproc.contourArea(contour);
            if (contourarea > maxArea) {
                maxArea = contourarea;
                maxAreaIdx = idx;
            } else if (contourarea > limitArea){
                // if the box is larger than 80% of the field, it must be empty.
                return null;
            }
        }
        tempField.release();
        if (maxArea == -1){
            return null;
        }
        
        Rect rect = Imgproc.boundingRect(contours.get(maxAreaIdx));
        // check the aspect ratio of the found area. If it is less than 1:3,
        // it is invalid.
        if((double)rect.width/rect.height < MIN_ASPECT_RATIO || (double)rect.height/rect.width < MIN_ASPECT_RATIO){
            // Log.v(TAG, "Bad aspect ratio: " + ((double)rect.width/rect.height) + ", " + ((double)rect.height/rect.width));
            return null;
        }
        
        return rect;
    }

    /**
     * A helper method used during development and debugging.
     */
    public void drawBoundingBox(Mat field, int row, int col){
        Rect rect = getBoundingBoxFor(field);
        if (rect != null){
            int x = rect.x + col*fieldSize + borderWidth;
            int y = rect.y + row*fieldSize + borderWidth;
            // Log.v(TAG, "Bounding box: " + x + ", " + y + " w:" + rect.width + " h: " + rect.height);
            Core.rectangle(source, new Point(x, y), 
                    new Point(x+rect.width, y+rect.height),
                    new Scalar(192));
        }

    }
    
}
