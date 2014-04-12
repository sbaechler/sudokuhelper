package ch.zahw.students.sudokuhelper;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.WindowManager;

public class CaptureActivity extends Activity implements CvCameraViewListener2 {
    private static final String TAG = "SudokuHelper::CaptureActivity";
    
    private CameraBridgeViewBase mOpenCvCameraView;
<<<<<<< HEAD
    private Mat mGray;
    private Mat mRgba;
=======
    private SudokuTracker sudokuTracker;
>>>>>>> 1fb755756b8bd50c625a645ff1dafd3d51e048b3
    
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
<<<<<<< HEAD
                    Log.i(TAG, "OpenCV loaded successfully");
=======
                    Log.v(TAG, "OpenCV loaded successfully");
>>>>>>> 1fb755756b8bd50c625a645ff1dafd3d51e048b3
                    mOpenCvCameraView.enableView();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
<<<<<<< HEAD
        Log.i(TAG, "called onCreate");
=======
>>>>>>> 1fb755756b8bd50c625a645ff1dafd3d51e048b3
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
<<<<<<< HEAD
        super.onDestroy();
=======
        super.onDestroy();    
>>>>>>> 1fb755756b8bd50c625a645ff1dafd3d51e048b3
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }
    
    public void onCameraViewStarted(int width, int height) {
<<<<<<< HEAD
        Log.i(TAG, "Camera view started");
        mRgba = new Mat(height, width, CvType.CV_8UC4);
        mGray = new Mat(height, width, CvType.CV_8UC1);
    }

    public void onCameraViewStopped() {
        mRgba.release();
        mGray.release();
        Log.i(TAG, "Camera view stopped");
    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        Imgproc.cvtColor(inputFrame.gray(), mRgba, Imgproc.COLOR_GRAY2RGBA, 4);
        return mRgba;
=======
        sudokuTracker = new SudokuTracker(width, height);
    }

    public void onCameraViewStopped() {
        sudokuTracker = null;
        Log.d(TAG, "Camera view stopped");
    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        return sudokuTracker.detect(inputFrame.gray());
>>>>>>> 1fb755756b8bd50c625a645ff1dafd3d51e048b3
    }
    


}
