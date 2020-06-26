package com.merlin.patterns.observer;

import java.util.Observable;
import java.util.Observer;

public class ObserverDemo {
    public static void main(String[] args) {
        // 可以理解为主题Subject
        MyObservable observable = new MyObservable();

        // 注册观察者
        observable.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.println("观察者1: 收到的通知:" + arg);
            }
        });
        observable.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.println("观察者2: 收到的通知:" + arg);
            }
        });
        observable.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.println("观察者3: 收到的通知:" + arg);
            }
        });

        // 调整变化
        observable.setChanged();

        // 通知变化到所有观察者
        observable.notifyObservers("Hell World");;

    }

    private static class MyObservable extends Observable {
        // 子类提升方法，将protected变为public
        public void setChanged() {
            super.setChanged();
        }
    };
}
