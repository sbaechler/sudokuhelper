package ch.zahw.students.sudokuhelper.capture;

import org.opencv.core.Mat;

public class FieldCandidate {
    
    private int row;
    private int column;
    private Mat image;
    private int content;
    
    public FieldCandidate() {}
    
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }
       
    public Mat getImage() {
        return image;
    }

    public void setImage(Mat image) {
        this.image = image;
    }

    public void setField(int row, int column, int content) {
        this.row = row;
        this.column = column;
        this.content = content;
    }
}
