package com.kl.blueberry.di;

import com.kl.blueberry.ui.MainActivity;
import com.kl.blueberry.ui.home.HomeFragment;
import com.kl.blueberry.ui.playlist.PlaylistActivity;
import com.kl.blueberry.ui.search.SearchActivity;
import com.kl.blueberry.ui.signin.SignInActivity;
import com.kl.blueberry.ui.splash_screen.SplashScreenActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Kaltrina Lubovci on 30,May,2020
 */
@Module
abstract class ActivityBuilder {

    //put here activities that use @Inject

    @ContributesAndroidInjector
    abstract SplashScreenActivity bindSplashScreen();

    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract HomeFragment bindHomeFragment();

    @ContributesAndroidInjector
    abstract PlaylistActivity bindPlaylistActivity();

    @ContributesAndroidInjector
    abstract SearchActivity bindSearchActivity();

    @ContributesAndroidInjector
    abstract SignInActivity bindSignInActivity();

}
