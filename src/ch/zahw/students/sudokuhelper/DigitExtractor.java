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



/**
 * This class takes care of extracting the digits from the sudoku and
 * passing them to the OCR class.
 * @author simon
 *
 */
public class DigitExtractor {

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
        borderWidth = sudokuSize/200;
        fieldSize = (int) Math.floor(sudokuSize/9);
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
        Imgproc.findContours(field, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
        double maxArea = -1;
        int maxAreaIdx = -1;
        for (int idx = 0; idx < contours.size(); idx++) {
            Mat contour = contours.get(idx);
            double contourarea = Imgproc.contourArea(contour);
            if (contourarea > maxArea) {
                maxArea = contourarea;
                maxAreaIdx = idx;
            }
        }
        Rect rect = Imgproc.boundingRect(contours.get(maxAreaIdx));
        return rect;
    }

    /**
     * A helper method used during development and debugging.
     */
    public void drawBoundingBoxes(Mat field, int row, int col){
        Rect rect = getBoundingBoxFor(field);
        int x = rect.x + col*fieldSize;
        int y = rect.y + row*fieldSize;
        Core.rectangle(field, new Point(x, y), 
                new Point(x+rect.width, y+rect.height),
                new Scalar(255), 3);
    }
    
}
