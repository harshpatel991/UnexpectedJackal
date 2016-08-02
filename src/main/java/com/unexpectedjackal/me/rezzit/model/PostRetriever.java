package com.unexpectedjackal.me.rezzit.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.unexpectedjackal.me.rezzit.RemoteData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public abstract class PostRetriever {
    String after = "";

    /**
     * Returns a list of Post objects after fetching data from
     * Reddit using the JSON API.
     *
     * @return
     */
    public List<Post> fetchPosts() {
        String url = generateURL(after);
        String raw = RemoteData.readContents(url);
        List<Post> list = new ArrayList<>();
        try {
            JSONObject data = new JSONObject(raw).getJSONObject("data");
            JSONArray children = data.getJSONArray("children");

            after = data.getString("after");

            for (int i = 0; i < children.length(); i++) {
                JSONObject cur = children.getJSONObject(i)
                        .getJSONObject("data");
                Post p = new Post()
                        .withTitle(cur.optString("title"))
                        .withUrl(cur.optString("url"))
                        .withNumComments(cur.optInt("num_comments"))
                        .withPoints(cur.optInt("score"))
                        .withAuthor(cur.optString("author"))
                        .withSubreddit(cur.optString("subreddit"))
                        .withPermalink(cur.optString("permalink"))
                        .withDomain(cur.optString("domain"))
                        .withCreated(cur.optLong("created"))
                        .withId(cur.optString("id"));

                String thumbnailUrl = cur.optString("thumbnail");

                if(thumbnailUrl != null && !thumbnailUrl.equals("self")) {
                    InputStream thumbnailStream = new java.net.URL(thumbnailUrl).openStream();
                    Bitmap thumbnailBitmap = BitmapFactory.decodeStream(thumbnailStream);
                    p.withThumbnailBitmap(thumbnailBitmap);
                }

                if (p.getTitle() != null) {
                    list.add(p);
                }
            }
        } catch (Exception e) {
            Log.e("fetchPosts()", e.toString());
        }
        return list;
    }


    abstract String generateURL(String after);
}
