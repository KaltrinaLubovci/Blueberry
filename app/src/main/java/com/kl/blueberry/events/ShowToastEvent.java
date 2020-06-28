package com.kl.blueberry.events;

/**
 * Created by Kaltrina Lubovci on 27,June,2020
 */
public class ShowToastEvent {
    String message;

    public ShowToastEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
