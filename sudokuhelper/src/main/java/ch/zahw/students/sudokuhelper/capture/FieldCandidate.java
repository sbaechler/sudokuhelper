package ch.zahw.students.sudokuhelper.capture;

import org.opencv.core.Mat;

/**
 * The FieldCandidate class is used during the image recognition phase and
 * represents a Sudoku field.
 * It either carries an image (Mat) with the digit or the values
 * of the recognized number.
 * @author simon
 *
 */
public class FieldCandidate {
    
    private int row;
    private int column;
    private Mat image;
    private int primary;
    private int secondary;
    
    /**
     * The class must be passed the extracted image as a Mat.
     * @param row - Sudoku row
     * @param column - Sudoku column
     * @param image - Mat CV_8UC1 containing a white number on black background.
     */
    public FieldCandidate(int row, int column, Mat image) {
        this.row = row;
        this.column = column;
        this.image = image;
    }
    
    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
         
    public int getPrimary() {
        return primary;
    }

    public int getSecondary() {
        return secondary;
    }

    public Mat getImage() {
        return image;
    }

    public void setContent(int[] content) {
        this.primary = content[0];
        this.secondary = content[1];
        // release the Mat
        this.image = null;
    }
    
    @Override
    public String toString(){
        return ""+row+","+column+":"+primary+"-"+secondary;
    }
    
}
