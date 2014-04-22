package ch.zahw.students.sudokuhelper;

import java.io.File;
import java.io.IOException;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import ch.zahw.students.sudokuhelper.capture.DigitExtractor;
import ch.zahw.students.sudokuhelper.capture.NoSudokuFoundException;
import ch.zahw.students.sudokuhelper.capture.SudokuTracker;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;


/**
 * This class is used to test the image recognition.
 * It is a stub class which uses stored images of previously captured sudokus.
 * @author simon
 */
public class TestActivity extends Activity {

    private static final String TAG = "SudokuHelper::TestActivity";
    private static final int WIDTH = 768;
    private static final int HEIGHT = 1024;
    private Mat mGray;
    private Mat mRgba;
    private static SudokuTracker sudokuTracker;
    
    public TestActivity() {
        Log.v(TAG, "Test Activity initialized: " + WIDTH + "x" + HEIGHT);
    }
        
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            if (status == LoaderCallbackInterface.SUCCESS ) {
                Log.v(TAG, "OpenCV loaded...");
                mGray = new Mat(HEIGHT, WIDTH, CvType.CV_8UC1);
                // mRgba = new Mat(HEIGHT, WIDTH, CvType.CV_8UC4);
                sudokuTracker = new SudokuTracker(WIDTH, HEIGHT);

                // now we can call opencv code !
                findSudoku();
            } else {
                super.onManagerConnected(status);
            }
        }
    };

    @Override
    public void onResume()
    {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_5, this, mLoaderCallback);
    }

    public void findSudoku() {

        try {
            mGray = Utils.loadResource(this, R.raw.s57, Highgui.CV_LOAD_IMAGE_GRAYSCALE);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.e(TAG, "error loading resource");
            e.printStackTrace();
        }
        
        
        // send the image to the tracker
        mRgba = sudokuTracker.detect(mGray);
        Mat mStraight = sudokuTracker.getMStraight();
        DigitExtractor extractor = new DigitExtractor(mStraight);
        Imgproc.cvtColor(mStraight, mRgba, Imgproc.COLOR_GRAY2RGBA, 4 );

        try {
            extractor.extractDigits(mRgba);
        } catch (NoSudokuFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        
        // write the Mat to disk for test use. (uncomment permission in AndroidManifest)
//        File file = new File(Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES), "straightMat.png");
//        Log.i(TAG, "Saving mat to " + file.toString());
//        Highgui.imwrite(file.toString(), mStraight);
        
    
        // convert to bitmap:
        Bitmap bm = Bitmap.createBitmap(mRgba.cols(), mRgba.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(mRgba, bm);
    
        // find the imageview and draw it!
        ImageView iv = (ImageView) findViewById(R.id.imageView1);
        iv.setImageBitmap(bm);
    }

}
