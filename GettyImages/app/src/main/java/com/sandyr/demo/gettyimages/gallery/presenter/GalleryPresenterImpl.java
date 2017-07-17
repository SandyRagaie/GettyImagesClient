package com.sandyr.demo.gettyimages.gallery.presenter;

import android.content.Context;

import com.sandyr.demo.gettyimages.gallery.model.GettyImage;
import com.sandyr.demo.gettyimages.gallery.Interactor.RequestManager;
import com.sandyr.demo.gettyimages.gallery.Interactor.responses.GettyImageResponse;
import com.sandyr.demo.gettyimages.gallery.view.GalleryView;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;

/**
 * Created by sandyr on 2/23/2017.
 */

public class GalleryPresenterImpl implements GalleryPresenter {
    private RequestManager manager;
    private Subscription subscription;
    private GalleryView galleryView;
    private final int pageSize;
    private int page;
    private ArrayList<GettyImage> gettyImages;
    @Inject
    public GalleryPresenterImpl() {
        pageSize = 10;
        page=1;
        manager = new RequestManager();
    }

    public void setView(GalleryView view){
        galleryView = view;
    }

    @Override
    public void onDestroy() {
        subscription.unsubscribe();
        galleryView = null;
    }

    /**
     * get view instance
     * @return
     */
    public GalleryView getGalleryView() {
        return galleryView;
    }

    /**
     * this method should be called for onSaveInstanceState only
     * @return
     */
    public ArrayList<GettyImage> getCachedImages(){
    if (gettyImages == null) {
        gettyImages = new ArrayList<GettyImage>();
    }
    return gettyImages;
}
    /**
     * This method called when user clicks search button in softkeyboard
     * @param pageNumber
     * @param phrase
     */
    public void getGettyImages(int pageNumber ,final String phrase) {
        page=pageNumber;
        if(gettyImages!=null) {
            gettyImages.clear();
            galleryView.onRemoveImagesFromUI();
        }
        getGettyImages(phrase);
    }

    /**
     * initiate server request
     * @param phrase
     */
    public void getGettyImages(final String phrase) {
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
                if(gettyImageResponse.getItems().size()==0){
                    galleryView.onEmptyResult();
                }else {
                    if (gettyImages == null) {
                        gettyImages = new ArrayList<GettyImage>();
                    }
                    gettyImages.addAll(gettyImageResponse.getItems());
                }
            }

        };
        subscription = manager.retrieveGettyImages(myObserver, page,pageSize, phrase);
        galleryView.showProgress();
    }
}
