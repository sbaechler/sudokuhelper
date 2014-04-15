package ch.zahw.students.sudokuhelper;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;

import android.util.Log;

/**
 * Helper class that finds the Sudoku from a collection of lines.
 * @author simon
 *
 */
public class SquareFinder {
    private static final String TAG = "SudokuHelper::SquareFinder";
    private static final int LINE_THRESHOLD = 1;
    private static final double INTERSECT_TOLERANCE = 2.0;

    private double[][] horizontalLines;
    private double[][] verticalLines;
    private int nextH = 0;
    private int nextV = 0;
    // CSS style array of lines: top, right, bottom, left
    private double[][] edges = new double[4][4];
    
    SquareFinder(Mat lines) throws NoSudokuFoundException {
        int cols = lines.cols();
        horizontalLines = new double[cols][4];
        verticalLines = new double[cols][4];
        Log.v(TAG, "Lines size: " + lines.cols());
        // split up the lines matrix into horizontal and vertical lines
        for (int x = 0; x < lines.cols(); x++) 
        {
              double[] vec = lines.get(0, x);
              if(Math.abs(vec[0]-vec[2]) > Math.abs(vec[1]-vec[3])) {
                  // line is horizontal
                  horizontalLines[nextH++] = vec.clone();
              } else {
                  verticalLines[nextV++] = vec.clone();
              }
        }
        if(nextH==0 || nextV==0) {
            throw new NoSudokuFoundException("No horizontal or vertical lines found");
        }
        Log.v(TAG, "Lines horizontal: " + nextH + ", vertical: " + nextV);
    }
    
    // helper constructors used for testing without OpenCV
    public SquareFinder(double[][] horizontalLines, double[][] verticalLines) throws NoSudokuFoundException {
        this.horizontalLines = horizontalLines;
        this.verticalLines = verticalLines;
        nextH = horizontalLines.length;
        nextV = verticalLines.length;
        if(nextH==0 || nextV==0) {
            throw new NoSudokuFoundException("No horizontal or vertical lines found");
        }
    }
    public SquareFinder(){}
    
    
    public double[][] getEdges(){
        return edges;
    }
    
    public void findUpperAndLowerEdge() throws NoSudokuFoundException{
        // iterate through all the horizontal lines
        int bestUpperHit = 0;
        int bestLowerHit = 0;
        double t = INTERSECT_TOLERANCE;
        for (int h=0; h<nextH; h++) {
            int upperHit = 0;
            int lowerHit = 0;
            // for every horizontal line, check if vertical edge points A and B 
            // are on that line.
            double[] hline = horizontalLines[h];
            double hy1 = hline[1],
                   hy2 = hline[3];
            for (int v=0; v<nextV; v++){
                double[] vec = verticalLines[v];
                double ay = vec[1],
                       by = vec[3];
                // if they are, count them
                if(ay < by) { // A is above B
                    if ((hy1<=ay+t && ay<=hy2+t) || (hy1+t>=ay && ay+t>=hy2)){
                        // A is between the left and right edge point
                        upperHit++;
                    } else if ((hy1<=by+t && by<=hy2+t) || (hy1+t>=by && by+t>=hy2)) {
                        lowerHit++;
                    }
                } else { // B is above A
                    if ((hy1<=by+t && by<=hy2+t) || (hy1+t>=by && by+t>=hy2)) {
                        upperHit++;
                    }  else if ((hy1<=ay+t && ay<=hy2+t) || (hy1+t>=ay && ay+t>=hy2)) {
                        lowerHit++;
                    }                  
                }
            }
            // take the lines with the highest counts
            if(upperHit > bestUpperHit) {
                edges[0] = horizontalLines[h];
                bestUpperHit = upperHit;
            } else if (lowerHit > bestLowerHit){
                edges[2] = horizontalLines[h];
                bestLowerHit = lowerHit;
            }
        }
        if(bestUpperHit < LINE_THRESHOLD || bestLowerHit < LINE_THRESHOLD){
            throw new NoSudokuFoundException("Number of upper or lower line ends below threshold.");
        }
        Log.v(TAG, "Best upper hit: " + bestUpperHit + ", best lower: " + bestLowerHit);
    }
    
    public void findLeftAndRightEdge() throws NoSudokuFoundException{
        // iterate through all the vertical lines
        int bestLeftHit = 0;
        int bestRightHit = 0;
        double t = INTERSECT_TOLERANCE;
        for (int v=0; v<nextV; v++) {
            int leftHit = 0;
            int rightHit = 0;
            // for every vertical line, check if horizontal edge points A B
            // are on that line.
            double[] vline = verticalLines[v];
            double vx1 = vline[0],
                   vx2 = vline[2];
            for (int h=0; h<nextH; h++){
                double[] vec = horizontalLines[h];
                double ax = vec[0],
                       ay = vec[1],
                       bx = vec[2],
                       by = vec[3];
                if(ax < bx) { // A is left of B
                    if ((vx1<=ax && ax<=vx2) || (vx1>=ax && ax>=vx2)){
                        // A is between the upper and lower edge point
                        // TODO: Sanity check if the point is vertically within the box.
                        leftHit++;
                    } else if ((vx1<=bx&& bx<=vx2) || (vx1>=bx && bx>=vx2)){
                        rightHit++;
                    }
                } else {
                    if ((vx1<=bx && bx<=vx2) || (vx1>=bx && bx>=vx2)){
                        leftHit++;
                    } else if ((vx1<=ax && ax<=vx2) || (vx1>=ax && ax>=vx2)){
                        rightHit++;
                    }
                }
            }
            // take the lines with the highest counts
            if(leftHit > bestLeftHit) {
                edges[3] = verticalLines[v];
                bestLeftHit = leftHit;
            } else if (rightHit > bestRightHit){
                edges[1] = verticalLines[v];
                bestRightHit = rightHit;
            }
        }
        if(bestLeftHit < LINE_THRESHOLD || bestRightHit < LINE_THRESHOLD){
            throw new NoSudokuFoundException("Number of left or right line ends below threshold.");
        }
        Log.v(TAG, "Best left hit: " + bestLeftHit + ", best right: " + bestRightHit);
    }
    
    public void drawEdges(Mat mRgba) throws NoSudokuFoundException{
        // draw edge points
        findUpperAndLowerEdge();
        findLeftAndRightEdge();
        
        for(int i=0; i<edges.length; i++){
            Point a = new Point(edges[i][0], edges[i][1]);
            Point b = new Point(edges[i][2], edges[i][3]);
            Core.line(mRgba, a, b, new Scalar(255, 0, 0), 3);
        }
        // Find the upper left corner point
        Point tl = findCornerPoint(edges[0], edges[3]);
        // Find the upper right corner point
        Point tr = findCornerPoint(edges[0], edges[1]);
        // Find the lower left corner point
        Point bl = findCornerPoint(edges[3], edges[2]);
        // Find the lower right corner point
        Point br = findCornerPoint(edges[2], edges[1]);
        
        // Sanity check: The corner points have to be correctly relative
        // to each other
        if(tl.x > tr.x || bl.x > br.x || tl.y > br.y || tr.y > br.y) {
            throw new NoSudokuFoundException("Points are not correctly aligned");
        }
        
        // Sanity check: The Sudoku has to be about square
        // calculate and compare the square distance of the diagonals
        // raise an exception if the difference is more than 10%.
        double a = Math.pow(tr.y-bl.y, 2) + Math.pow(tr.x-bl.x, 2);
        double b = Math.pow(tl.y-br.y, 2) + Math.pow(tl.x-br.x, 2);
        double diff = Math.abs(a-b);

        // draw the points cyan.
        Core.line(mRgba, tl, tl, new Scalar(20,255,255), 3);
        Core.line(mRgba, tr, tr, new Scalar(20,255,255), 3);
        Core.line(mRgba, bl, bl, new Scalar(20,255,255), 3);
        Core.line(mRgba, br, br, new Scalar(20,255,255), 3);
        
        if(diff * 10 > a || diff * 10 > b) {
            throw new NoSudokuFoundException("Structure is not a square.");
        }
    }
    
    /**
     * This methods finds the corner points of the sudoku
     * by finding the intersection points of the edge lines.
     * It uses simple vector algebra: v1=P1+u(P2-P1) ∩ v2=P2+v(P4-P3) 
     */
    public Point findCornerPoint(double[] v1, double[] v2){
        // calculate the matrix A and vector b from the coordinates 
        // of the edge lines
        Mat x = Mat.zeros(2, 1, CvType.CV_64F);  // result
        Mat A = new Mat(2, 2, CvType.CV_64F);
        Mat b = new Mat(2, 1, CvType.CV_64F);
        
        A.put(0, 0, v1[2]-v1[0]);
        A.put(0, 1, -v2[2]+v2[0]);
        A.put(1, 0, v1[3]-v1[1]);
        A.put(1, 1, -v2[3]+v2[1]);
        b.put(0, 0, v2[0]-v1[0]);
        b.put(1, 0, v2[1]-v1[3]);
        // solve the matrix equation
        // OpenCV Java does not support the * operator on matrices.
        Core.gemm(A.inv(), b,  1, new Mat(), 0, x, 0 );
        double u = x.get(0,0)[0];
        double v = x.get(1,0)[0];
        Log.v(TAG, "scalars u: " + u + " v: " + v);
        // insert the scalar uv into the original equation
        double x1 = Math.round(v1[0] + u*(v1[2]-v1[0]));
        double y1 = Math.round(v1[1] + v*(v1[3]-v1[1]));
        Log.v(TAG, "Point (" + x1 + ", " + y1 + ")");
        A = null; b = null; x = null;
        return new Point(x1, y1);
    }
    
    

}
