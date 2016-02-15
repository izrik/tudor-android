package com.metaindu.tudorandroid;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by izrik on 1/2/2016.
 */
public class RequestTask extends AsyncTask<URL, Integer, Long> {

    public RequestTask(View view, boolean verifyCerts) {
        this.view = view;
        this.verifyCerts = verifyCerts;
    }

    View view;
    boolean verifyCerts;

    @Override
    protected Long doInBackground(URL... params) {

        if (!verifyCerts) {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};
            // Install the all-trusting trust manager
            final SSLContext sc;
            try {
                sc = SSLContext.getInstance("SSL");
            } catch (NoSuchAlgorithmException e) {
                Snackbar.make(view, "4 Caught an exception: " + e.toString(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return null;
            }
            try {
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
            } catch (KeyManagementException e) {
                Snackbar.make(view, "5 Caught an exception: " + e.toString(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return null;
            }
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        }


        try {

            for (URL url : params) {

                HttpsURLConnection urlConnection;
                try {
                    urlConnection = (HttpsURLConnection) url.openConnection();
                } catch (IOException e) {
                    Snackbar.make(view, "2 Caught an exception: " + e.toString(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return null;
                }

                InputStream in;
                try {
                    try {
                        in = urlConnection.getInputStream();

                        String result = slurp(in, 1024);

                        Snackbar.make(view, "Success! " + result, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    } catch (IOException e) {
                        // e.printStackTrace();
                        Snackbar.make(view, "3 Caught an exception: " + e.toString(), Snackbar.LENGTH_LONG)
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


    public static String slurp(final InputStream is, final int bufferSize) throws IOException {
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
//            try (
        Reader in = new InputStreamReader(is, "UTF-8");
//            ) {
        for (;;) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
//            }
//            catch (UnsupportedEncodingException ex) {
//        /* ... */
//            }
//            catch (IOException ex) {
//        /* ... */
//            }
        return out.toString();
    }
}
