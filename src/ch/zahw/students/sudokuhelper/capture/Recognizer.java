package ch.zahw.students.sudokuhelper.capture;

import java.util.List;

import org.opencv.core.Mat;

/**
 * Interface for the recognizer class.
 */
public interface Recognizer {
    
    /**
     * Recognize a single field
     * @param candidate - A binary Mat of arbitrary size cropped to the digit.
     * @return integer 0-9. (0 is ignored)
     */
    public int[] recognize(Mat candidate);
    
    /**
     * Iterates through all the passed candidates passes them to the recognize
     * function. It does a sanity check in the end and aborts if there are not
     * enough good results found.
     * @param candidates - A List of FieldCandidates containing Mats cropped to 
     *                     the digit. The object is upadted in place.
     * @throws NoSudokuFoundException 
     */
    public void regognize(List<FieldCandidate> candidates) throws NoSudokuFoundException;
    
}
