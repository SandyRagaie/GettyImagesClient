package com.sandyr.demo.gettyimages.common.network;



import com.sandyr.demo.gettyimages.BuildConfig;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sandyr on 2/26/2017.
 */
public class RetrofitServiceManager {
    private Retrofit mRetrofit;

    private static RetrofitServiceManager ourInstance = new RetrofitServiceManager();

    public static RetrofitServiceManager getInstance() {
        return ourInstance;
    }

    private RetrofitServiceManager() {
        OkHttpClient client = new OkHttpClient.Builder().build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
    }

    public Retrofit getRetrofitClient() {
        return mRetrofit;
    }
}
