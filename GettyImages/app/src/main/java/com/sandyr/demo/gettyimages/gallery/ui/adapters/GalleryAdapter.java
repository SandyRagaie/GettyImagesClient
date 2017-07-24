package com.sandyr.demo.gettyimages.gallery.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sandyr.demo.gettyimages.R;
import com.sandyr.demo.gettyimages.gallery.model.GettyImage;
import com.sandyr.demo.gettyimages.gallery.ui.viewholder.GalleryImageViewHolder;

import java.util.ArrayList;
import java.util.List;


public class GalleryAdapter extends RecyclerView.Adapter<GalleryImageViewHolder> {
    private final ArrayList<GettyImage> imagesList = new ArrayList<>();
    private final GalleryAdapterListener adapterListener;

    public GalleryAdapter(GalleryAdapterListener listener) {
        this.adapterListener = listener;
    }

    @Override
    public GalleryImageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_gallery_adapter_cell, null);
        return new GalleryImageViewHolder(view, adapterListener);
    }

    @Override
    public void onBindViewHolder(GalleryImageViewHolder customViewHolder, int position) {
        customViewHolder.onBind(imagesList.get(position));
    }


    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    @Override
    public void onViewAttachedToWindow(final GalleryImageViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (isAlmostLastItem(holder)) {
            adapterListener.loadNextPageOnEndOfListReached();
        }
    }

    public void insertImages(List<GettyImage> images) {
        int startPosition = imagesList.size();
        int endPosition = startPosition + images.size() - 1;
        imagesList.addAll(images);
        notifyItemRangeInserted(startPosition, endPosition);
    }

    public void removeAllImages() {
        imagesList.clear();
        notifyDataSetChanged();
    }

    public ArrayList<GettyImage> getCachedImages() {
        return imagesList;
    }

    private boolean isAlmostLastItem(GalleryImageViewHolder holder) {
        int adapterPosition = holder.getAdapterPosition();
        if(adapterPosition < 3){
            return false;
        }
        return holder.getAdapterPosition() == (imagesList.size() - 3);
    }

}

