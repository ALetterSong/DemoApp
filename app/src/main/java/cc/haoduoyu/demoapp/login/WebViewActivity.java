package cc.haoduoyu.demoapp.login;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import cc.haoduoyu.demoapp.R;

/**
 * WebView
 * http://developer.android.com/guide/webapps/webview.html
 * <p/>
 * ！That's it. Now all links the user clicks load in your WebView.！
 * ！The WebView now automatically syncs cookies as necessary.
 * You no longer need to create or use the CookieSyncManager.！
 * <p/>
 * fitsSystemWindows会导致toolbar颜色异常
 * Created by XP on 2016/3/4.
 */
public class WebViewActivity extends AppCompatActivity {

    @Bind(R.id.webview)
    WebView mWebView;
    @Bind(R.id.progressbar)
    ProgressBar mProgressbar;

    private static final String EXTRA_URL = "url";
    private static final String EXTRA_TITLE = "title";

    private String mUrl, mTitle;



    public static void startIt(Context context, String url, String title) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(EXTRA_URL, url);
        intent.putExtra(EXTRA_TITLE, title);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        mUrl = getIntent().getStringExtra(EXTRA_URL);
        mTitle = getIntent().getStringExtra(EXTRA_TITLE);

        WebSettings settings = mWebView.getSettings();
        //设置是否支持Javascript
        settings.setJavaScriptEnabled(true);
        //设置WebView推荐使用的窗口
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setSupportZoom(true);
        //设置H5的缓存是否打开，默认关闭
        settings.setAppCacheEnabled(true);
        //设置布局方式(单列显示)
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.setWebChromeClient(new ChromeClient());
        mWebView.setWebViewClient(new WebViewClient());

        mWebView.loadUrl(mUrl);

        if (mTitle != null) setTitle(mTitle);
    }


    private void refresh() {
        mWebView.reload();
    }

    private void goForward() {
        mWebView.goForward();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    //支持返回
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) mWebView.destroy();
    }


    @Override
    protected void onPause() {
        if (mWebView != null) mWebView.onPause();
        super.onPause();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mWebView != null) mWebView.onResume();
    }


    private class ChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (mProgressbar != null) {
                if (newProgress == 100) {
                    mProgressbar.setVisibility(View.INVISIBLE);
                } else {
                    if (View.INVISIBLE == mProgressbar.getVisibility()) {
                        mProgressbar.setVisibility(View.VISIBLE);
                    }
                    mProgressbar.setProgress(newProgress);
                }
            }
            //显示网页标题,onReceivedTitle对goBack()无效
            setTitle(view.getTitle());
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            setTitle(title);
        }
    }

    /**
     * 使用外部浏览器打开
     * http://developer.android.com/guide/webapps/webview.html
     */
    private class Client2 extends WebViewClient {

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals("www.example.com")) {
                // This is my web site, so do not override; let my WebView load the page
                return false;
            }
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }
    }


}
