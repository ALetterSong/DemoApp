package cc.haoduoyu.demoapp.mvp.login;

/**
 * Created by XP on 2016/3/11.
 */
public interface OnLoginListener {
    void loginSuccess(User user);

    void loginFailed();
}
