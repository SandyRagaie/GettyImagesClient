package com.sandyr.demo.gettyimages.gallery.Injector.modules;


import android.os.Environment;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.sandyr.demo.gettyimages.BuildConfig;
import com.sandyr.demo.gettyimages.gallery.Interactor.Services.GettyImageService;
import com.sandyr.demo.gettyimages.gallery.Interactor.cache.CacheProvider;
import com.sandyr.demo.gettyimages.gallery.Interactor.responses.GettyImageResponse;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sandyr on 7/17/2017.
 */
@Module(includes = {ContextModule.class})
public class InteractorModule {
    @Inject
public InteractorModule(){

}

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
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(new OkHttpClient.Builder().build())
                .build();
    }
    public Single<GettyImageResponse> searchGettyImages(CacheProvider cacheProvider, GettyImageService service, int page, int pageSize, String phrase) {
        return cacheProvider.searchGettyImages(service.getGettyImages(page,pageSize,phrase), new DynamicKey(phrase+pageSize), new EvictDynamicKey(false))
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io());

        //return retrofit.create(GettyImageService.class);
    }
    @Provides
    static public GettyImageService provideGettyImageService(Retrofit retrofit) {
        return retrofit.create(GettyImageService.class);
    }

    @Provides
    public File provideFile(android.content.Context context){
        return context.getFilesDir();
    }

}
