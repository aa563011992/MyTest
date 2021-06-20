package com.example.myapplication.network;



import com.example.myapplication.network.Exception.ApiException;
import com.example.myapplication.network.Exception.CustomException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class HttpResultSubscriber<T> implements Observer<T> {

    private Disposable myDisposable;

    @Override
    public void onNext(T tMyResponse) {
        onSuccess(tMyResponse);
    }
//
    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        //在这里做全局的错误处理
        ApiException apiException = CustomException.handleException(e);
        onError(apiException.getCode(),apiException.getDisplayMessage());
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(Disposable d) {
        myDisposable =d;
    }

    public  Disposable getDispable(){
        if(myDisposable!=null)
        return myDisposable;
        else
            return null;
    }
    public abstract void onSuccess(T t);

    public abstract void onError(int code, String msg);

}
