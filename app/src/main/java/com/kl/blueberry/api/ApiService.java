package com.kl.blueberry.api;

import com.kl.blueberry.model.playlist.PlaylistResponse;
import com.kl.blueberry.model.search_music.SearchMusicResponse;

import io.reactivex.Observable;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by Kaltrina Lubovci on 30,May,2020
 */
public interface ApiService {

    @GET("/search")
    public Observable<SearchMusicResponse> searchSinger( @Header("x-rapidapi-key") String rapidApiKey, @Query("q") String singerName);

    @GET("/playlist/1963962142")
    public Observable<PlaylistResponse> mainPlaylist(@Header("x-rapidapi-key") String rapidApiKey);

}
