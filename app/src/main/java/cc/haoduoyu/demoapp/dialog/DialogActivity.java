package cc.haoduoyu.demoapp.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cc.haoduoyu.demoapp.R;
import cc.haoduoyu.demoapp.utils.ToastUtils;

/**
 * Created by XP on 2016/3/2.
 */
public class DialogActivity extends Activity {
    private Button button1;
    private Button button2;
    private CustomDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new CustomDialog(DialogActivity.this);
                dialog.setContent("custom content1");
                dialog.setPositiveListener("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showToast(DialogActivity.this, "click OK!");
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new CustomDialog(DialogActivity.this);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setNegativeListener("Cancel", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showToast(DialogActivity.this, "click Cancel!");
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }
}
