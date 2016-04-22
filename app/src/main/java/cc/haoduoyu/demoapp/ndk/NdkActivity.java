package cc.haoduoyu.demoapp.ndk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import cc.haoduoyu.demoapp.R;

/**
 * Created by XP on 2016/4/14.
 */
public class NdkActivity extends AppCompatActivity {
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndk);

        mTextView = (TextView) this.findViewById(R.id.tv);

        NdkJniUtils jni = new NdkJniUtils();

        mTextView.setText(jni.getCString());
    }
}
