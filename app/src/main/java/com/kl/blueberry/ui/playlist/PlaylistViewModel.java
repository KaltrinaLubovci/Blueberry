package com.kl.blueberry.ui.playlist;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kl.blueberry.api.ApiService;
import com.kl.blueberry.model.playlist.PlaylistTracksDataResponse;

import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Kaltrina Lubovci on 15,June,2020
 */
public class PlaylistViewModel extends ViewModel {
    Disposable disposable;

    MutableLiveData<String> playlistTitle = new MutableLiveData<>();
    MutableLiveData<String> playlistImage = new MutableLiveData<>();
    MutableLiveData<Boolean> loading = new MutableLiveData<>();
    MutableLiveData<List<PlaylistTracksDataResponse>> playlistTracks = new MutableLiveData<>();

    void getMainPlaylist(ApiService apiService) {


        disposable = apiService.mainPlaylist("api_key_goes_here")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(onSubscribe -> {
                    loading.setValue(true);
                })
                .doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        loading.setValue(false);
                    }
                })
                .subscribe(result -> {
                    playlistTitle.setValue(result.getTitle());
                    playlistImage.setValue(result.getPicture_medium());

                    PlaylistTracksDataResponse[] playlistTracksList = result.getTracks().data;
                    playlistTracks.setValue(Arrays.asList(playlistTracksList));

                }, error -> {
                    error.getStackTrace();
//                    Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
                });

    }
}
