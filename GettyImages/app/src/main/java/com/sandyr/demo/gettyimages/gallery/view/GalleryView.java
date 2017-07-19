package com.sandyr.demo.gettyimages.gallery.view;

import com.sandyr.demo.gettyimages.gallery.model.GettyImage;

import java.util.ArrayList;

/**
 * Created by sandyr on 7/14/2017.
 */

public interface GalleryView {
    /**
     * Show progress
     */
    void showProgress();

    /**
     * hide progress
     */
    void hideProgress();

    /**
     * called when images received from server
     *
     * @param images
     */
    void onLoadImagesByPhraseSuccess(ArrayList<GettyImage> images);

    /**
     * called when error received from server
     *
     * @param result
     */
    void onLoadImagesByPhraseError(String result);

    /**
     * called when empty result received from server
     */
    void onEmptyResult();

    /**
     * called when ui clear needed
     */
    void onRemoveImagesFromUI();
}
