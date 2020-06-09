package com.kl.blueberry.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Kaltrina Lubovci on 09,June,2020
 */

public interface Usage {
    static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
