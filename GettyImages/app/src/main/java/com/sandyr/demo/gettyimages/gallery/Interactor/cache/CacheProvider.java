package com.sandyr.demo.gettyimages.gallery.Interactor.cache;

import com.sandyr.demo.gettyimages.gallery.Interactor.responses.GettyImageResponse;

import java.util.concurrent.TimeUnit;


import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.LifeCache;
import rx.Observable;

/**
 * Created by Sandy on 9/12/2017.
 */

public interface CacheProvider {
    @LifeCache(duration = 15, timeUnit = TimeUnit.MINUTES)
    Observable<GettyImageResponse> searchGettyImages(Observable<GettyImageResponse> gettyImageResponse, DynamicKey query, EvictDynamicKey update);
}
