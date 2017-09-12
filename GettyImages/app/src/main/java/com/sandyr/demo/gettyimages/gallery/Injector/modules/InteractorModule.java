package com.sandyr.demo.gettyimages.gallery.Injector.modules;

import android.app.Activity;
import android.os.Environment;

import com.sandyr.demo.gettyimages.BuildConfig;
import com.sandyr.demo.gettyimages.gallery.Interactor.Services.GettyImageService;
import com.sandyr.demo.gettyimages.gallery.Interactor.cache.CacheProvider;
import com.sandyr.demo.gettyimages.gallery.Interactor.responses.GettyImageResponse;
import com.sandyr.demo.gettyimages.gallery.presenter.GalleryPresenterImpl;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by sandyr on 7/17/2017.
 */
@Module
public class InteractorModule {

    @Inject
    GalleryPresenterImpl mPresenter;
    @Provides
    @Singleton
    static public CacheProvider provideCacheProvider(File cacheDir)
    {
        return new RxCache.Builder()
                .persistence(cacheDir, new GsonSpeaker())
                .using(CacheProvider.class);
    }

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

    @Provides
    public File provideFile(){
        return Environment.getExternalStorageDirectory();
        //return  ((Activity)mPresenter.getGalleryView()).getApplication().getApplicationContext().getExternalCacheDir();
        //String storagePath = System.getenv("SECONDARY_STORAGE");
        //return new File(storagePath);
    }

}
