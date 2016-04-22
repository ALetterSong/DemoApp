package cc.haoduoyu.demoapp.mytest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cc.haoduoyu.demoapp.R;

/**
 * http://www.jianshu.com/p/03118c11c199
 * https://google.github.io/android-testing-support-library/
 * Created by XP on 2016/4/20.
 */
public class UITest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    public void sayHello(View v) {
        TextView textView = (TextView) findViewById(R.id.test_textView);
        EditText editText = (EditText) findViewById(R.id.test_editText);
        textView.setText("Hello, " + editText.getText().toString() + "!");
    }
}
