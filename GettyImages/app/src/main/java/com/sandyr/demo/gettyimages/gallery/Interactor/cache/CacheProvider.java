package com.sandyr.demo.gettyimages.gallery.Interactor.cache;

import com.sandyr.demo.gettyimages.gallery.Interactor.responses.GettyImageResponse;

import java.util.concurrent.TimeUnit;


import io.reactivex.Single;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.LifeCache;
import io.reactivex.Observable;

/**
 * Created by Sandy on 9/12/2017.
 */

public interface CacheProvider {
    @LifeCache(duration = 15, timeUnit = TimeUnit.MINUTES)
    Single <GettyImageResponse> searchGettyImages(Single<GettyImageResponse> gettyImageResponse, DynamicKey query, EvictDynamicKey update);
}
