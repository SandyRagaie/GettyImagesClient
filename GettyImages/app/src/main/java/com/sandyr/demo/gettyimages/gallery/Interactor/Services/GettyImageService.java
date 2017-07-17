package com.sandyr.demo.gettyimages.gallery.Interactor.Services;

import com.sandyr.demo.gettyimages.gallery.Interactor.responses.GettyImageResponse;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by sandyr on 7/14/2017.
 */

public interface GettyImageService {
    @Headers("Api-Key:fh2qcnms3uc4hcmyh5y7np6h")

    @GET("/v3/search/images")
    Observable<GettyImageResponse> getGettyImages(@Query("page") int page,
                                                  @Query("page_size") int pageSize,
                                                  @Query("phrase") String phrase
                                                   /*@Query("Authorization") String token*/);

}
