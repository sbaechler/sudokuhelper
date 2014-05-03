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

public class CaptureActivity extends Activity implements CvCameraViewListener2 {
    private static final String TAG = "SudokuHelper::CaptureActivity";
    
    private CameraBridgeViewBase mOpenCvCameraView;
    private SudokuTracker sudokuTracker;
    private static DigitExtractor digitExtractor;
    private Recognizer recognizer;
    
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            if (status == LoaderCallbackInterface.SUCCESS ) {
                    Log.v(TAG, "OpenCV loaded successfully");
                    digitExtractor = new DigitExtractor();
                    recognizer = new NeuralNetworkRecognizer(getApplicationContext());
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
        super.onDestroy();    
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }
    
    public void onCameraViewStarted(int width, int height) {
        sudokuTracker = new SudokuTracker(width, height, getApplicationContext());
    }

    public void onCameraViewStopped() {
        sudokuTracker = null;
        Log.d(TAG, "Camera view stopped");
    }
    
    public void finish(List<FieldCandidate> candidates){
        String allCandidates = TextUtils.join(";", candidates);
        // Create intent to deliver some kind of result data
        Intent result = new Intent(this, MainActivity.class);
        setResult(Activity.RESULT_OK, result);
        result.putExtra("candidates", allCandidates);
        super.finish();
    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        Mat mRgba = sudokuTracker.detect(inputFrame.gray());
        if(sudokuTracker.hasFoundCandidate()){
            List<FieldCandidate> candidates;
            mOpenCvCameraView.disableView();
            Mat mStraight = sudokuTracker.getMStraight();
            digitExtractor.setSource(mStraight);
            try {
                candidates = digitExtractor.extractDigits();
                // update array in place.
                recognizer.regognize(candidates);
                finish(candidates);
            } catch (NoSudokuFoundException e) {
                sudokuTracker.setFoundCandidate(false);
                mOpenCvCameraView.enableView();
            }
        }
        return mRgba;
    }
    


}
