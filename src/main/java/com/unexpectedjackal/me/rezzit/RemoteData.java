package com.unexpectedjackal.me.rezzit;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RemoteData {

    public static String readContents(String url) {

        String cached = MyCache.read(url);
        if (cached != null) {
            Log.d("MSG", "Using cache for " + url);
            return cached;
        }

        HttpURLConnection httpURLConnection = getConnection(url);
        if (httpURLConnection == null) return null;
        try {
            StringBuffer stringBuffer = new StringBuffer(8192);
            String tmp = "";
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(
                            httpURLConnection.getInputStream()
                    )
            );
            while ((tmp = bufferedReader.readLine()) != null) {
                stringBuffer.append(tmp).append("\n");
            }
            bufferedReader.close();
            return stringBuffer.toString();
        } catch (IOException e) {
            Log.d("READ FAILED", e.toString());
            return null;
        }
    }

    //TODO: can probably use 1 http connection
    private static HttpURLConnection getConnection(String url) {

        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setReadTimeout(30000);
        } catch (MalformedURLException e) {
            Log.e("getConnection()", "Invalid URL: " + e.toString());
        } catch (IOException e) {
            Log.e("getConnection()", "Could not connect: " + e.toString());
        }
        return httpURLConnection;
    }
}
