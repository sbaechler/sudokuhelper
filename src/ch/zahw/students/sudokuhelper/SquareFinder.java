package ch.zahw.students.sudokuhelper;

import org.opencv.core.Core;
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

    private double[][] horizontalLines;
    private double[][] verticalLines;
    private int nextH = 0;
    private int nextV = 0;
    private double[][] edges = new double[4][4];
    
    public SquareFinder(Mat lines) {
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
        Log.v(TAG, "Lines horizontal: " + nextH + ", vertical: " + nextV);
    }
    
    private void findUpperAndLowerEdge(){
        // iterate through all the horizontal lines
        int bestUpperHit = 0;
        int bestLowerHit = 0;
        for (int h=0; h<nextH; h++) {
            int upperHit = 0;
            int lowerHit = 0;
            // for every horizontal line, check if vertical edge points 
            // are on that line.
            double[] hline = horizontalLines[h];
            double hy1 = hline[1],
                   hy2 = hline[3];
            for (int v=0; v<nextV; v++){
                double[] vec = verticalLines[v];
                double vy1 = vec[1],
                       vy2 = vec[3];
                // if they are, count them
                if(hy1<=vy1 && vy1<=hy2){
                    // the endpoint vy1 is on the line
                    if(vy1 < vy2) {
                        upperHit++;
                    } else {
                        lowerHit++;
                    }
                } else if (hy1<=vy2 && vy2<=hy2) {
                 // the endpoint vy2 is on the line
                    if(vy1 > vy2) {
                        upperHit++;
                    } else {
                        lowerHit++;
                    }
                }

            }
            // take the lines with the highes counts
            // the one with low x values is the upper line, 
            // the other one the lower one. 
            if(upperHit > bestUpperHit) {
                edges[0] = horizontalLines[h];
                bestUpperHit = upperHit;
            } else if (lowerHit > bestLowerHit){
                edges[2] = horizontalLines[h];
                bestLowerHit = lowerHit;
            }
        }
        Log.v(TAG, "Best upper hit: " + bestUpperHit + ", best lower: " + bestLowerHit);
    }
    
    public void drawEdges(Mat mRgba){
        // draw edge points
        findUpperAndLowerEdge();
        Point left = new Point(edges[0][0], edges[0][1]);
        Point right = new Point(edges[0][2], edges[0][3]);
        Core.line(mRgba,left, right, new Scalar(255, 0, 0), 3);
        
        left = new Point(edges[2][0], edges[2][1]);
        right = new Point(edges[2][2], edges[2][3]);
        Core.line(mRgba,left, right, new Scalar(255, 0, 0), 3);
    }
    
    

}
