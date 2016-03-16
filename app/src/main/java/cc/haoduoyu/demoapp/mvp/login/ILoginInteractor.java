package cc.haoduoyu.demoapp.mvp.login;

/**
 * Created by XP on 2016/3/11.
 */
public interface ILoginInteractor {
    void login(String username, String password, OnLoginListener loginListener);
}
