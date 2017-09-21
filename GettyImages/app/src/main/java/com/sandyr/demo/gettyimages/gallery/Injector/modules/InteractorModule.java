package com.sandyr.demo.gettyimages.gallery.Injector.modules;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.sandyr.demo.gettyimages.BuildConfig;
import com.sandyr.demo.gettyimages.gallery.Interactor.Services.GettyImageService;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sandyr on 7/17/2017.
 */
@Module(includes = {ContextModule.class})
public class InteractorModule {

    @Provides
    @Singleton
    static Retrofit provideRetrofit(File file, final Context context) {
        int cacheSize = 10 * 1024 * 1024 ; // 10 MiB
        Cache cache = new Cache(file, cacheSize);

        //cache interceptor level
        Interceptor cacheInterceptor=new Interceptor()
        {
            @Override
            public Response intercept(Chain chain) throws IOException
            {
                Request request = chain.request();


                if (isNetworkAvailable(context)) {
                    request = request.newBuilder().removeHeader("pragma")
                            .removeHeader("Access-control-allow-headers")
                            .removeHeader("Access-control-allow-methods")
                            .removeHeader("Access-control-max-age")
                            .removeHeader("expires")
                            .removeHeader("cache-control")
                            .header("cache-control", "public, max-age=3000")
                            .build();
                } else {
                    request = request.newBuilder()
                            .removeHeader("pragma")
                            .removeHeader("Access-control-allow-headers")
                            .removeHeader("Access-control-allow-methods")
                            .removeHeader("Access-control-max-age")
                            .removeHeader("expires")
                            .removeHeader("cache-control")
                            .header("cache-control",
                                    "public, only-if-cached").build();

                }
                return chain.proceed(request);
            }
        };

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(cacheInterceptor)
                .addNetworkInterceptor(cacheInterceptor)
                .cache(cache)
                .build();

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    static public GettyImageService provideGettyImageService(Retrofit retrofit) {
        return retrofit.create(GettyImageService.class);
    }
    @Provides
    public File provideFile(android.content.Context context){
        return new File(context.getCacheDir(),
                "apiResponses");
    }

    public static boolean  isNetworkAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
