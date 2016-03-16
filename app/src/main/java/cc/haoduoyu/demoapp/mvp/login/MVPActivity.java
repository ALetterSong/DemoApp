package cc.haoduoyu.demoapp.mvp.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import cc.haoduoyu.demoapp.R;

/**
 * MVP模式
 * View 对应于Activity，负责View的绘制以及与用户交互
 * Model 依然是业务逻辑和实体模型
 * Presenter 负责完成View于Model间的交互
 * <p/>
 * https://github.com/antoniolg/androidmvp
 * http://blog.csdn.net/lmj623565791/article/details/46596109
 * <p/>
 * Created by XP on 2016/3/11.
 */
public class MVPActivity extends AppCompatActivity implements ILoginView {
    private EditText mUsernameEt;
    private EditText mPasswordEt;
    private Button mLoginBtn;
    private ProgressBar mProgressBar;

    private LoginPresenter presenter = new LoginPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
        mUsernameEt = (EditText) findViewById(R.id.username);
        mPasswordEt = (EditText) findViewById(R.id.password);
        mLoginBtn = (Button) findViewById(R.id.button);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.login();
            }
        });
    }

    @Override
    public String getUserName() {
        return mUsernameEt.getText().toString();
    }

    @Override
    public String getPassword() {
        return mPasswordEt.getText().toString();
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void toMainActivity(User user) {
        Toast.makeText(this, user.getUsername() + " login success to MainActivity ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFailedError() {
        Toast.makeText(this, "login failed", Toast.LENGTH_SHORT).show();
    }
}
