package com.sandyr.demo.gettyimages.gallery.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sandyr.demo.gettyimages.R;
import com.sandyr.demo.gettyimages.gallery.model.GettyImage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryImageDetails extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_GETTY_IMAGE ="#GETTYIMG" ;
    @BindView(R.id.image_details)
    ImageView imageView;
    @BindView(R.id.title_details)
    TextView title_textview;
    @BindView(R.id.caption_details)
    TextView caption_textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_image_details);
        ButterKnife.bind(this);
        GettyImage gettyImage=getIntentExtras();
        Picasso.with(this).load(gettyImage.getDisplay_size().get(0).getUri())
                .error(R.drawable.ic_placeholder)
                .placeholder(R.drawable.ic_placeholder)
                .into(imageView);

        //Setting text view title
        title_textview.setText(gettyImage.getTitle());
        //Setting text view id
        caption_textview.setText(gettyImage.getCaption());
    }

    public static Intent newIntent(Context context, GettyImage gettyImage){
        Intent intent = new Intent(context, GalleryImageDetails.class);
        intent.putExtra(MAIN_ACTIVITY_GETTY_IMAGE, gettyImage);
        return intent;
    }

    private GettyImage getIntentExtras() {
        GettyImage  data = getIntent().getExtras().getParcelable(MAIN_ACTIVITY_GETTY_IMAGE);
        return data;
    }
}
