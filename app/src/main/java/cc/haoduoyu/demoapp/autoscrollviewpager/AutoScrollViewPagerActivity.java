package cc.haoduoyu.demoapp.autoscrollviewpager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.util.LruCache;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cc.haoduoyu.demoapp.R;

/**
 * 自动循环ViewPager
 * Created by XP on 2016/3/26.
 */
public class AutoScrollViewPagerActivity extends AppCompatActivity {

    private List<Integer> imageList;
    private TextView mTextView;
    private LinearLayout ll;
    private int preEnablePosition = 0;
    private String[] imageText = {"Text1", "Text2", "Text3", "Text4", "Text5"};
    int[] imageIds = {R.drawable.iv1, R.drawable.iv2, R.drawable.iv3, R.drawable.iv4, R.drawable.iv5};
    private ViewPager mThreadViewPager;
    private ViewPager mHandlerViewPager;
    private boolean isRunning = true;
    private boolean isStop = false;
    private int messageTag = 0;
    private static final int DEFAULT_TIME = 1200;
    private boolean firstClick = true;
    private PageChangeListener1 threadListener;
    private PageChangeListener2 handlerListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_scroll_viewpager);

        initViews();
    }

    //线程形式
    Thread mThread = new Thread(new Runnable() {

        @Override
        public void run() {
            while (!isStop) {
                //每个两秒钟发一条消息到主线程，更新viewpager界面
                SystemClock.sleep(DEFAULT_TIME);
                runOnUiThread(new Runnable() {
                    public void run() {
                        int index = mThreadViewPager.getCurrentItem() + 1;
                        mThreadViewPager.setCurrentItem(index % imageList.size());
                        Log.d("线程形式index：", index + "");
                    }
                });
            }
        }
    });

    //Handler形式
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (isRunning) {
                int index = mHandlerViewPager.getCurrentItem() + 1;
                mHandlerViewPager.setCurrentItem(index % imageList.size());
                start(msg.arg1);
                Log.d("Handler形式index：", index + "");
                Log.d("123", mThread.isAlive() + "");
            }
        }
    };

    private void initViews() {
        mThreadViewPager = (ViewPager) findViewById(R.id.viewpager);
        mHandlerViewPager = (ViewPager) findViewById(R.id.loop_viewpager);
        ll = (LinearLayout) findViewById(R.id.ll_point_group);
        mTextView = (TextView) findViewById(R.id.tv_image_text);
        threadListener = new PageChangeListener1();
        handlerListener = new PageChangeListener2();

        imageList = new ArrayList<>();
        for (int id : imageIds) {
            imageList.add(id);
            // 每循环一次添加一个点到布局中
            View view = new View(this);
            view.setBackgroundResource(R.drawable.point_background);
            LayoutParams params = new LayoutParams(5, 5);
            params.leftMargin = 5;
            view.setEnabled(false);
            view.setLayoutParams(params);
            ll.addView(view);
        }

        mThreadViewPager.setAdapter(new MyPageAdapter(this, imageList));
        mHandlerViewPager.setAdapter(new MyPageAdapter(this, imageList));
        mThreadViewPager.setOffscreenPageLimit(5);
        mHandlerViewPager.setOffscreenPageLimit(5);
        mThreadViewPager.addOnPageChangeListener(threadListener);
        mHandlerViewPager.addOnPageChangeListener(handlerListener);

        mTextView.setText(imageText[0]);
        ll.getChildAt(0).setEnabled(true);

        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //线程形式
                if (firstClick) {
                    mThread.start();
                    firstClick = false;
                }
                start();
            }
        });

        findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop();
            }
        });

    }


    //自动播放
    public void start(int time) {
        //Handler形式
        isRunning = true;
        //防止多次点击导致有多个消息
        mHandler.removeMessages(messageTag);
        Message message = Message.obtain();
        message.arg1 = time;
        //what作为Message的标记，用于移除未被接收到的Message
        message.what = messageTag;
        mHandler.sendMessageDelayed(message, time);
    }

    //自动播放，默认间隔
    public void start() {
        start(DEFAULT_TIME);
    }

    //停止自动播放
    public void stop() {
        isRunning = false;
        //移除所有之前的Message
        mHandler.removeMessages(messageTag);
    }

    @Override
    protected void onDestroy() {
        isRunning = false;
        isStop = true;
        if (mHandler != null)
            mHandler.removeCallbacksAndMessages(null);
        mThreadViewPager.removeOnPageChangeListener(threadListener);
        mHandlerViewPager.removeOnPageChangeListener(handlerListener);
        super.onDestroy();
    }

    private class PageChangeListener1 implements OnPageChangeListener {
        private int mPosition;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            // 取余后的索引
            mPosition = position;
            // 根据索引设置图片的描述
            mTextView.setText(imageText[mPosition]);
            // 把上一个点设置为未选中
            ll.getChildAt(preEnablePosition).setEnabled(false);
            // 根据索引设置一个点被选中
            ll.getChildAt(mPosition).setEnabled(true);
            preEnablePosition = mPosition;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            //未拖动页面时
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                if (mPosition == imageList.size() - 1) {
//                    mThreadViewPager.setCurrentItem(0, false);
//                    Log.d("", "mThreadViewPager.setCurrentItem(0, false);");
                }
            }
        }

    }

    private class PageChangeListener2 implements OnPageChangeListener {
        private int mPosition;


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mPosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            //未拖动页面时
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                if (mPosition == imageList.size() - 1) {
//                    mHandlerViewPager.setCurrentItem(0, false);
//                    Log.d("", "mThreadViewPager.setCurrentItem(0, false);");
                }
            }
        }
    }

}
