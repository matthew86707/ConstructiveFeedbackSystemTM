package com.example.matthew.ratingapp;

import android.view.View;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by Matthew on 8/18/2015.
 */

public class RatingReader {

    public static void readRatings(View v) {
        try {
            FileInputStream in = MainActivity.context.openFileInput(Data.FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
                sb.append(" : ");
            }
            ToastStuff.createToast(sb.toString() + "", MainActivity.context);
        } catch (Exception e) {
            ToastStuff.createToast(Data.DATA_READ_ERROR, MainActivity.context);
            ToastStuff.createToast(e.getMessage(), MainActivity.context);
        }
    }
}
