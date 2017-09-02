package com.sandyr.demo.gettyimages.gallery.presenter;


/**
 * Created by sandyr on 2/23/2017.
 */

public interface GalleryPresenter {
    void onDestroy();

    void loadNextPage();

    void searchGettyImages(String searchPhrase);
}
