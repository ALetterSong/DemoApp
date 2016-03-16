package cc.haoduoyu.demoapp.mvp.login;

import android.os.Handler;

/**
 * Created by XP on 2016/3/11.
 */
public class LoginPresenter {

    private ILoginInteractor loginInteractor;
    private ILoginView loginView;
    private Handler mHandler = new Handler();

    public LoginPresenter(ILoginView loginView) {
        this.loginView = loginView;
        this.loginInteractor = new LoginInteractor();
    }

    public void login() {
        loginView.showLoading();
        loginInteractor.login(loginView.getUserName(), loginView.getPassword(), new OnLoginListener() {
            @Override
            public void loginSuccess(final User user) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        loginView.toMainActivity(user);
                        loginView.hideLoading();
                    }
                });
            }

            @Override
            public void loginFailed() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        loginView.showFailedError();
                        loginView.hideLoading();
                    }
                });
            }
        });
    }
}
