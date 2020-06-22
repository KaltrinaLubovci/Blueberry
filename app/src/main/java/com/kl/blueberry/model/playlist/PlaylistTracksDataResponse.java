package com.kl.blueberry.model.playlist;

import com.squareup.moshi.Json;

/**
 * Created by Kaltrina Lubovci on 14,June,2020
 */
public class PlaylistTracksDataResponse {

    @Json(name = "id")
    int id;

    @Json(name = "title")
    String title;

    @Json(name = "link")
    String link;

    @Json(name = "preview")
    String preview;

    @Json(name = "artist")
    ArtistResponse artist = null;

    Boolean isPlaying = false;

    public Boolean getPlaying() {
        return isPlaying;
    }

    public void setPlaying(Boolean playing) {
        isPlaying = playing;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getPreview() {
        return preview;
    }

    public ArtistResponse getArtist() {
        return artist;
    }
}
