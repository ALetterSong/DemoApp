package cc.haoduoyu.demoapp.rxjava;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.haoduoyu.demoapp.R;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * http://gank.io/post/560e15be2dca930e00da1083
 * https://github.com/androidmalin/RengwuxianRxjava
 * <p/>
 * a library for composing asynchronous and event-based programs using observable sequences for the Java VM"
 * （一个在 Java VM 上使用可观测的序列来组成异步的、基于事件的程序的库）
 * <p/>
 * Created by XP on 2015/12/14.
 */
public class RxJavaActivity extends AppCompatActivity {

    private static String TAG = "RxJavaActivity";

    @Bind(R.id.imageView)
    ImageView mImageView;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.button1)
    /**
     * 1:被观察者,事件源:它决定什么时候触发事件以及触发怎样的事件
     * 2:观察者:它决定事件触发的时候将有怎样的行为
     * 3:订阅
     */
    void method1() {
        //被观察者，事件源

        //简化:观察者的创建2
        Observable<String> observable2 = Observable.just("Hello", "World", "!");

        //简化:观察者的创建3
        String[] array = new String[]{"Hello", "World", "!"};
        Observable observable3 = Observable.from(array);

        Observable<String> observable1 = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("World");
                subscriber.onNext("!");
                subscriber.onCompleted();
                subscriber.onError(new Throwable());
                Log.d(TAG, "被观察者-observable->call()->onCompleted()之后是否还有输出");

            }
        });

        Observer<String> observer = new Observer<String>() {

            /**
             * 事件队列完结
             */
            @Override
            public void onCompleted() {
                Log.d(TAG, "观察者-observer:onCompleted()");
            }

            /**
             * 事件队列异常
             * @param throwable
             */
            @Override
            public void onError(Throwable throwable) {
                Log.d(TAG, "观察者-observer:onError" + throwable.getMessage());
            }

            /**
             * 普通事件
             * @param s
             */
            @Override
            public void onNext(String s) {
                Log.d(TAG, "观察者-observer:onNext():" + s);
            }
        };

        observable1.subscribe(observer);

    }

    @OnClick(R.id.button2)
    /**
     * 简单解释一下这段代码中出现的 Action1 和 Action0。
     * Action0 是 RxJava 的一个接口，它只有一个方法 call()，这个方法是无参无返回值的；
     * 由于 onCompleted() 方法也是无参无返回值的，因此 Action0 可以被当成一个包装对象，
     * 将 onCompleted() 的内容打包起来将自己作为一个参数传入 subscribe()以实现不完整定义的回调。
     * 这样其实也可以看做将 onCompleted() 方法作为参数传进了 subscribe()，相当于其他某些语言中的『闭包』。
     * Action1 也是一个接口，它同样只有一个方法 call(T param)，这个方法也无返回值，但有一个参数；
     * 与 Action0 同理，由于 onNext(T obj) 和 onError(Throwable error) 也是单参数无返回值的，
     * 因此 Action1 可以将 onNext(obj) 和 onError(error) 打包起来传入 subscribe() 以实现不完整定义的回调。
     * 事实上，虽然 Action0 和 Action1 在 API 中使用最广泛，
     * 但 RxJava 是提供了多个 ActionX 形式的接口 (例如 Action2, Action3) 的，它们可以被用以包装不同的无返回值的方法。
     */
    void method2() {
        Observable observable = Observable.from(new String[]{"Hello", "World", "!"});

        Action1<String> onNextAction = new Action1<String>() {
            // onNext()
            @Override
            public void call(String s) {
                Log.d(TAG, s);
            }
        };
        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            // onError()
            @Override
            public void call(Throwable throwable) {
                // Error handling
            }
        };
        Action0 onCompletedAction = new Action0() {
            // onCompleted()
            @Override
            public void call() {
                Log.d(TAG, "completed");
            }
        };

        //自动创建 Subscriber ，并使用 onNextAction 来定义 onNext()
        observable.subscribe(onNextAction);
        //自动创建 Subscriber ，并使用 onNextAction 和 onErrorAction 来定义 onNext() 和 onError()
        observable.subscribe(onNextAction, onErrorAction);
        //自动创建 Subscriber ，并使用 onNextAction、 onErrorAction 和 onCompletedAction 来定义 onNext()、 onError() 和 onCompleted()
        observable.subscribe(onNextAction, onErrorAction, onCompletedAction);

    }

    @OnClick(R.id.button3)
    /**
     * 正如上面两个例子这样，创建出 Observable 和 Subscriber ，
     * 再用 subscribe() 将它们串起来，一次 RxJava 的基本使用就完成了。
     * 然而，
     *
     * 这并没有什么卵用。
     */
    void method3() {
        final int drawableRes = R.mipmap.ic_launcher;

        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = getResources().getDrawable(drawableRes);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        }).subscribe(new Observer<Drawable>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(RxJavaActivity.this, "Error!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNext(Drawable drawable) {
                mImageView.setImageDrawable(drawable);
            }
        });
    }

    @OnClick(R.id.button4)
    /**
     * 在 RxJava 的默认规则中，事件的发出和消费都是在同一个线程的。
     * 也就是说，如果只用上面的方法，实现出来的只是一个同步的观察者模式。
     * 观察者模式本身的目的就是『后台处理，前台回调』的异步机制，
     * 因此异步对于 RxJava 是至关重要的。
     * 而要实现异步，则需要用到 RxJava 的另一个概念： Scheduler（调度器） 。
     */
    void method4() {
        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer number) {
                        Log.d(TAG, "number:" + number);
                    }
                });

    }

    @OnClick(R.id.button5)
    /**
     * 变换
     * 所谓变换，就是将事件序列中的对象或整个序列进行加工处理，转换成不同的事件或事件序列。
     */
    void method5() {
        int drawableRes = R.mipmap.default_artwork;

        Observable.just(drawableRes)
                /**
                 * 1.Func1 和 Action 的区别在于， Func1 包装的是有返回值的方法。
                 * 2.map() 方法将参数中的 Integer 对象转换成一个 Drawable 对象后返回，
                 *   而在经过 map() 方法后，事件的参数类型也由 Integer 转为了 Drawable。
                 *   这种直接变换对象并返回的，是最常见的也最容易理解的变换。
                 *   不过 RxJava 的变换远不止这样，它不仅可以针对事件对象，
                 *   还可以针对整个事件队列，这使得 RxJava 变得非常灵活。
                 */
                .map(new Func1<Integer, Drawable>() {
                    @Override
                    public Drawable call(Integer integer) {
                        Log.d(TAG, "integer: " + integer);
                        return getResources().getDrawable(integer);
                    }
                })
                .subscribeOn(Schedulers.io())////事件产生的线程。指定 subscribe() 发生在 IO 线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (mProgressBar != null) {
                            mProgressBar.setVisibility(View.VISIBLE);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())////指定 Subscriber 所运行在的线程。或者叫做事件消费的线程
                .subscribe(new Subscriber<Drawable>() {
                    @Override
                    public void onCompleted() {
                        if (mProgressBar != null) {
                            mProgressBar.setVisibility(View.GONE);
                        }
                        Log.d(TAG, "观察者:onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mProgressBar != null) {
                            mProgressBar.setVisibility(View.GONE);
                            Toast.makeText(RxJavaActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "观察者:onError(Throwable e):" + e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(Drawable drawable) {
                        mImageView.setImageDrawable(drawable);
                        Log.d(TAG, "观察者:onNext(Drawable drawable):" + drawable.toString());
                    }
                });
    }

    @OnClick(R.id.button6)
    /**
     * flatMap
     */
    void method6() {
        Observable.from(DataFactory.getData())
                .map(new Func1<Student, ArrayList<Course>>() {
                    @Override
                    public ArrayList<Course> call(Student student) {
                        return student.courses;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ArrayList<Course>>() {
                    @Override
                    public void call(ArrayList<Course> courses) {
                        for (int i = 0; i < courses.size(); i++) {
                            Log.d(TAG, "观察者:" + courses.get(i).name);
                        }
                    }
                });

    }

    @OnClick(R.id.button7)
    /**
     * 对method6的简化
     * 和 map() 不同的是， flatMap() 中返回的是个 Observable 对象，
     * 并且这个 Observable 对象并不是被直接发送到了 Subscriber 的回调方法中。
     * flatMap() 的原理是这样的：
     * 1. 使用传入的事件对象创建一个 Observable 对象；
     * 2. 并不发送这个 Observable, 而是将它激活，于是它开始发送事件；
     * 3. 每一个创建出来的 Observable 发送的事件，都被汇入同一个 Observable ，
     * 而这个 Observable 负责将这些事件统一交给 Subscriber 的回调方法。
     * 这三个步骤，把事件拆成了两级，通过一组新创建的 Observable 将初始的对象『铺平』之后通过统一路径分发了下去。
     * 而这个『铺平』就是 flatMap() 所谓的 flat。
     */
    void method7() {
        Observable.from(DataFactory.getData())
                .flatMap(new Func1<Student, Observable<Course>>() {
                    @Override
                    public Observable<Course> call(Student student) {
                        return Observable.from(student.courses);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Course>() {
                    @Override
                    public void call(Course course) {
                        Log.d(TAG, "观察者:" + course.name);
                    }
                });
    }

    @OnClick(R.id.button8)
    void method8() {

    }


}
