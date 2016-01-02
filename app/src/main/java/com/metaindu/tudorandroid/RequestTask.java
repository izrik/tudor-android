package com.metaindu.tudorandroid;

import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by izrik on 1/2/2016.
 */
public class RequestTask extends AsyncTask<URL, Integer, Long> {

    public RequestTask(View view) {
        this.view = view;
    }

    View view;

    @Override
    protected Long doInBackground(URL... params) {

        try {

            for (URL url : params) {

                HttpsURLConnection urlConnection;
                try {
                    urlConnection = (HttpsURLConnection) url.openConnection();
                } catch (IOException e) {
                    Snackbar.make(view, "Caught an exception: " + e.toString(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return null;
                }

                InputStream in;
                try {
                    try {
                        in = urlConnection.getInputStream();
                    } catch (IOException e) {
                        // e.printStackTrace();
                        Snackbar.make(view, "Caught an exception: " + e.toString(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        return null;
                    }

                } finally {
                    urlConnection.disconnect();
                }
            }

            return 0L;

        } catch (Exception e) {
            // e.printStackTrace();
            Snackbar.make(view, "Last chance, caught an exception: " + e.toString(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return null;
        }
    }
}
