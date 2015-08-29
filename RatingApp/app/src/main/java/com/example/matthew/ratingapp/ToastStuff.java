package com.example.matthew.ratingapp;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Matthew on 8/18/2015.
 */
public class ToastStuff {

    public static void createToast(String message, Context con){
        Toast toast = new Toast(con);
        toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
        // TODO: no toast if no text, adjust the length to something more reasonable
        toast.makeText(con, message, Toast.LENGTH_LONG).show();
    }
}
