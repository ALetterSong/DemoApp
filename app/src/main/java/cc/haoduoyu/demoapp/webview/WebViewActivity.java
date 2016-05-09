package cc.haoduoyu.demoapp.webview;

import android.content.Context;
import android.content.Intent;
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

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        webView = (WebView) findViewById(R.id.webview);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JsInteraction(this), "control");
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                test(webView);
                addImageClickListner();

            }
        });
//        webView.loadUrl("file:///android_asset/js.html");
        webView.loadUrl("http://1.androidj.applinzi.com/domsters/index.html");
    }

    // 注入js函数监听
    private void addImageClickListner() {
        // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.control.openImage(this.src);  " +
                "    }  " +
                "}" +
                "})()");
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

        private Context context;

        public JsInteraction(Context context) {
            this.context = context;
        }

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

        @JavascriptInterface
        public void openImage(String img) {
            System.out.println(img);
            //
            Intent intent = new Intent();
            intent.putExtra("image", img);
            intent.setClass(context, ShowImageActivity.class);
            context.startActivity(intent);
            System.out.println(img);
        }

    }
}
