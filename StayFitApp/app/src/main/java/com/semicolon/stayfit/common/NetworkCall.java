package com.semicolon.stayfit.common;

import android.support.annotation.NonNull;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by shubhankar_roy on 3/12/2016.
 */
public class NetworkCall {
    public static final String baseUrl = "http://10.222.120.101:8080/stayfit/";

    @NonNull
    public String postData(String actionUrl, final String object) throws IOException {
        StringEntity se = new StringEntity(object);

        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(baseUrl + actionUrl);

        //sets the post request as the resulting string
        httppost.setEntity(se);
        //sets a request header so the page receving the request
        //will know what to do with it
        httppost.setHeader("Accept", "application/json");
        httppost.setHeader("Content-type", "application/json");

        // Execute HTTP Post Request
        HttpResponse response = httpclient.execute(httppost);
        InputStream responseStream = response.getEntity().getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
        }
        return out.toString();
    }

    /*@NonNull
    public String postDataNew(final String actionUrl, final String object) throws IOException {
        final StringBuffer response = new StringBuffer();
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                HttpURLConnection urlConnection = null;
                try {
                    URL url = new URL(Uri.encode(baseUrl + actionUrl, ":/"));
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setUseCaches(false);
                    urlConnection.setConnectTimeout(10000);
                    urlConnection.setReadTimeout(10000);
                    urlConnection.setRequestProperty("Content-Type", "application/json");

                    //urlConnection.setRequestProperty("Host", "10.222.120.98");
                    urlConnection.connect();

                    DataOutputStream printout = new DataOutputStream(urlConnection.getOutputStream());
                    printout.writeBytes(Uri.encode(object));
                    printout.flush();
                    printout.close();

                    //Get Response
                    InputStream is = urlConnection.getInputStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                    String line;
                    while ((line = rd.readLine()) != null) {
                        response.append(line);
                        response.append('\r');
                    }
                    rd.close();

                } catch (MalformedURLException e) {
                    Log.e("NetworkCall", e.getMessage(), e);
                } catch (ProtocolException e) {
                    Log.e("NetworkCall", e.getMessage(), e);
                } catch (IOException e) {
                    Log.e("NetworkCall", e.getMessage(), e);
                } finally {
                    if(urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
                return null;
            }
        }.execute();
        return response.toString();
    }*/
}
