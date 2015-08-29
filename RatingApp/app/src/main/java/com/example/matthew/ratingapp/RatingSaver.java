package com.example.matthew.ratingapp;
import android.content.Context;

import java.io.*;
import java.io.FileOutputStream;

/**
 * Created by Matthew on 8/18/2015.
 */

// TODO: use inheritance/polymorphism with RatingReader/RatingSaver so you don't do FILE_NAME twice
public class RatingSaver {

    public static String FILE_NAME = "saveData";

    public static void saveRating(float fun, float learn, Context app){
        try {
            FileOutputStream fos = app.openFileOutput(FILE_NAME, Context.MODE_APPEND);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(fun + "");
            bw.newLine();
            bw.write(learn + "");
            bw.newLine();
            bw.close();
        }catch (Exception e){
            ToastStuff.createToast("An Error Occured While Saving Data!", app);
            ToastStuff.createToast(e.getMessage(), app);
        }
    }

}
