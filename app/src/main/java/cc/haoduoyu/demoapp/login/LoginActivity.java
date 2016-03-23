package cc.haoduoyu.demoapp.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cc.haoduoyu.demoapp.R;

/**
 * Created by XP on 2016/3/19.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String SD_URL = "http://ids1.suda.edu.cn/amserver/UI/Login?goto=http://myauth.suda.edu.cn/default.aspx?app=wzjw";
    public static final String WZ_URL = "http://wzjw.sdwz.cn/";

    private ImageView imageView;
    private EditText editText;
    private TextView textView;
    RequestQueue mRequestQueue;
    private Button button;
    private Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        imageView = (ImageView) findViewById(R.id.imageView);
        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);
        button1 = (Button) findViewById(R.id.open);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.startIt(LoginActivity.this, WZ_URL, null);
            }
        });
        button.setOnClickListener(this);
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        ImageLoaderUtils.displayUserImage("http://wzjw.sdwz.cn/ImageValidate.ashx?temp=ilyfj16s", imageView);
    }

    public CookieManager cookieManager = null;
    public static String cookies;
    private boolean clear;
    String value1, value2, value3, strResult;

    public String sendPost(String url) {
        try {
            CookieSyncManager.createInstance(this);
            // 每次登录操作的时候先清除cookie
            clear = !clear;
            if (clear)
                removeAllCookie();
            // 根据url获得HttpPost对象
            HttpPost httpRequest = new HttpPost(url);
            // 根据url获得HttpGet对象
            HttpGet httpGet = new HttpGet(url);
            // 取得默认的HttpClient
            DefaultHttpClient httpclient = new DefaultHttpClient();
            strResult = null;
            // NameValuePair实现请求参数的封装
            HttpResponse response = httpclient.execute(httpGet);
            Document doc = Jsoup.parse(response.getEntity().getContent(), "gbk", "http://i.cqut.edu.cn");
            value1 = doc.select("[name=__VIEWSTATE]").attr("value");
            value2 = doc.select("[name=__VIEWSTATEGENERATOR]").attr("value");
            value3 = doc.select("[name=__EVENTVALIDATION]").attr("value");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
//            params.add(new BasicNameValuePair("__VIEWSTATE", value1));
//            params.add(new BasicNameValuePair("__VIEWSTATEGENERATOR", value2));
//            params.add(new BasicNameValuePair("__EVENTVALIDATION", value3));
//            params.add(new BasicNameValuePair("txtuserid", "1217443023"));
//            params.add(new BasicNameValuePair("txtpwd", "cool6582026.."));
//            params.add(new BasicNameValuePair("txtjym", ""));
//            params.add(new BasicNameValuePair("btnlogin", "登录"));

            params.add(new BasicNameValuePair("IDButton", "Submit"));
            params.add(new BasicNameValuePair("encoded", "false"));
            params.add(new BasicNameValuePair("goto", ""));
            params.add(new BasicNameValuePair("gx_charset", "UTF-8"));
            params.add(new BasicNameValuePair("IDToken0", ""));
            params.add(new BasicNameValuePair("IDToken1", "1217443023"));
            params.add(new BasicNameValuePair("IDToken9", ""));
            params.add(new BasicNameValuePair("IDToken2", "ec5bb526f85028e09d4449ac86c3fea5"));

//        httpRequest.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            httpRequest.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36");
//        httpRequest.addHeader("Host", "ids1.suda.edu.cn");
//            httpRequest.addHeader("Host", "wzjw.sdwz.cn");
//        httpRequest.addHeader("Cookie", cookies);
//        httpRequest.addHeader("Content-Type", "application/x-www-form-urlencoded");
//        httpRequest.addHeader("Origin", "http://ids1.suda.edu.cn");
//        httpRequest.addHeader("Referer", "http://ids1.suda.edu.cn/amserver/UI/Login?goto=http://myauth.suda.edu.cn/default.aspx?app=wzjw");
            // 添加请求参数到请求对象
            httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            // 获得响应对象
            HttpResponse httpResponse = httpclient.execute(httpRequest);
            // 判断是否请求成功
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                // 获得响应返回Json格式数据
                strResult = EntityUtils.toString(httpResponse.getEntity());
                // 取得Cookie
                CookieStore mCookieStore = httpclient.getCookieStore();
                List<Cookie> cookies = mCookieStore.getCookies();
                if (cookies.isEmpty()) {
                    Log.d("", "Cookies为空");
                } else {
                    for (int i = 0; i < cookies.size(); i++) {
                        // 保存cookie
                        Cookie cookie = cookies.get(i);
                        Log.d("", "Cookie " + cookies.get(i).getName() + "=" + cookies.get(i).getValue());
                        cookieManager = CookieManager.getInstance();
                        //JSESSIONID=1E2C5F3EF6146068DF920B66F5FE6FD5; JROUTE=kTye; AMAuthCookie=AQIC5wM2LY4Sfcwa3vWt%2FtEbeWNiNuu4UItTGcWtjoN3mJo%3D%40AAJTSQACMDI%3D%23; amlbcookie=02
                        String cookieString = cookie.getName() + "=" + cookie.getValue() + "; domain=" + cookie.getDomain();
                        cookieManager.setCookie(url, cookieString);
                    }
                }
                Log.d("", strResult);

                return strResult;
            } else {
                strResult = "Error1:" + httpResponse.getStatusLine().toString();
            }
        } catch (ClientProtocolException e) {
            strResult = "Error2:" + e.getMessage();
            e.printStackTrace();
            return strResult;
        } catch (IOException e) {
            strResult = "Error3:" + e.getMessage();
            e.printStackTrace();
            return strResult;
        } catch (Exception e) {
            strResult = "Error4:" + e.getMessage();
            e.printStackTrace();
            return strResult;
        }
        Log.d("", strResult);
        return strResult;
    }

    @Override
    public void onClick(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String s = sendPost(SD_URL);
                Log.d("", s);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(s);
                    }
                });
            }
        }).start();
    }

    public void getMethod() {
        StringRequest request = new StringRequest(Request.Method.GET, "http://wzjw.sdwz.cn/student/xwxf_detail.aspx", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
//        {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap localHashMap = new HashMap();
//                localHashMap.put("Cookie", PreferencesUtils.getString(AboutActivity.this, getString(R.string.cookie)));
//                return localHashMap;
//            }
//        };
        request.setTag(this);
        mRequestQueue.add(request);

    }

    private void removeAllCookie() {
        cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRequestQueue.cancelAll(this);
    }


}
