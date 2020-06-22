package com.kl.blueberry.events;

import android.app.Activity;

import com.kl.blueberry.model.playlist.PlaylistTracksDataResponse;

import java.util.ArrayList;

/**
 * Created by Kaltrina Lubovci on 15,June,2020
 */
public class SendDataEvent {
    private Activity activity;
    private String playlistName;
    private ArrayList<PlaylistTracksDataResponse> playListTracksArrList;

    public SendDataEvent(Activity activity, String playlistName, ArrayList<PlaylistTracksDataResponse> playListTracksArrList) {
        this.activity = activity;
        this.playlistName = playlistName;
        this.playListTracksArrList = playListTracksArrList;
    }

    public Activity getActivity() {
        return activity;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public ArrayList<PlaylistTracksDataResponse> getPlayListTracksArrList() {
        return playListTracksArrList;
    }
}
