//� 2015 Wintress Technical Schools / League Of Amazing Programmers
//Developed by Matthew Smith and Russ Baxt (Will ask about spelling of last name)
//Version 0.1 - GUI TEST

package com.example.matthew.ratingapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;


public class MainActivity extends Activity {

    RatingBar ratingFun;
    RatingBar ratingLearn;

    //This is just for a small easter egg im putting in...
    public String badRatings[] =
                            { "Please Provide A Rating"
                            , "Come on, it takes 2 seconds"
                            , "PLEASE"
                            , "Okay Now You're getting on my nerves"
                            , "You're wasting my time"
                            , "Why am I even writing these messages"
                            , "Its not like anyone is going to find them"
                            , "Im a bored programmer"
                            , "Hiding secret messages in apps..."
                            , "You know what..."
                            , "IM DONE"
                            , "RATE YOUR CLASS ALREADY!"
                            , "Do you want to know how many button presses you have made?"
                            , "Well I have a variable for that too..."};

    public int badRatingNum = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        //If either rating has not been entered...
        if (ratingFun.getRating() == 0f || ratingLearn.getRating() == 0f) {
            //The next few lines are a secret I hid for anyone who wants to find it...
            badRatingNum++;
            if (badRatingNum > 13) {
                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
                toast.makeText(MainActivity.this, "You have pressed this " +badRatingNum+ " times!", toast.LENGTH_SHORT).show();

            } else {
                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
                toast.makeText(MainActivity.this, badRatings[badRatingNum], toast.LENGTH_LONG).show();
            }
        //else if a rating has been entered...
        } else {
            badRatingNum = -1;
            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
            toast.makeText(MainActivity.this, "Fun : " + ratingFun.getRating() + " Learn : " + ratingLearn.getRating(), toast.LENGTH_LONG).show();
        }

        ratingFun.setRating(0.0f);
        ratingLearn.setRating(0.0f);
    }
}
