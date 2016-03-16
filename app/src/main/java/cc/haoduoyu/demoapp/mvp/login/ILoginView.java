package cc.haoduoyu.demoapp.mvp.login;

/**
 * Created by XP on 2016/3/11.
 */
public interface ILoginView {
    String getUserName();

    String getPassword();

    void showLoading();

    void hideLoading();

    void toMainActivity(User user);

    void showFailedError();
}
