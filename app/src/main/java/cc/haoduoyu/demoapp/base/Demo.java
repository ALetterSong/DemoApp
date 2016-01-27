package cc.haoduoyu.demoapp.base;

import android.content.Intent;

/**
 * Created by XP on 2016/1/27.
 */
public class Demo {

    public Demo(String title, Intent intent) {
        this.title = title;
        this.intent = intent;
    }

    public String title;
    public Intent intent;
}
