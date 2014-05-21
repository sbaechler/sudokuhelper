package ch.zahw.students.sudokuhelper;

import java.util.List;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.core.Mat;

import ch.zahw.students.sudokuhelper.capture.DigitExtractor;
import ch.zahw.students.sudokuhelper.capture.FieldCandidate;
import ch.zahw.students.sudokuhelper.capture.NeuralNetworkRecognizer;
import ch.zahw.students.sudokuhelper.capture.NoSudokuFoundException;
import ch.zahw.students.sudokuhelper.capture.Recognizer;
import ch.zahw.students.sudokuhelper.capture.SudokuTracker;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceView;
import android.view.WindowManager;


/**
 * This activity digitizes the Sudoku and recognizes the numbers.
 * If it is successfull it passes an array with the result back to the 
 * main activity.
 *
 */
public class CaptureActivity extends Activity implements CvCameraViewListener2 {
    private static final String TAG = "SudokuHelper::CaptureActivity";
    private static final int MAX_FRAME_SIZE = 1024;
    
    private CameraBridgeViewBase mOpenCvCameraView;
    private SudokuTracker sudokuTracker = null;
    private static DigitExtractor digitExtractor;
    private Recognizer recognizer;
    
    /**
     * Callback class that gets called when the native OpenCV libraries
     * have loaded.
     */
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            if (status == LoaderCallbackInterface.SUCCESS ) {
                    Log.v(TAG, "OpenCV loaded successfully");
                    digitExtractor = new DigitExtractor();
                    recognizer = new NeuralNetworkRecognizer(getApplicationContext());
                    mOpenCvCameraView.setMaxFrameSize(MAX_FRAME_SIZE, MAX_FRAME_SIZE);
                    mOpenCvCameraView.enableView();
                } else {
                    super.onManagerConnected(status);
                }
        }
    };
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        setContentView(R.layout.activity_capture);
        
        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.capture_activity_capture);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }
   
    @Override
    public void onResume()
    {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_5, this, mLoaderCallback);
    }

    public void onDestroy() {
        // Log.d(TAG, "Capture Activity destroyed");
        super.onDestroy();    
        sudokuTracker = null;
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }
    
    public void onCameraViewStarted(int width, int height) {
        if(sudokuTracker == null){
            sudokuTracker = new SudokuTracker(width, height, getApplicationContext());
        }
    }

    public void onCameraViewStopped() {
        // Log.d(TAG, "Camera view stopped");
    }
    
    /**
     * Serialize all the candidates to a string and return them to the caller
     * activity.
     * @param candidates - a list with all the found numbers.
     */
    public void finish(List<FieldCandidate> candidates){
        Log.v(TAG, "Capture Activity Finish method called");
        String allCandidates = TextUtils.join(";", candidates);
        // Create intent to deliver some kind of result data
        Intent result = new Intent(this, MainActivity.class);
        setResult(Activity.RESULT_OK, result);
        result.putExtra("candidates", allCandidates);
        super.finish();
    }

    /**
     * This method gets called every time there is a preview frame coming
     * from the camera view. 
     * All the processing is initiated from within this method.
     * The returned frame is passed to the view and
     * displayed to the user.
     */
    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        Log.v(TAG, "Got Camera Frame. Width: " + inputFrame.gray().cols());
        Mat mRgba = sudokuTracker.detect(inputFrame.gray());
        
        if(sudokuTracker.hasFoundCandidate()){
            List<FieldCandidate> candidates;
            Mat mStraight = sudokuTracker.getMStraight();
            Mat transform = sudokuTracker.getInverseTransformMat();
            digitExtractor.setSource(mStraight);
            try {
                candidates = digitExtractor.extractDigits(
                        sudokuTracker.onlyRoi(mRgba), transform);
                // update array in place.
                recognizer.regognize(candidates);
                finish(candidates);
            } catch (NoSudokuFoundException e) {
                Log.v(TAG, e.getMessage());
                sudokuTracker.setFoundCandidate(false);
            }
        }
        return mRgba;
    }
    


}
