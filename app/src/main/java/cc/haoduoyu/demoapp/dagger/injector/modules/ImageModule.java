package cc.haoduoyu.demoapp.dagger.injector.modules;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import cc.haoduoyu.demoapp.dagger.injector.ActivityScope;
import dagger.Module;
import dagger.Provides;

/**
 * Created by xiepan on 2016/9/14.
 */
@Module
public class ImageModule {

    private Context mContext;

    public ImageModule(Context context) {
        mContext = context;
    }

    @Provides
    @ActivityScope
    Context provideActivityContext() {
        return mContext;
    }

    @Provides
    @ActivityScope
    List<String> provideUrls() {
        List<String> urls = new ArrayList<>();

        for (int i = 1; i < 20; i++) {
            urls.add("http://7xq3d5.com1.z0.glb.clouddn.com/wall-" + i + ".jpg");
        }
        return urls;
    }

}
