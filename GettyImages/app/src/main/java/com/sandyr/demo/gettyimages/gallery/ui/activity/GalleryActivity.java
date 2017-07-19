package com.sandyr.demo.gettyimages.gallery.ui.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sandyr.demo.gettyimages.R;
import com.sandyr.demo.gettyimages.gallery.model.GettyImage;
import com.sandyr.demo.gettyimages.common.util.SoftKeyboardUtil;
import com.sandyr.demo.gettyimages.gallery.Injector.DaggerGalleryApplication_ApplicationComponent;
import com.sandyr.demo.gettyimages.gallery.presenter.GalleryPresenterImpl;
import com.sandyr.demo.gettyimages.gallery.ui.adapters.GalleryAdapter;
import com.sandyr.demo.gettyimages.gallery.ui.adapters.GalleryAdapterListener;
import com.sandyr.demo.gettyimages.gallery.view.GalleryView;


import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GalleryActivity extends AppCompatActivity implements GalleryView, GalleryAdapterListener, TextView.OnEditorActionListener {
    @Inject
    GalleryPresenterImpl mPresenter;
    @BindView(R.id.images_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.progress)
    ProgressBar progress;
    GalleryAdapter mAdapter;
    Unbinder unbinder;
    private static final String SAVED_INSTANCE_DATA = "#SAVED_INSTANCE_DATA";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        unbinder = ButterKnife.bind(this);
        /* The component setup in the GalleryApplication takes all Module classes and fills in @Inject
         * annotated fields for you automatically.*/
        DaggerGalleryApplication_ApplicationComponent.builder().build().inject(this);

        mPresenter.setView(this);
        search.setOnEditorActionListener(this);

        initRecyclerView();
    }

    /**
     * initialize recycler view
     * Add Grid manager
     * TODO: different screen sizes must be considers while initialize GridLayoutManager
     */
    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                SoftKeyboardUtil.getInstance(GalleryActivity.this).hideSoftKeyboard();
            }
        });
        GridLayoutManager lLayout = new GridLayoutManager(GalleryActivity.this, 2);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(lLayout);
        mAdapter = new GalleryAdapter(GalleryActivity.this);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void onLoadImagesByPhraseSuccess(ArrayList<GettyImage> images) {
        mAdapter.insertImages(images);
    }

    @Override
    public void onLoadImagesByPhraseError(String result) {
        String message = getString(R.string.error_downloading);
        Snackbar snackbar = Snackbar
                .make(mRecyclerView, message, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPresenter.loadNextPage();
                    }
                });
        snackbar.show();
    }

    @Override
    public void onEmptyResult() {
        String message = getString(R.string.warning_empty_result);
        Snackbar snackbar = Snackbar.make(mRecyclerView, message, Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
    }

    @Override
    public void onRemoveImagesFromUI() {
        mAdapter.removeAllImages();
    }

    @Override
    public void onImageClick(int position) {
        GettyImage data = mAdapter.getCachedImages().get(position);
        Intent intent = GalleryImageDetailsActivity.newIntent(GalleryActivity.this, data);
        startActivity(intent);
    }

    @Override
    public void loadNextPageOnEndOfListReached() {
        mPresenter.loadNextPage();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (actionId) {
            case EditorInfo.IME_ACTION_SEARCH:
                SoftKeyboardUtil.getInstance(GalleryActivity.this).hideSoftKeyboard();
                String phrase = v.getText().toString();
                if (phrase.length() == 0) {
                    //clear Images list from UI
                    mPresenter.getGalleryView().onRemoveImagesFromUI();
                } else {
                    mPresenter.searchGettyImages(phrase);
                }
                return true;
            default:
                return false;


        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(SAVED_INSTANCE_DATA, mAdapter.getCachedImages());
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ArrayList<GettyImage> images = savedInstanceState.getParcelableArrayList(SAVED_INSTANCE_DATA);
        mPresenter.getGalleryView().onLoadImagesByPhraseSuccess(images);
        SoftKeyboardUtil.getInstance(GalleryActivity.this).hideSoftKeyboard();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
        unbinder.unbind();
    }
}
