package com.kl.blueberry.model.search_music;

import com.kl.blueberry.model.playlist.ArtistResponse;
import com.kl.blueberry.ui.search.AlbumResponse;
import com.squareup.moshi.Json;

/**
 * Created by Kaltrina Lubovci on 31,May,2020
 */
public class SearchMusicDataResponse {

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

    @Json(name = "album")
    AlbumResponse album = null;

    Boolean isPlaying = false;

    public AlbumResponse getAlbum() {
        return album;
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

    public Boolean getPlaying() {
        return isPlaying;
    }

    public void setPlaying(Boolean playing) {
        isPlaying = playing;
    }
}
