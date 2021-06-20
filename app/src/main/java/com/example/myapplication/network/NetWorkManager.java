package com.example.myapplication.network;

import android.net.Uri;

import androidx.collection.ArrayMap;

import com.example.myapplication.network.converter.ResponseConverterFactory;
import com.example.myapplication.network.request.MyRequest;
import com.example.myapplication.network.rxmanager.RxActionManager;
import com.example.myapplication.network.util.GsonUtil;
import com.example.myapplication.network.util.LogUtil;
import com.example.myapplication.network.util.Constant;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class NetWorkManager implements RxActionManager {
    //单例 使用volatile方式cpu运行排序 导致单例失败
    private static volatile NetWorkManager mInstance;
    private static Retrofit retrofit;
    private static volatile MyRequest request = null;
    private static final String TAG = "NetWorkManager";
    private ArrayMap<Object, HttpResultSubscriber> maps;

    //私有化构造函数
    private NetWorkManager() {
    }

    public static NetWorkManager getInstance() {
        if (mInstance == null) {
            synchronized (NetWorkManager.class) {
                if (mInstance == null) {
                    mInstance = new NetWorkManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化必要对象和参数
     */
    public void init() {
        try {
            // 初始化okhttp
            //初始化一个OKhttpClient
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> {
                try {
                    String text = Uri.decode(message);
                    LogUtil.e(TAG, text);
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtil.e(TAG, message);
                }
            });
            //设置日志Level
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[0];
                        }
                    }
            };

            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(15, TimeUnit.SECONDS)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .addNetworkInterceptor(loggingInterceptor)//（网络-okhttp core)
                    .sslSocketFactory(sslSocketFactory, new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[0];
                        }
                    })
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    })
                    .build();

            // 初始化Retrofit
            retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(Constant.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//支持rxjava
                    .addConverterFactory(ResponseConverterFactory.create(GsonUtil.gson))
                    .build();

            maps = new ArrayMap<>();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取请求
     *
     * @return
     */
    public static MyRequest getRequest() {
        if (request == null) {
            synchronized (MyRequest.class) {
                if (request == null)
                    request = retrofit.create(MyRequest.class);
            }
        }
        return request;
    }


    @Override
    public void add(Object tag, HttpResultSubscriber observable) {
        if (maps != null)
            maps.put(tag, observable);
    }

    @Override
    public void remove(Object tag) {
        if (!maps.isEmpty()) {
            maps.remove(tag);
        }
    }

    public void removeAll() {
        if (!maps.isEmpty()) {
            maps.clear();
        }
    }

    @Override
    public void cancel(Object tag) {
        if (maps.isEmpty()) {
            return;
        }
        if (maps.get(tag) == null) {
            return;
        }
        if (!maps.get(tag).getDispable().isDisposed()) {
            maps.get(tag).getDispable().dispose();
            maps.remove(tag);
        }
    }

    @Override
    public void cancelAll() {
        if (maps.isEmpty()) {
            return;
        }
        Set<Object> keys = maps.keySet();
        for (Object apiKey : keys) {
            cancel(apiKey);
        }
    }
}
