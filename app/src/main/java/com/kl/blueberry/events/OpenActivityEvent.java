package com.kl.blueberry.events;

import android.app.Activity;

/**
 * Created by Kaltrina Lubovci on 09,June,2020
 */
public class OpenActivityEvent {

    private Activity activity;

    public Activity getActivity() {
        return activity;
    }

    public OpenActivityEvent(Activity activity) {
        this.activity = activity;
    }
}
