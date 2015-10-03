package com.example.matthew.ratingapp;

import android.app.Application;
import android.app.DownloadManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matthew on 9/30/2015.
 */
public class DataBaseFunctions {

    RequestQueue requestQueue;

    public void pushRatings(final float ratingFun, final float ratingLearn){

        requestQueue = Volley.newRequestQueue(MainActivity.context);
        StringRequest request = new StringRequest(Request.Method.POST, "http://feedback.jointheleague.org/addRating.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parameters  = new HashMap<String, String>();
                parameters.put("informationRating",ratingLearn +"");
                parameters.put("funRating",ratingFun + "");

                return parameters;
            }
        };
        requestQueue.add(request);
    }


}

