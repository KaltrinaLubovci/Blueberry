package com.kl.blueberry.model.search_music;

import com.squareup.moshi.Json;

/**
 * Created by Kaltrina Lubovci on 31,May,2020
 */
public class SearchMusicResponse {
    @Json(name = "data")
    public SearchMusicDataResponse[] data;
}
