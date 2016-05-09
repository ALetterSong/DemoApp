package cc.haoduoyu.demoapp.camera.cameramanager;

import android.content.Context;
import android.util.Log;
import android.view.OrientationEventListener;

/**
 * 方向变化监听器，监听传感器方向的改变
 */
public class MyOrientationDetector extends OrientationEventListener {
	int Orientation;

	public MyOrientationDetector(Context context) {
		super(context);
	}

	@Override
	public void onOrientationChanged(int orientation) {
		Log.d("MyOrientationDetector ", "onOrientationChanged:" + orientation);
		this.Orientation = orientation;
		Log.d("MyOrientationDetector", "当前的传感器方向为" + orientation);
	}

	public int getOrientation() {
		return Orientation;
	}
}