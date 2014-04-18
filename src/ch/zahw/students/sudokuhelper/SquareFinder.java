package ch.zahw.students.sudokuhelper;

// import java.util.Arrays;

import java.util.ArrayList;
import java.util.List;

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
    private int width;
    private int height;
    int centerX;
    int centerY;
    // CSS style array of lines: top, right, bottom, left
    private double[][] edges = new double[4][4];
    
    SquareFinder(Mat lines, int width, int height) throws NoSudokuFoundException {
        int cols = lines.cols();
        if (cols < 4) {
            throw new NoSudokuFoundException("Not enough lines found");
        }
        horizontalLines = new double[cols][4];
        verticalLines = new double[cols][4];
        Log.v(TAG, "Lines size: " + lines.cols());
        // split up the lines matrix into horizontal and vertical lines
        for (int x = 0; x < lines.cols(); x++) 
        {
              double[] vec = lines.get(0, x);
              if(!(vec==null) && (Math.abs(vec[0]-vec[2]) > Math.abs(vec[1]-vec[3]))) {
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
        centerX = width / 2;
        centerY = width / 2;
    }
    
    // helper constructors used for testing without OpenCV
    public SquareFinder(double[][] horizontalLines, double[][] verticalLines) throws NoSudokuFoundException {
        this.horizontalLines = horizontalLines;
        this.verticalLines = verticalLines;
        nextH = horizontalLines.length;
        nextV = verticalLines.length;
        centerX = 360;
        centerY = 640;
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
                        // A is between the left and right edge point.
                        // only count it if it is above the screen center.
                        if(hy1 < centerY && hy2 < centerY) {
                            upperHit++;
                        }
                    } else if ((hy1<=by+t && by<=hy2+t) || (hy1+t>=by && by+t>=hy2)) {
                        if(hy1 > centerY && hy2 > centerY) {
                            lowerHit++;
                        }
                    }
                } else { // B is above A
                    if ((hy1<=by+t && by<=hy2+t) || (hy1+t>=by && by+t>=hy2)) {
                        if(hy1 < centerY && hy2 < centerY) {
                            upperHit++;
                        }
                    }  else if ((hy1<=ay+t && ay<=hy2+t) || (hy1+t>=ay && ay+t>=hy2)) {
                        if(hy1 > centerY && hy2 > centerY) {
                            lowerHit++;
                        }
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
        // Log.v(TAG, "Best upper hit: " + bestUpperHit + ", best lower: " + bestLowerHit);
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
                        if(vx1 < centerX && vx2 < centerX) {
                            leftHit++;
                        }
                    } else if ((vx1<=bx&& bx<=vx2) || (vx1>=bx && bx>=vx2)){
                        if(vx1 > centerX && vx2 > centerX) {
                            rightHit++;
                        }
                    }
                } else {
                    if ((vx1<=bx && bx<=vx2) || (vx1>=bx && bx>=vx2)){
                        if(vx1 < centerX && vx2 < centerX) {
                            leftHit++;
                        }
                    } else if ((vx1<=ax && ax<=vx2) || (vx1>=ax && ax>=vx2)){
                        if(vx1 > centerX && vx2 > centerX) {
                            rightHit++;
                        }
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
    
    public List<Point> drawEdges(Mat mRgba) throws NoSudokuFoundException{
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
        
        // Sanity check: The upper edge is the shortest because it is farthest
        // from the lens. So the area of the square found must be larger than 
        // the square of the upper line.

        

        // draw the points cyan.
        Core.line(mRgba, tl, tl, new Scalar(20,255,255), 3);
        Core.line(mRgba, tr, tr, new Scalar(20,255,255), 3);
        Core.line(mRgba, bl, bl, new Scalar(20,255,255), 3);
        Core.line(mRgba, br, br, new Scalar(20,255,255), 3);
        
        if(diff * 10 > a || diff * 10 > b) {
            throw new NoSudokuFoundException("Structure is not a square.");
        }
        List<Point> points = new ArrayList<Point>();
        points.add(bl);
        points.add(br);
        points.add(tr);
        points.add(tl);
        return points;
    }
    
    /**
     * This methods finds the corner points of the sudoku
     * by finding the intersection points of the edge lines.
     * It uses simple vector algebra: v1=P1+u(P2-P1) ∩ v2=P2+v(P4-P3) 
     */
    public Point findCornerPoint(double[] v1, double[] v2){
        // calculate the matrix A and vector b from the coordinates 
        // of the edge lines
        // Log.d(TAG, "Finding intersection: "+Arrays.toString(v1)+Arrays.toString(v2));
        Mat x = Mat.zeros(2, 1, CvType.CV_64F);  // result
        Mat A = new Mat(2, 2, CvType.CV_64F);
        Mat b = new Mat(2, 1, CvType.CV_64F);
        double x1, y1;
        
        A.put(0, 0, v1[2]-v1[0]);
        A.put(0, 1, -v2[2]+v2[0]);
        A.put(1, 0, v1[3]-v1[1]);
        A.put(1, 1, -v2[3]+v2[1]);
        b.put(0, 0, v2[0]-v1[0]);
        b.put(1, 0, v2[1]-v1[1]);
        // solve the matrix equation
        // OpenCV Java does not support the * operator on matrices.
        Core.gemm(A.inv(), b,  1, new Mat(), 0, x, 0 );
        double u = x.get(0,0)[0];
        double v = x.get(1,0)[0];
//        Log.d(TAG, "scalars u: " + u + " v: " + v);
//        Log.d(TAG, "Matrix A: "+A.dump());
//        Log.d(TAG, "b: " + b.dump());
        // insert the scalar uv into the original equation
        if(u>0 || v<0) { // use the positive value due to better precision
            x1 = Math.round(v1[0] + u*(v1[2]-v1[0]));
            y1 = Math.round(v1[1] + u*(v1[3]-v1[1]));    
        } else {
            x1 = Math.round(v2[0] + v*(v2[2]-v2[0]));
            y1 = Math.round(v2[1] + v*(v2[3]-v2[1]));
        }
        Log.v(TAG, "Point (" + x1 + ", " + y1 + ")");
        A.release(); 
        b.release();
        x.release();
        return new Point(x1, y1);
    }
    
    

}
