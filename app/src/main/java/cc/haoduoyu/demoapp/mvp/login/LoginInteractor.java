package cc.haoduoyu.demoapp.mvp.login;

/**
 * Created by XP on 2016/3/11.
 */
public class LoginInteractor implements ILoginInteractor {
    @Override
    public void login(final String username, final String password, final OnLoginListener loginListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if ("xp".equals(username) && "123456".equals(password)) {
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    loginListener.loginSuccess(user);
                } else {
                    loginListener.loginFailed();
                }
            }

        }).start();
    }
}
