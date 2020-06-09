package com.kl.blueberry.ui;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.kl.blueberry.api.ApiService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Kaltrina Lubovci on 01,June,2020
 */
public class MainViewModel extends ViewModel {
    Disposable disposable;
    void search(Context context, ApiService apiService, String singerName) {
        disposable = apiService.searchSinger("api_key_goes_here", "eminem")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    Toast.makeText(context, "Success" + result.data[0].getTitle(), Toast.LENGTH_SHORT).show();
                }, error -> {
                    error.getStackTrace();
                    Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
                });

    }
}
