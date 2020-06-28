package com.kl.blueberry.events;

/**
 * Created by Kaltrina Lubovci on 28,June,2020
 */
public class ProfileImageChangedEvent {

    String profilePath;

    public ProfileImageChangedEvent(String profilePath) {
        this.profilePath = profilePath;
    }

    public String getProfilePath() {
        return profilePath;
    }
}
