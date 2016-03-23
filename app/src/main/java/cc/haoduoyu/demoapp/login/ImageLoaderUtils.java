package cc.haoduoyu.demoapp.login;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * Created by XP on 2015/8/12.
 */
public class ImageLoaderUtils {

    public static void displayUserImage(String url, ImageView view) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.clearDiskCache();
        imageLoader.clearMemoryCache();
        DisplayImageOptions options = getSimpleOptions();
        imageLoader.displayImage(url, view, options);
    }

    private static DisplayImageOptions getSimpleOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
                .resetViewBeforeLoading(true)
                .build();
        return options;
    }
}
