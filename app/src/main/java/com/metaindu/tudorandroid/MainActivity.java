package com.metaindu.tudorandroid;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    String currentTask = "121";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                URL url;
                try {
                    url = new URL(Settings.getUrl() + "/task/" + currentTask);
                } catch (MalformedURLException e) {
                    Snackbar.make(view, "Caught an exception: " + e.toString(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

                HttpsURLConnection urlConnection;
                try {
                    urlConnection = (HttpsURLConnection) url.openConnection();
                } catch (IOException e) {
                    Snackbar.make(view, "Caught an exception: " + e.toString(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

                InputStream in;
                try {
                    try {
                        in = urlConnection.getInputStream();
                    } catch (IOException e) {
                        // e.printStackTrace();
                        Snackbar.make(view, "Caught an exception: " + e.toString(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        return;
                    }

                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                // e.printStackTrace();
                Snackbar.make(view, "Last chance, caught an exception: " + e.toString(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;
            }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
