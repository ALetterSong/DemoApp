package cc.haoduoyu.demoapp.camera;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;

import java.io.File;
import java.io.IOException;

import cc.haoduoyu.demoapp.R;
import cc.haoduoyu.demoapp.camera.cameramanager.CameraManager;
import cc.haoduoyu.demoapp.camera.cameramanager.CameraPreview;
import cc.haoduoyu.demoapp.utils.Log;


public class CaptureActivity extends Activity {

    private Camera mCamera;
    private CameraPreview mPreview;
    private CameraManager mCameraManager;
    private FrameLayout scanPreview;
    private Button takePicture;
    private Handler autoFocusHandler;
    private static final String DIR = Environment.getExternalStorageDirectory().getPath()
            + File.separator + "demoappimage" + File.separator;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);

        File dir = new File(DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        autoFocusHandler = new Handler();
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        scanPreview = (FrameLayout) findViewById(R.id.capture_preview);
        takePicture = (Button) findViewById(R.id.take_picture);
        takePicture.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCameraManager.takePicture(DIR + "temp_" + System.currentTimeMillis() + ".jpg",
                        new CameraManager.IHandlePic() {
                            @Override
                            public void onHandlePic(String fileUrl) {
                                Log.d("CaptureActivity", "fileUrl " + fileUrl.toString());
                            }
                        });
            }
        });


    }

    Camera.AutoFocusCallback autoFocusCallback = new Camera.AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            autoFocusHandler.postDelayed(doAutoFocus, 1000);
        }
    };

    private Runnable doAutoFocus = new Runnable() {
        public void run() {
            mCamera.autoFocus(autoFocusCallback);
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        initViews();
    }

    private void initViews() {
        mCameraManager = new CameraManager();
        try {
            mCameraManager.open();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mCamera = mCameraManager.getCamera();
//        mPreview = new CameraPreview(this, mCamera);
        mPreview = new CameraPreview(this, mCamera);
        scanPreview.addView(mPreview);

//        LogUtils.d("initViews()");
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
                0.85f);
        animation.setDuration(2000);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(Animation.REVERSE);
    }

    public void onPause() {
        super.onPause();
        releaseCamera();
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCameraManager.close();
//            LogUtils.d("releaseCamera()");
        }
    }

}
