package com.kl.blueberry.model.playlist;

import com.squareup.moshi.Json;

/**
 * Created by Kaltrina Lubovci on 14,June,2020
 */
public class PlaylistResponse {

    @Json(name = "title")
    String title = null;

    @Json(name = "link")
    String link = null;

    @Json(name = "picture_big")
    String picture_big = null;

    @Json(name = "tracks")
    PlaylistTracksResponse tracks = null;

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getPicture_medium() {
        return picture_big;
    }

    public PlaylistTracksResponse getTracks() {
        return tracks;
    }
}
