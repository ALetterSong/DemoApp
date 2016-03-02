package cc.haoduoyu.demoapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cc.haoduoyu.demoapp.R;
import cc.haoduoyu.demoapp.utils.Log;

/**
 * Created by XP on 2016/3/2.
 */
public class CustomDialog extends Dialog {
    Context context;
    private TextView positiveBtn;
    private CharSequence positiveTxt;
    private TextView negativeBtn;
    private CharSequence negativeTxt;
    private TextView contentTv;
    private CharSequence content;
    private static final String DEFAULT_POSITIVE_CONTENT = "POSITIVE";
    private static final String DEFAULT_NEGATIVE_CONTENT = "NEGATIVE";
    private static final String DEFAULT_CONTENT = "CONTENT";

    public CustomDialog(Context context) {
        super(context);
        this.init(context);
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
        this.init(context);
    }

    public CustomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.init(context);
    }

    private void init(Context context) {
        this.context = context;
        this.setContentView(R.layout.dialog_custom);
        positiveBtn = (TextView) findViewById(R.id.dialog_positive_tv);
        negativeBtn = (TextView) findViewById(R.id.dialog_negative_tv);
        contentTv = (TextView) findViewById(R.id.dialog_content);
    }

    @Override
    public void show() {
        super.show();
        positiveBtn.setText(positiveTxt != null ? positiveTxt : DEFAULT_POSITIVE_CONTENT);
        negativeBtn.setText(negativeTxt != null ? negativeTxt : DEFAULT_NEGATIVE_CONTENT);
        contentTv.setText(content != null ? content : DEFAULT_CONTENT);
        Log.d("CustomDialog", "positiveTxt: " + positiveTxt,
                " negativeTxt: " + negativeTxt, " content: " + content);
    }

    public void setPositiveListener(CharSequence string, View.OnClickListener listener) {
        this.positiveTxt = string;
        this.positiveBtn.setOnClickListener(listener);
    }

    public void setNegativeListener(CharSequence string, View.OnClickListener listener) {
        this.negativeTxt = string;
        this.negativeBtn.setOnClickListener(listener);
    }

    public void setContent(CharSequence content) {
        this.content = content;
    }
}

