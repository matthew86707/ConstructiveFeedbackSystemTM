//2015 Wintress Technical Schools / League Of Amazing Programmers
//Developed by Matthew Smith and Russ Baxt (Will ask about spelling of last name)
//Version 0.1 - GUI TEST

package com.example.matthew.ratingapp;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.os.Debug;
import android.os.StrictMode;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;


public class MainActivity extends Activity {

    public static String teacherSelected = "None";
    public static float funSelected;
    public static float infoSelected;

    public static MenuItem a;
    public static MenuItem b;
    public static MenuItem c;

    public static Context context;
    public String badRatings[] = {
            "Please Provide A Rating",
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
            "Well I have a variable for that too..."
    };
    public int badRatingNum = -1;

    //This is just for a small easter egg im putting in...
    RatingBar ratingFun;
    RatingBar ratingLearn;

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
        return item.getItemId() == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu (ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreateContextMenu(menu, v, menuInfo);
      //  a = (MenuItem) findViewById(R.id.id_Teacher1);
       // b = (MenuItem) findViewById(R.id.id_Teacher2);
       // c = (MenuItem) findViewById(R.id.id_Teacher3);
//        try {
//            URL u = new URL("http://feedback.jointheleague.org/getTeachers.php");
//            URLConnection connection = u.openConnection();
//            connection.setConnectTimeout(5000);
//            connection.setReadTimeout(5000);
//            connection.connect();
//
//            // Read and store the result line by line then return the entire string.
//            InputStream in = connection.getInputStream();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//            StringBuilder html = new StringBuilder();
//            for (int i = 0; i < 3; i++) {
//                String s = reader.readLine();
//                if (i == 0) {
//                    a.setTitle(s);
//                }
//                if (i == 1) {
//                    b.setTitle(s);
//                }
//                if (i == 2) {
//                    c.setTitle(s);
//                }
//            }
//            in.close();
//        }catch (IOException e){
//            Log.v("WPW", e.toString());
//        }
//        a.setTitle("June");
//        b.setTitle("Site");
//        c.setTitle("Dave");
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.teacher_menu, menu);
    }



    @Override
public boolean onContextItemSelected(MenuItem item){
	switch (item.getItemId()){
	
	    case R.id.id_Teacher1:
       // teacherSelected = a.getTitle() + "";
            teacherSelected = "Site";
        RatingSaver.saveRating(funSelected, infoSelected, teacherSelected, getApplication());
	    item.setChecked(true);

        case R.id.id_Teacher2:
            //teacherSelected = b.getTitle() + "";
            teacherSelected = "June";
            RatingSaver.saveRating(funSelected, infoSelected, teacherSelected, getApplication());
            item.setChecked(true);

        case R.id.id_Teacher3:
            teacherSelected = "Dave";
            //teacherSelected = c.getTitle() + "";
            RatingSaver.saveRating(funSelected, infoSelected, teacherSelected, getApplication());
            item.setChecked(true);
	return true;

}
        return true;
}

    public void submitRating(View v){

        ratingFun = (RatingBar) findViewById(R.id.ratingBar);
        ratingLearn = (RatingBar) findViewById(R.id.ratingBar2);

        if (ratingFun.getRating() == 0f || ratingLearn.getRating() == 0f) {
            //The next few lines are a secret I hid for anyone who wants to find it...
            badRatingNum++;

            if (badRatingNum > 13) {
                ToastStuff.createToast("You have pressed this " + badRatingNum + " times!", MainActivity.this);
            } else {
                ToastStuff.createToast(badRatings[badRatingNum], MainActivity.this);
            }

        } else {
            registerForContextMenu(v);
            openContextMenu(v);

            funSelected = ratingFun.getRating();
            infoSelected = ratingLearn.getRating();

            ToastStuff.createToast("Fun : " + ratingFun.getRating() + " Learn : " + ratingLearn.getRating(), MainActivity.this);

            ratingFun.setRating(0.0f);
            ratingLearn.setRating(0.0f);
            //Show a toast with the rating information...
            badRatingNum = -1;

        }


    }

    public void clearRating(View v){
        try {
            FileOutputStream fos = openFileOutput(Data.FILE_NAME, Context.MODE_PRIVATE);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write("");
            bw.close();
        } catch (Exception e) {
            ToastStuff.createToast("An Error Occured While Clearing Data!", getApplication());
            ToastStuff.createToast(e.getMessage(), getApplication());
        }
    }

    public void read(View v){
        RatingReader.readRatings(v);
    }
}
