package cc.haoduoyu.demoapp.camera.cameramanager;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This code was modified by me. All these changes are licensed under the Apache License.
 * The original source can be found here: https://github.com/SkillCollege}
 * <p/>
 * 相机管理器
 */
public final class CameraManager {

    private static final String TAG = "CameraManager";

    private Camera mCamera;
    private String mFileUrl;
    private IHandlePic mListener;

    /**
     * 打开相机并初始化
     * Opens the mCamera driver and initializes the hardware parameters.
     *
     * @throws IOException Indicates the mCamera driver failed to open.
     */
    public synchronized void open() throws IOException {
        if (mCamera == null) {
            mCamera = Camera.open();
            mCamera.setDisplayOrientation(90);

        }
    }

    public interface IHandlePic {
        void onHandlePic(String fileUrl);
    }

    public synchronized void takePicture(String fileUrl, IHandlePic listener) {
        if (mCamera != null) {
            mListener = listener;
            mFileUrl = fileUrl;
            mCamera.autoFocus(autoFocusCallback);
        }
    }

    Camera.AutoFocusCallback autoFocusCallback = new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            if (success) {
                camera.takePicture(
                        new Camera.ShutterCallback() {
                            @Override
                            public void onShutter() {

                            }
                        }, new Camera.PictureCallback() {
                            @Override
                            public void onPictureTaken(byte[] data, Camera camera) {

                            }
                        }, mJpegCallback);
            }
        }
    };

    private Camera.PictureCallback mJpegCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(final byte[] data, Camera camera) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    File file = new File(mFileUrl);
                    try {
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(data);
                        fos.close();
                        mListener.onHandlePic(mFileUrl);
                        mCamera.stopPreview();
                        mCamera.startPreview();
                    } catch (FileNotFoundException e) {
                        Log.e(TAG, "File not found:" + e.getMessage());
                    } catch (IOException e) {
                        Log.e(TAG, "Error accessing file" + e.getMessage());
                    }
                }
            }).start();
        }
    };

    public synchronized void close() {
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    public synchronized boolean isOpen() {
        return mCamera != null;
    }

    public Camera getCamera() {
        return mCamera;
    }

}
