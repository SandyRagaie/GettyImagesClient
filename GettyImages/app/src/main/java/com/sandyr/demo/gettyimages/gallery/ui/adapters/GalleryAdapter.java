package com.sandyr.demo.gettyimages.gallery.ui.adapters;

import android.content.Context;
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
    private final Context mContext;
    private List<GettyImage> imagesList = new ArrayList<>();
    private final GalleryAdapterListener adapterListener;

    public GalleryAdapter(Context context, GalleryAdapterListener listener) {
        this.adapterListener = listener;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gallery_adapter_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {
        GettyImage gettyImage = imagesList.get(position);
        //Download image using picasso library
        Picasso.with(mContext).load(gettyImage.getDisplay_size().get(0).getUri())
                    .error(R.drawable.ic_placeholder)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(customViewHolder.imageView);

        //Setting text view title
        customViewHolder.titleTextView.setText(gettyImage.getTitle());
        //Setting text view id
        customViewHolder.idTextView.setText(gettyImage.getId());

    }


    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView titleTextView;
        protected TextView idTextView;


        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) ButterKnife.findById(view, R.id.gallery_item_image);
            this.titleTextView = (TextView) ButterKnife.findById(view, R.id.gallery_item_title);
            this.idTextView = (TextView) ButterKnife.findById(view, R.id.gallery_item_image_id);
        }
    }

    @Override
    public void onViewAttachedToWindow(final CustomViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (isLastItem(holder)) {
            adapterListener.onEndList();
        }
    }

    public void InsertImages(List<GettyImage> images) {
        int startPosition = imagesList.size();
        int endPosition = startPosition + images.size() - 1;
        imagesList.addAll(images);
        notifyItemRangeInserted(startPosition, endPosition);
    }

    public void removeAllImages() {
        imagesList.clear();
        notifyDataSetChanged();
    }

    private boolean isLastItem(CustomViewHolder holder) {
        return holder.getAdapterPosition() == imagesList.size() - 1;
    }

    GettyImage getItem(int position) {
        return imagesList.get(position);
    }

}

