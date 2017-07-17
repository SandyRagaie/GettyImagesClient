package com.sandyr.demo.gettyimages.gallery.ui.adapters;

import android.graphics.drawable.Drawable;

/**
 * Created by sandyr on 7/16/2017.
 */
public interface GalleryAdapterListener {

    void onImageClick(int position);

    void onEndList();
}
