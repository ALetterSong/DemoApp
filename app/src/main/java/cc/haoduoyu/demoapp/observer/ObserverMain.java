package cc.haoduoyu.demoapp.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * 观察者模式
 *
 * Created by xiepan on 2016/9/15.
 */
public class ObserverMain {


    //好处：松耦合（被观察者可以增加删除观察者；被观察者只负责通知观察者；观察者只需等待通知；）
    //     通知不错过（被动接受，不易错过通知）

    public static void main(String[] args) {

        MilkProvider provider = new MilkProvider();

        Consumer consumer1 = new Consumer("道格拉斯");
        Consumer consumer2 = new Consumer("图灵");
        Consumer consumer3 = new Consumer("牛顿");

        provider.addObserver(consumer1);
        provider.addObserver(consumer2);
        provider.addObserver(consumer3);
        provider.deleteObserver(consumer3);

        provider.milkProduced("草莓味的牛奶");
    }

    // 静态成员属于类,不需要生成对象就存在了.
    // 而非静态需要生成对象才产生，所以静态成员不能直接访问.所以声明为静态类

    // 被观察者 牛奶站
    static class MilkProvider extends Observable {

        // 牛奶做好啦
        public void milkProduced(String milkType) {
            // 标识状态或内容发生改变
            setChanged();
            // 通知所有订奶的客户（观察者）
            notifyObservers(milkType);
        }
    }

    //观察者 订奶的客户
    static class Consumer implements Observer {

        private String name;

        public Consumer(String name) {
            this.name = name;
        }

        @Override
        public void update(Observable observable, Object o) {
            System.out.println("Hi， " + name + " 牛奶做好啦！" + " observable=" + observable + " o=" + o);
        }

        @Override
        public String toString() {
            return "toString " + name;
        }
    }

}
