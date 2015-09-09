package com.example.matthew.ratingapp;

import android.content.Context;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class RatingSaver {
    public static void saveRating(float fun, float learn, Context app){
        try {
            FileOutputStream fos = app.openFileOutput(Data.FILE_NAME, Context.MODE_APPEND);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(Float.toString(fun));
            bw.newLine();
            bw.write(Float.toString(learn));
            bw.newLine();
            bw.close();
        }catch (Exception e){
            ToastStuff.createToast(Data.DATA_WRITE_ERROR, app);
            ToastStuff.createToast(e.getMessage(), app);
        }
    }

}
