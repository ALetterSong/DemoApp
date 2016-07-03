package cc.haoduoyu.demoapp.camera.cameramanager;

import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * basic camera preview
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mHolder;
    private Camera mCamera;
    private final static String TAG = "CameraPreview";
    private MyOrientationDetector cameraOrientation;
    private Context mContext;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mContext = context;
        mCamera = camera;
        setCameraParms();

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            setCameraParms();
            mCamera.startPreview();

        } catch (Exception e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

    private void setCameraParms() {
        Camera.Parameters myParam = mCamera.getParameters();

        cameraOrientation = new MyOrientationDetector(mContext);
        int orientation = cameraOrientation.getOrientation();
        myParam.setRotation(90);
        myParam.set("rotation", 90);
        if ((orientation >= 45) && (orientation < 135)) {
            myParam.setRotation(180);
            myParam.set("rotation", 180);
        }
        if ((orientation >= 135) && (orientation < 225)) {
            myParam.setRotation(270);
            myParam.set("rotation", 270);
        }
        if ((orientation >= 225) && (orientation < 315)) {
            myParam.setRotation(0);
            myParam.set("rotation", 0);
        }


        myParam.setPreviewSize(1280, 720);
        myParam.setPictureSize(1280, 720);
        List<String> focusModes = myParam.getSupportedFocusModes();
        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            myParam.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }
        myParam.setPictureFormat(ImageFormat.JPEG);
        myParam.setJpegQuality(100);
        myParam.setJpegThumbnailQuality(100);
        myParam.setAntibanding(Camera.Parameters.ANTIBANDING_AUTO);
        myParam.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
        myParam.setZoom(0);
        mCamera.setParameters(myParam);

    }

    /**
     * 手动聚焦
     *
     * @param point 触屏坐标
     */
    public void onFocus(Point point, Camera.AutoFocusCallback callback) {
        Camera.Parameters parameters = mCamera.getParameters();
        //不支持设置自定义聚焦，则使用自动聚焦，返回
        if (parameters.getMaxNumFocusAreas() <= 0) {
            mCamera.autoFocus(callback);
            return;
        }
        List<Camera.Area> areas = new ArrayList<Camera.Area>();
        int left = point.x - 300;
        int top = point.y - 300;
        int right = point.x + 300;
        int bottom = point.y + 300;
        left = left < -1000 ? -1000 : left;
        top = top < -1000 ? -1000 : top;
        right = right > 1000 ? 1000 : right;
        bottom = bottom > 1000 ? 1000 : bottom;
        areas.add(new Camera.Area(new Rect(left, top, right, bottom), 100));
        parameters.setFocusAreas(areas);
        try {
            mCamera.setParameters(parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mCamera.autoFocus(callback);
    }

}
