package cc.haoduoyu.demoapp.webview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cc.haoduoyu.demoapp.R;
import cc.haoduoyu.demoapp.utils.ToastUtils;

/**
 * Created by XP on 2016/1/30.
 */
public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        final WebView webView = (WebView) findViewById(R.id.webview);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JsInteraction(), "control");
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                test(webView);
            }
        });
        webView.loadUrl("file:///android_asset/js.html");
    }

    private void test(WebView webview) {
        String call;
//        call = "javascript:sayHello()";
        call = "javascript:alertMessage(\"" + "java call js" + "\")";
//        call = "javascript:toastMessage(\"" + "content from js" + "\")";
//        call = "javascript:sumToJava(1,2)";
        webview.loadUrl(call);//调用js写的代码
        webview.loadUrl("javascript:javaCallJsWithArgs(\"" + "java call js" + "\")");//调用js写的代码

    }

    public class JsInteraction {

        @JavascriptInterface
        public void showToast(String txt) {
            ToastUtils.showToast(WebViewActivity.this, txt);
        }

        @JavascriptInterface
        public void onSumResult(int result) {
            Log.i("WebViewActivity", "result=" + result);
        }

        @JavascriptInterface
        public void startFunction() {
            ToastUtils.showToast(WebViewActivity.this, "js调用了java函数(class JsInteraction)"
                    + System.currentTimeMillis());
        }

    }
}
