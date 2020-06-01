package com.kl.blueberry.model.search_music;

import com.squareup.moshi.Json;

/**
 * Created by Kaltrina Lubovci on 31,May,2020
 */
public class SearchMusicBodyResponse {

    @Json(name = "id")
    int id ;

    @Json(name = "title")
    String title;

    @Json(name = "link")
    String link;

    @Json(name = "preview")
    String preview;

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
}
