package com.kl.blueberry.model.playlist;

import com.squareup.moshi.Json;

/**
 * Created by Kaltrina Lubovci on 14,June,2020
 */
public class PlaylistTracksResponse {
    @Json(name = "data")
    public PlaylistTracksDataResponse[] data;
}
