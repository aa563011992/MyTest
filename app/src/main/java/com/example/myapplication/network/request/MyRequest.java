package com.example.myapplication.network.request;


import com.example.myapplication.bean.ItemList;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface MyRequest {
    @GET("/mianshi1.js")
    Observable<ItemList> getJson();
}
