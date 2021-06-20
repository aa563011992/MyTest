package com.example.myapplication.network.Exception;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.example.myapplication.network.util.Constant;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * 本地错误
 */
public class CustomException {

    //发生错误 统一交给APiException处理
    public static ApiException handleException(Throwable e) {
        ApiException ex;
        if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            //解析错误
            ex = new ApiException(Constant.PARSE_ERROR, Constant.S_PARSE_ERROR);
            return ex;
        } else if (e instanceof ConnectException) {
            //网络错误
            ex = new ApiException(Constant.NETWORK_ERROR, Constant.S_NETWORK_ERROR);
            return ex;
        } else if (e instanceof UnknownHostException || e instanceof SocketTimeoutException) {
            //连接错误
            ex = new ApiException(Constant.NETWORK_ERROR, Constant.S_NETWORK_ERROR);
            return ex;
        }  else if (e instanceof ResultException) {
            //结果错误
            ResultException e1 = (ResultException) e;
            ex = new ApiException(e1.getErrCode(), e1.getMsg());
            return ex;
        }else if(e instanceof HttpException) {
            //后台接口问题
            ex = new ApiException(Constant.HTTP_ERROR, Constant.S_HTTP_ERROR);
            return ex;
        }
        else {
            //未知错误
            ex = new ApiException(Constant.UNKNOWN, Constant.S_UNKNOWN);
            return ex;
        }
    }
}
