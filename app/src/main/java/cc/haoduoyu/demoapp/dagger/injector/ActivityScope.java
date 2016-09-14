package cc.haoduoyu.demoapp.dagger.injector;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by xiepan on 2016/9/14.
 */


@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScope {
}
