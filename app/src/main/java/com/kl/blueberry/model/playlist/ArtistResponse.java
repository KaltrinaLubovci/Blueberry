package com.kl.blueberry.model.playlist;

import com.squareup.moshi.Json;

/**
 * Created by Kaltrina Lubovci on 14,June,2020
 */
public class ArtistResponse {
    @Json(name = "name")
    String name = null;

    public String getName() {
        return name;
    }
}
