package com.sandyr.demo.gettyimages.gallery.presenter;


import com.sandyr.demo.gettyimages.gallery.Interactor.Services.GettyImageService;
import com.sandyr.demo.gettyimages.gallery.model.GettyImage;
import com.sandyr.demo.gettyimages.gallery.Interactor.responses.GettyImageResponse;
import com.sandyr.demo.gettyimages.gallery.view.GalleryView;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sandyr on 2/23/2017.
 */

public class GalleryPresenterImpl implements GalleryPresenter {
    public Subscription subscription;
    private GalleryView galleryView;
    private final int pageSize;
    private String phrase;
    private int page;
    private ArrayList<GettyImage> gettyImages;

    @Inject
    GettyImageService mService;

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
        if(subscription != null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
        galleryView = null;
    }

    @Override
    public void loadNextPage() {
        Observer<GettyImageResponse> myObserver = new Observer<GettyImageResponse>() {

            @Override
            public void onCompleted() {
                if (gettyImages != null) {
                    page++;
                    galleryView.onLoadImagesByPhraseSuccess(gettyImages);
                }
                galleryView.hideProgress();
            }

            @Override
            public void onError(Throwable e) {
                galleryView.onLoadImagesByPhraseError(e.getMessage());
                galleryView.hideProgress();
            }

            @Override
            public void onNext(GettyImageResponse gettyImageResponse) {
                if (gettyImageResponse.getItems().size() == 0) {
                    galleryView.onEmptyResult();
                } else {

                    gettyImages = gettyImageResponse.getItems();
                }
            }

        };

        subscription = mService.getGettyImages(page, pageSize, phrase)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(myObserver);
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
