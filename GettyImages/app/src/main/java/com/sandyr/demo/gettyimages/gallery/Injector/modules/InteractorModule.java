package com.sandyr.demo.gettyimages.gallery.Injector.modules;

import com.sandyr.demo.gettyimages.BuildConfig;
import com.sandyr.demo.gettyimages.gallery.Interactor.Services.GettyImageService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sandyr on 7/17/2017.
 */
@Module
public class InteractorModule {

    @Provides
    @Singleton
    static Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(new OkHttpClient.Builder().build())
                .build();
    }

    @Provides
    static public GettyImageService provideGettyImageService(Retrofit retrofit) {
        return retrofit.create(GettyImageService.class);
    }

}
