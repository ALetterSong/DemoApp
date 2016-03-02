package cc.haoduoyu.demoapp.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast工具类
 * Created by XP on 2016/1/9.
 */
public class ToastUtils {

    private static String mOldMsg;
    protected static Toast mToast = null;
    private static long oneTime = 0;

    private static long twoTime = 0;

    private ToastUtils() {
    }

    public static void showToast(Context context, String s) {
        if (mToast == null) {
            mToast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
            mToast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (s.equals(mOldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    mToast.show();
                }
            } else {
                mOldMsg = s;
                mToast.setText(s);
                mToast.show();
            }
        }
        oneTime = twoTime;
    }

}