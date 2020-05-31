package com.kl.blueberry.di;


import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * Created by Kaltrina Lubovci on 30,May,2020
 */
public class App extends DaggerApplication {

    public static App instance;



    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent
                .builder()
                .create(this)
                .appModule(new AppModule())
                .networkModule(new NetworkModule())
                .build();
    }
}
