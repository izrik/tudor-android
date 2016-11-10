package com.metaindu.tudorandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    String currentTask = "121";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView text_output = (TextView)findViewById(R.id.content_main).findViewById(R.id.text_output);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                URL url;
                try {
                    url = new URL(Settings.getUrl(MainActivity.this) + "/task/" + currentTask);
                } catch (MalformedURLException e) {
                    Snackbar.make(view, "1 Caught an exception: " + e.toString(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }


                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                boolean verifyCerts = sp.getBoolean("verify_tls_certs", true);

                RequestTask req = new RequestTask(view, verifyCerts, text_output);
                req.execute(url);

                // progress indicator

                try {
                    List<Object> tasks = req.get();
                    text_output.setText(tasks.get(0).toString());

                } catch (InterruptedException e) {
                    Snackbar.make(view, "fab.onClick Caught an exception: " + e.toString(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    Snackbar.make(view, "fab.onClick Caught an exception: " + e.toString(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    e.printStackTrace();
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
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
