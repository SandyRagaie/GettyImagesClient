package com.sandyr.demo.gettyimages;

import com.sandyr.demo.gettyimages.gallery.model.DisplaySize;
import com.sandyr.demo.gettyimages.gallery.model.GettyImage;
import com.sandyr.demo.gettyimages.gallery.presenter.GalleryPresenterImpl;
import com.sandyr.demo.gettyimages.gallery.view.GalleryView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GalleryPresenterTest {

    @Mock
    public GalleryView view;
    @Mock
    public GalleryPresenterImpl presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new GalleryPresenterImpl();
        //presenter.setContext(view);
    }


    @Test
    public void checkIfViewIsReleasedOnDestroy() {
        presenter.onDestroy();
        assertNull(presenter.getGalleryView());
    }

    @Test
    public void checkIfItemsArePassedToView() {
        ArrayList<GettyImage> items = new ArrayList<GettyImage>();
        GettyImage item = new GettyImage();
        item.setId("test id");
        item.setTitle("test image title");
        item.setCaption("test caption");

        DisplaySize displaySize=new DisplaySize();
        displaySize.setUri("https://media.gettyimages.com/photos/models-show-samsung-electronics-cos-android-smartphones-during-the-picture-id96402674?b=1&k=6&m=96402674&s=170x170&h=N07yBNCzfyZdKO9ahoSVfass0L9BrUry8Z2-oOlrHDg=");
        ArrayList<DisplaySize> arrayList= new ArrayList<DisplaySize>();
        arrayList.add(displaySize);
        item.setDisplay_size(arrayList);
        items.add(item);
        presenter.getGalleryView().onLoadImagesByPhraseSuccess(items);
        verify(view, times(1)).onLoadImagesByPhraseSuccess(items);
    }
}
