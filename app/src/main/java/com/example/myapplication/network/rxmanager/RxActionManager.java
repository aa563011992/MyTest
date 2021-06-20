package com.example.myapplication.network.rxmanager;


import com.example.myapplication.network.HttpResultSubscriber;

public interface RxActionManager<T> {

    void add(T tag, HttpResultSubscriber observable);
    void remove(T tag);

    void cancel(T tag);

    void cancelAll();
}
