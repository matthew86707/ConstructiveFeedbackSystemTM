//2015 Wintress Technical Schools / League Of Amazing Programmers
//Developed by Matthew Smith and Russ Baxt (Will ask about spelling of last name)
//Version 0.1 - GUI TEST

package com.example.matthew.ratingapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.*;


public class MainActivity extends Activity {

    RatingBar ratingFun;
    RatingBar ratingLearn;

    public static Context context;

    //This is just for a small easter egg im putting in...

    // TODO: refactor this code so it looks more like
    // https://chromium.googlesource.com/chromium/blink/+/c560c44b38bd4769ae6c5bc314fd4a50a264891a/Source/core/page/FrameView.cpp
    // LINE: 162

    public String badRatings[] = {"Please Provide A Rating",
                              "Come on, it takes 2 seconds",
                                                   "PLEASE",
                     "Okay Now You're getting on my nerves",
                                   "You're wasting my time",
                     "Why am I even writing these messages",
                "Its not like anyone is going to find them",
                                    "Im a bored programmer",
                        "Hiding secret messages in apps...",
                                         "You know what...",
                                                  "IM DONE",
                                 "RATE YOUR CLASS ALREADY!",
            "Do you want to know how many button presses you have made?",
                    "Well I have a variable for that too..."};

    public int badRatingNum = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        ratingFun = (RatingBar) findViewById(R.id.ratingBar);
        ratingLearn = (RatingBar) findViewById(R.id.ratingBar2);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void submitRating(View v){

        ratingFun = (RatingBar) findViewById(R.id.ratingBar);
        ratingLearn = (RatingBar) findViewById(R.id.ratingBar2);

        // TODO: style issues. if (something) and } else { needs spaces 
        if(ratingFun.getRating() == 0f || ratingLearn.getRating() == 0f){
            //The next few lines are a secret I hid for anyone who wants to find it...
            badRatingNum++;
            if(badRatingNum > 13) {
                // TODO: can we make a createToast method with parameters and call that instead of rewriting code or use ToastStuff
                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
                toast.makeText(MainActivity.this, "You have pressed this " +badRatingNum+ " times!", toast.LENGTH_SHORT).show();

            }else {
                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
                toast.makeText(MainActivity.this, badRatings[badRatingNum], toast.LENGTH_LONG).show();
            }
        }else {
            //Show a toast with the rating information...
            badRatingNum = -1;
            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
            toast.makeText(MainActivity.this, "Fun : " + ratingFun.getRating() + " Learn : " + ratingLearn.getRating(), toast.LENGTH_LONG).show();
        }

        RatingSaver.saveRating(ratingFun.getRating(), ratingLearn.getRating(), getApplication());

        ratingFun.setRating(0.0f);
        ratingLearn.setRating(0.0f);

    }

    // TODO: move this method to rating saver, make it static?
    public void clearRating(View v){
        try {
            // TODO: use more CONSTANTS (example FILE_NAME instead of "saveData")
            FileOutputStream fos = openFileOutput("saveData", Context.MODE_PRIVATE);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write("");
            bw.close();
            // ToastStuff.createToast("Saved to:" + getFilesDir().getPath());
        }catch (Exception e){
            ToastStuff.createToast("An Error Occured While Clearing Data!", getApplication());
            ToastStuff.createToast(e.getMessage(), getApplication());
        }
    }
    public void read(View v){
        RatingReader.readRatings(v);
    }







}
