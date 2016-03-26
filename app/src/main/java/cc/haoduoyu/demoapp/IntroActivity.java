package cc.haoduoyu.demoapp;

import android.app.Activity;
import android.os.Bundle;

import us.feras.mdv.MarkdownView;

/**
 * Created by XP on 2016/3/23.
 */
public class IntroActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        MarkdownView markdownView = (MarkdownView) findViewById(R.id.markdownView);
        markdownView.loadMarkdownFile("file:///android_asset/README.md");
    }
}
