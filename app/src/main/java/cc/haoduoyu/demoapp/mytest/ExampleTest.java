package cc.haoduoyu.demoapp.mytest;

import android.test.InstrumentationTestCase;

/**
 * http://rexstjohn.com/unit-testing-with-android-studio/
 * Created by XP on 2016/4/20.
 */
public class ExampleTest extends InstrumentationTestCase {
    public void test() throws Exception {
        final int expected = 1;
        final int reality = 5;
        assertEquals(expected, reality);
    }
}
