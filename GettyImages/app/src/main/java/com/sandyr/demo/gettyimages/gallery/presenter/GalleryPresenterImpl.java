package com.sandyr.demo.gettyimages.gallery.presenter;


import com.sandyr.demo.gettyimages.gallery.Injector.modules.InteractorModule;
import com.sandyr.demo.gettyimages.gallery.Interactor.Services.GettyImageService;
import com.sandyr.demo.gettyimages.gallery.Interactor.cache.CacheProvider;
import com.sandyr.demo.gettyimages.gallery.model.GettyImage;
import com.sandyr.demo.gettyimages.gallery.Interactor.responses.GettyImageResponse;
import com.sandyr.demo.gettyimages.gallery.view.GalleryView;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.reactivex.android.schedulers.AndroidSchedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by sandyr on 2/23/2017.
 */

public class GalleryPresenterImpl implements GalleryPresenter {
    public Disposable subscription;
    private GalleryView galleryView;
    private final int pageSize;
    private String phrase;
    private int page;
    private ArrayList<GettyImage> gettyImages;

    @Inject
    GettyImageService mService;

    @Inject
    CacheProvider mCacheProvider;
    @Inject
    InteractorModule mInteractorModule;


    @Inject
    public GalleryPresenterImpl() {
        pageSize = 10;
        phrase = null;
    }

    public void setView(GalleryView view) {
        galleryView = view;
    }

    @Override
    public void onDestroy() {
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }
        galleryView = null;
    }

    @Override
    public void loadNextPage() {
        mInteractorModule.searchGettyImages(mCacheProvider, mService, page, pageSize, phrase)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GettyImageResponse>() {
                    @Override
                    public void accept(@NonNull GettyImageResponse gettyImageResponse) throws Exception {
                        if (gettyImageResponse.getItems().size() == 0) {
                            galleryView.onEmptyResult();
                        } else {

                            gettyImages = gettyImageResponse.getItems();
                        }
                        if (gettyImages != null) {
                            page++;
                            galleryView.onLoadImagesByPhraseSuccess(gettyImages);
                        }
                        galleryView.hideProgress();
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        galleryView.onLoadImagesByPhraseError();
                        galleryView.hideProgress();
                        e.printStackTrace();
                    }
                });

        galleryView.showProgress();


    }

    /**
     * initiate server request
     *
     * @param searchPhrase
     */
    @Override
    public void searchGettyImages(String searchPhrase) {
        if (gettyImages != null) {
            gettyImages.clear();
            galleryView.onRemoveImagesFromUI();
        }
        page = 1;
        phrase = searchPhrase;
        loadNextPage();
    }

    /**
     * get view instance
     *
     * @return
     */
    public GalleryView getGalleryView() {
        return galleryView;
    }
}
