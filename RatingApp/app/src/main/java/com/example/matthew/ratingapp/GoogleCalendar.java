package com.example.matthew.ratingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Date;

/**
 * Created by Ruoya on 11/7/15.
 */
public class GoogleCalendar {
    GoogleAccountCredential mCredential;
    private static final String[] SCOPES = { CalendarScopes.CALENDAR_READONLY };
    MainActivity main;
    private static String prefAccountName;
    private static final int EVENT_NUM_TOLERANCE = 5;


    public GoogleCalendar(MainActivity main, String prefAccountName) {
        // Initialize credentials and service object.
        this.main = main;
        this.prefAccountName = prefAccountName;
        SharedPreferences settings = main.getPreferences(Context.MODE_PRIVATE);
        mCredential = GoogleAccountCredential.usingOAuth2(
                main.getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff())
                .setSelectedAccountName(settings.getString(prefAccountName, null));


    }

    /**
     * Starts an activity in Google Play Services so the user can pick an
     * account.
     */
    public void chooseAccount() {
        main.startActivityForResult(
                mCredential.newChooseAccountIntent(), main.REQUEST_ACCOUNT_PICKER);
    }

    public void executeRequestTask() {
        new MakeRequestTask(mCredential).execute();
    }

    /**
     * An asynchronous task that handles the Google Calendar API call.
     * Placing the API calls in their own task ensures the UI stays responsive.
     */
    private class MakeRequestTask extends AsyncTask<Void, Void, List<String>> {
        private com.google.api.services.calendar.Calendar mService = null;
        private Exception mLastError = null;

        public MakeRequestTask(GoogleAccountCredential credential) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.calendar.Calendar.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("Google Calendar API Android Quickstart")
                    .build();
        }

        /**
         * Background task to call Google Calendar API.
         *
         * @param params no parameters needed for this task.
         */
        @Override
        protected List<String> doInBackground(Void... params) {
            try {
                return getDataFromApi();
//                List<String> prints = new ArrayList<String>();
//                prints.add(String.format("%s (%s)", getCurrentClass().getSummary()));
//                return prints;
            } catch (Exception e) {
                mLastError = e;
                cancel(true);
                return null;
            }
        }

        /**
         * Fetch a list of the next 10 events from the primary calendar.
         *
         * @return List of Strings describing returned events.
         * @throws java.io.IOException
         */
        private List<String> getDataFromApi() throws IOException {
            // List the next 10 events from the primary calendar.
            DateTime now = new DateTime(System.currentTimeMillis());
            List<String> eventStrings = new ArrayList<String>();
            Events events = mService.events().list("vic.wintriss@gmail.com")
                    .setMaxResults(10)
                    .setTimeMin(now)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
            List<Event> items = events.getItems();

            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    // All-day events don't have start times, so just use
                    // the start date.
                    start = event.getStart().getDate();
                }
                eventStrings.add(
                        String.format("%s (%s)", event.getSummary(), start));
            }
            return eventStrings;
        }

        protected Event getCurrentClass() throws IOException {
            DateTime now = new DateTime(System.currentTimeMillis());
            Events events = mService.events().list("vic.wintriss@gmail.com")
                    .setMaxResults(5)
                    .setTimeMin(now)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
            List<Event> items = events.getItems();
            for (int i = 0; i <= 5; i++) {
                DateTime end = items.get(i).getEnd().getDateTime();
                DateTime start = items.get(i).getStart().getDateTime();
                if (end.getValue() > now.getValue() && start.getValue() < now.getValue() &&
                        items.get(i).getDescription().charAt(0) == '[') {
                    return items.get(i);
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            main.mOutputText.setText("");
            main.mProgress.show();
        }

        @Override
        protected void onPostExecute(List<String> output) {
            main.mProgress.hide();
            if (output == null || output.size() == 0) {
                main.mOutputText.setText("No results returned.");
            } else {

                output.add(0, "Data retrieved using the Google Calendar API:");
                main.mOutputText.setText(TextUtils.join("\n", output));
            }
        }

//        @Override
//        protected void onCancelled() {
//            mProgress.hide();
//            if (mLastError != null) {
//                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
//                    showGooglePlayServicesAvailabilityErrorDialog(
//                            ((GooglePlayServicesAvailabilityIOException) mLastError)
//                                    .getConnectionStatusCode());
//                } else if (mLastError instanceof UserRecoverableAuthIOException) {
//                    startActivityForResult(
//                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
//                            MainActivity.REQUEST_AUTHORIZATION);
//                } else {
//                    mOutputText.setText("The following error occurred:\n"
//                            + mLastError.getMessage());
//                }
//            } else {
//                mOutputText.setText("Request cancelled.");
//            }
//        }

    }
}
