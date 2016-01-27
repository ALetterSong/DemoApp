package cc.haoduoyu.demoapp.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by XP on 2016/1/10.
 */
public class App extends Application {
    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }


}
