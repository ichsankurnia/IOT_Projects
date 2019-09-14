package com.example.hello.pengkor;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AmbilDataTask extends AsyncTask<String, Void, String> {

    public interface Callback {
        void sendResult(String result);
    }

    private static final String TAG = "AmbilDataTask";

    private Callback callback;

    public AmbilDataTask(Callback callback) {
        this.callback = callback;
    }

    @Override protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override protected void onPostExecute(String result) {
        super.onPostExecute(result);
        // pass result to view
        callback.sendResult(result);
    }

    @Override protected String doInBackground(String... strings) {
        String urlString = strings[0];
        String hasil = null;
        try {
            URL url = new URL(urlString);

            hasil = ambilDataFromURL(url);
        } catch (MalformedURLException e) {
            Log.e(TAG, "doInBackground: " + e.getLocalizedMessage());
        } catch (IOException e) {
            Log.e(TAG, "doInBackground: " + e.getLocalizedMessage());
        }
        return hasil;
    }

    /**
     * baca data dari http connection
     */
    private String ambilDataFromURL(URL url) throws IOException {
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        String hasil = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();

            // Timeout for connection.connect() arbitrarily set to 3000ms.
            httpURLConnection.setConnectTimeout(3000);
            // For this use case, set HTTP method to GET.
            httpURLConnection.setRequestMethod("GET");

            httpURLConnection.connect();

            // Retrieve the response body as an InputStream.
            inputStream = httpURLConnection.getInputStream();

            if (inputStream != null) {
                // Converts Stream to String with max length of 500.
                hasil = readStream(inputStream, 500);
            }
        } finally {
            // Close Stream and disconnect HTTPS connection.
            if (inputStream != null) {
                inputStream.close();
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return hasil;
    }

    /**
     * bca text dari input stream, dan convert menjadi string object
     *
     * @throws IOException
     */
    private String readStream(InputStream stream, int maxLength) throws IOException {
        String result = null;
        // Read InputStream using the UTF-8 charset.
        InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
        // Create temporary buffer to hold Stream data with specified max length.
        char[] buffer = new char[maxLength];
        // Populate temporary buffer with Stream data.
        int numChars = 0;
        int readSize = 0;
        while (numChars < maxLength && readSize != -1) {
            numChars += readSize;
            readSize = reader.read(buffer, numChars, buffer.length - numChars);
        }
        if (numChars != -1) {
            // The stream was not empty.
            // Create String that is actual length of response body if actual length was less than
            // max length.
            numChars = Math.min(numChars, maxLength);
            result = new String(buffer, 0, numChars);
        }
        return result;
    }
}
