package cc.haoduoyu.demoapp.moretextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cc.haoduoyu.demoapp.R;

/**
 * 多行文本折叠展开效果
 * The original source can be found here:
 * http://blog.csdn.net/qiaoidea/article/details/45568653
 */
public class MoreTextView extends LinearLayout {
    private static final String TAG = "MoreTextView";

    protected TextView contentView;
    protected ImageView expandView;

    protected int textColor;
    protected float textSize;
    protected int maxLine;
    protected String text;

    public int defaultTextColor = Color.BLACK;
    public int defaultTextSize = 12;
    public int defaultLine = 3;

    public MoreTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initWithAttrs(context, attrs);
        bindListener();
    }

    protected void initWithAttrs(Context context, AttributeSet attrs) {

        //自定义属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MoreTextStyle);
        int textColor = a.getColor(R.styleable.MoreTextStyle_textColor, defaultTextColor);
        textSize = a.getDimensionPixelSize(R.styleable.MoreTextStyle_textSize, defaultTextSize);
        maxLine = a.getInt(R.styleable.MoreTextStyle_maxLine, defaultLine);
        text = a.getString(R.styleable.MoreTextStyle_text);
        bindTextView(textColor, textSize, maxLine, text);
        a.recycle();
    }

    //初始化并添加View。初始化TextView和ImageView,并添加到MoreTextView中去
    protected void init() {
        setOrientation(VERTICAL);
        setGravity(Gravity.RIGHT);
        contentView = new TextView(getContext());
        addView(contentView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        expandView = new ImageView(getContext());
        int padding = dip2px(getContext(), 5);
        expandView.setPadding(padding, padding, padding, padding);
        expandView.setImageResource(R.drawable.text_ic_expand);
        LayoutParams llp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        addView(expandView, llp);
    }

    //绑定点击事件并设置动画。 给当前MoreTextView设置点击事件，实现点击折叠和展开。
    protected void bindTextView(int color, float size, final int line, String text) {
        contentView.setTextColor(color);
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        contentView.setText(text);
        contentView.setHeight(contentView.getLineHeight() * line);
        post(new Runnable() {

            @Override
            public void run() {
                ////根据高度来判断是否需要再点击展开
                expandView.setVisibility(contentView.getLineCount() > line ? View.VISIBLE : View.GONE);
            }
        });
    }

    protected void bindListener() {
        setOnClickListener(new OnClickListener() {
            boolean needExpanded;

            @Override
            public void onClick(View v) {
                needExpanded = !needExpanded;
                Log.d(TAG, "needExpanded: " + needExpanded);
                contentView.clearAnimation();
                final int deltaValue;
                final int startValue = contentView.getHeight();
                int durationMillis = 350;
                //展开TextView
                if (needExpanded) {
                    //总高度-当前高度(设置的可视高度)
                    deltaValue = contentView.getLineHeight() * contentView.getLineCount() - startValue;
                    Log.d(TAG, "contentView.getLineHeight(): " + contentView.getLineHeight());
                    Log.d(TAG, "contentView.getLineCount(): " + contentView.getLineCount());
                    //旋转动画效果
                    RotateAnimation animation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    animation.setDuration(durationMillis);
                    animation.setFillAfter(true);
                    expandView.startAnimation(animation);
                    Log.d(TAG, "展开TextView");
                    //收缩TextView
                } else {
                    //设置的可视高度-当前高度(总高度)
                    deltaValue = contentView.getLineHeight() * maxLine - startValue;
                    Log.d(TAG, "contentView.getLineHeight(): " + contentView.getLineHeight());
                    Log.d(TAG, "maxLine: " + maxLine);
                    //旋转动画效果
                    RotateAnimation animation = new RotateAnimation(180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    animation.setDuration(durationMillis);
                    animation.setFillAfter(true);
                    expandView.startAnimation(animation);
                    Log.d(TAG, "收缩TextView");
                }
                Animation animation = new Animation() {
                    //根据ImageView旋转动画的百分比来显示textView高度，达到动画效果
                    protected void applyTransformation(float interpolatedTime, Transformation t) {
                        contentView.setHeight((int) (startValue + deltaValue * interpolatedTime));
                        Log.d(TAG, "startValue: " + startValue + " deltaValue: " + deltaValue
                                + " interpolatedTime: " + interpolatedTime);
                    }

                };
                animation.setDuration(durationMillis);
                contentView.startAnimation(animation);
            }
        });
    }

    public TextView getTextView() {
        return contentView;
    }

    public void setText(CharSequence charSequence) {
        contentView.setText(charSequence);
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
