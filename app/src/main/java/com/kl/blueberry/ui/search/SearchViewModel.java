package com.kl.blueberry.ui.search;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kl.blueberry.api.ApiService;
import com.kl.blueberry.model.search_music.SearchMusicDataResponse;

import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Kaltrina Lubovci on 22,June,2020
 */
public class SearchViewModel extends ViewModel {
    Disposable disposable;

    MutableLiveData<List<SearchMusicDataResponse>> musicResponseList = new MutableLiveData<>();

    void search(Context context, ApiService apiService, String singerName) {
        disposable = apiService.searchSinger("api_key_goes_here", singerName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    Toast.makeText(context, "Success" + result.data[0].getTitle(), Toast.LENGTH_SHORT).show();
                    SearchMusicDataResponse[] playlistTracksList = result.data;
                    musicResponseList.setValue(Arrays.asList(playlistTracksList));
                }, error -> {
                    error.getStackTrace();
                    Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
                });

    }

}
