package com.kl.blueberry.model.search_music;

import com.squareup.moshi.Json;

/**
 * Created by Kaltrina Lubovci on 22,June,2020
 */
public class AlbumResponse {
    @Json(name = "cover_medium")
    String cover_medium = null;

    public String getCover_medium() {
        return cover_medium;
    }
}
