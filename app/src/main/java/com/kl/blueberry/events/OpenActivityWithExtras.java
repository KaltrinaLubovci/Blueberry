package com.kl.blueberry.events;

import android.app.Activity;

/**
 * Created by Kaltrina Lubovci on 22,June,2020
 */
public class OpenActivityWithExtras {

    String message;
    Activity activity;

    public OpenActivityWithExtras(String message, Activity activity) {
        this.message = message;
        this.activity = activity;
    }

    public String getMessage() {
        return message;
    }

    public Activity getActivity() {
        return activity;
    }
}
