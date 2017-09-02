package com.sandyr.demo.gettyimages.gallery.Interactor.responses;

import com.sandyr.demo.gettyimages.gallery.model.GettyImage;

import java.util.ArrayList;

/**
 * Created by sandyr on 7/14/2017.
 */
public class GettyImageResponse {
    private ArrayList<GettyImage> images;

    public ArrayList<GettyImage> getItems() {
        return images;
    }

    public void setItems(ArrayList<GettyImage> items) {
        this.images = items;
    }
}
