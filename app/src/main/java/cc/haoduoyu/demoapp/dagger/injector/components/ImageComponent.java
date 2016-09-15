package cc.haoduoyu.demoapp.dagger.injector.components;

import cc.haoduoyu.demoapp.dagger.injector.ActivityScope;
import cc.haoduoyu.demoapp.dagger.injector.Test;
import cc.haoduoyu.demoapp.dagger.injector.modules.ImageModule;
import cc.haoduoyu.demoapp.dagger.ui.DaggerActivity;
import dagger.Component;

/**
 * Created by xiepan on 2016/9/14.
 */

@ActivityScope
@Component(modules = {ImageModule.class})
public interface ImageComponent {
    void inject(DaggerActivity activity);
}

