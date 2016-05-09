package cc.haoduoyu.demoapp.webview;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import cc.haoduoyu.demoapp.R;

/**
 * Created by XP on 2016/5/6.
 */
public class ShowImageActivity extends Activity {
    private String imagePath;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        imagePath = getIntent().getStringExtra("image");

        imageView = (ImageView) findViewById(R.id.show_webimage_imageview);

        Picasso.with(this).load(imagePath).into(imageView);

    }
}
