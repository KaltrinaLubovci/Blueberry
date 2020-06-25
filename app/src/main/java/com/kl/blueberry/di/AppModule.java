package com.kl.blueberry.di;

import android.app.Application;

import com.kl.blueberry.data.AppPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Kaltrina Lubovci on 30,May,2020
 */
@Module
public class AppModule {

    @Provides
    @Singleton
    static public AppPreferences provideSharedPreference(Application app){
        return new AppPreferences(app.getApplicationContext());
    }
}
