package com.sandyr.demo.gettyimages.gallery.Injector;

import android.app.Application;

import com.sandyr.demo.gettyimages.gallery.Injector.modules.GalleryPresenterModule;
import com.sandyr.demo.gettyimages.gallery.ui.activity.GalleryActivity;

import javax.inject.Singleton;
import dagger.Component;
/**
 * Created by sandyr on 7/16/2017.
 */

public class GalleryApplication extends Application {

    private ApplicationComponent component;

    /**
     * Newly added modules must be added to the @Component annotation here. You must also provide
     * further inject() methods for new classes that want to perform injection.
     */
    @Singleton
    @Component(modules = {GalleryPresenterModule.class})
    public interface ApplicationComponent {
        void inject(GalleryActivity galleryActivity);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // This setups up the component which is used by other views (activities/fragments/etc., not Android views) for injection.
        // This pulls all modules which have statically declared @Provides methods automatically.
        component = DaggerGalleryApplication_ApplicationComponent.builder().build();
    }

    public ApplicationComponent component() {
        if(component==null){
            component = DaggerGalleryApplication_ApplicationComponent.builder().build();
        }
        return component;
    }
}
