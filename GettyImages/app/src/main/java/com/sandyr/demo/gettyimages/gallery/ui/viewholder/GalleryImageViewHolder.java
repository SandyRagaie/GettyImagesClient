package com.sandyr.demo.gettyimages.gallery.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sandyr.demo.gettyimages.R;
import com.sandyr.demo.gettyimages.gallery.model.GettyImage;
import com.sandyr.demo.gettyimages.gallery.ui.adapters.GalleryAdapterListener;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;

public class GalleryImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ImageView imageView;
    private TextView titleTextView;
    private TextView idTextView;
    private GalleryAdapterListener adapterListener;

    public GalleryImageViewHolder(View view, GalleryAdapterListener listener) {
        super(view);
        adapterListener = listener;
        this.imageView = ButterKnife.findById(view, R.id.gallery_item_image);
        this.titleTextView = ButterKnife.findById(view, R.id.gallery_item_title);
        this.idTextView = ButterKnife.findById(view, R.id.gallery_item_image_id);
        view.setOnClickListener(this);
    }

    public void onBind(GettyImage gettyImage) {

        if (gettyImage.getDisplay_size() != null && gettyImage.getDisplay_size().size() > 0) {
            //Download image using picasso library
            Picasso.with(itemView.getContext()).load(gettyImage.getDisplay_size().get(0).getUri())
                    .error(R.drawable.ic_placeholder)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(imageView);
        }
        //Setting text view title
        titleTextView.setText(gettyImage.getTitle());
        //Setting text view id
        idTextView.setText(gettyImage.getId());
    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        adapterListener.onImageClick(position);
    }
}