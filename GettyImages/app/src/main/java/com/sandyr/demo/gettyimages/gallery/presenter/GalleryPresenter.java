package com.sandyr.demo.gettyimages.gallery.presenter;

import android.content.Context;

/**
 * Created by sandyr on 2/23/2017.
 */

public interface GalleryPresenter {
    void onDestroy();

    public void loadNextPage();

    public void searchGettyImages(String searchPhrase);
}
