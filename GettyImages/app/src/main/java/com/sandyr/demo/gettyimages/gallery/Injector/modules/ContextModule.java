package com.sandyr.demo.gettyimages.gallery.Injector.modules;

/**
 * Created by Sandy on 9/12/2017.
 */

import dagger.Module;
import dagger.Provides;

    @Module
    public class ContextModule {
        private android.content.Context context;

        public ContextModule(android.content.Context context) {
            this.context = context;
        }

        @Provides
        android.content.Context provideContext() {
            return context;
        }
    }

