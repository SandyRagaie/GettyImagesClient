package com.sandyr.demo.gettyimages.gallery.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sandyr.demo.gettyimages.R;
import com.sandyr.demo.gettyimages.gallery.model.GettyImage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.CustomViewHolder> {
    private final ArrayList<GettyImage> imagesList = new ArrayList<>();
    private final GalleryAdapterListener adapterListener;

    public GalleryAdapter(GalleryAdapterListener listener) {
        this.adapterListener = listener;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_gallery_adapter_cell, null);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {
        customViewHolder.onBind(customViewHolder, position);
    }


    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected ImageView imageView;
        protected TextView titleTextView;
        protected TextView idTextView;


        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) ButterKnife.findById(view, R.id.gallery_item_image);
            this.titleTextView = (TextView) ButterKnife.findById(view, R.id.gallery_item_title);
            this.idTextView = (TextView) ButterKnife.findById(view, R.id.gallery_item_image_id);
            view.setOnClickListener(this);
        }

        public void onBind(CustomViewHolder customViewHolder, int position) {
            GettyImage gettyImage = imagesList.get(position);
            if (gettyImage.getDisplay_size() != null && gettyImage.getDisplay_size().size() > 0) {
                //Download image using picasso library
                Picasso.with(customViewHolder.itemView.getContext()).load(gettyImage.getDisplay_size().get(0).getUri())
                        .error(R.drawable.ic_placeholder)
                        .placeholder(R.drawable.ic_placeholder)
                        .into(customViewHolder.imageView);
            }
            //Setting text view title
            customViewHolder.titleTextView.setText(gettyImage.getTitle());
            //Setting text view id
            customViewHolder.idTextView.setText(gettyImage.getId());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            adapterListener.onImageClick(position);
        }
    }

    @Override
    public void onViewAttachedToWindow(final CustomViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (isLastItem(holder)) {
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

    private boolean isLastItem(CustomViewHolder holder) {
        return holder.getAdapterPosition() == imagesList.size() - 1;
    }

    GettyImage getItem(int position) {
        return imagesList.get(position);
    }

}

