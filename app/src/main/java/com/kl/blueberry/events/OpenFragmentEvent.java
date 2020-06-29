package com.kl.blueberry.events;

import androidx.fragment.app.Fragment;

/**
 * Created by Kaltrina Lubovci on 25,June,2020
 */
public class OpenFragmentEvent {

    String fragmentType;

    public OpenFragmentEvent(String fragmentType) {
        this.fragmentType = fragmentType;
    }

    public String getFragmentType() {
        return fragmentType;
    }
}
