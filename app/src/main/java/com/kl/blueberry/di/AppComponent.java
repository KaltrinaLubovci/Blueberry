package com.kl.blueberry.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dagger.android.support.DaggerApplication;

/**
 * Created by Kaltrina Lubovci on 30,May,2020
 */

@Singleton
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                AppModule.class,
                NetworkModule.class,
                ActivityBuilder.class
        }

)
interface   AppComponent extends AndroidInjector<App> {
    @Component.Builder
    interface Builder{
        @BindsInstance
        public Builder create(Application app);
        public Builder appModule(AppModule appModule);
        public Builder networkModule(NetworkModule networkModule);
        public AppComponent build();
    }
}
