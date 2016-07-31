package com.unexpectedjackal.me.rezzit;

import android.util.Log;

import com.unexpectedjackal.me.rezzit.model.Post;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PostsHolder {
    private final String URL_TEMPLATE =
            "http://www.reddit.com/r/SUBREDDIT_NAME/"
                    + ".json"
                    + "?after=AFTER";

    String subreddit;
    String url;
    String after;

    public PostsHolder(String subreddit) {
        this.subreddit = subreddit;
        this.after = "";
        generateURL();
    }

    /**
     * Generates the actual URL from the template based on the
     * subreddit name and the 'after' property.
     */
    private void generateURL() {
        url = URL_TEMPLATE.replace("SUBREDDIT_NAME", subreddit);
        url = url.replace("AFTER", after);
    }

    /**
     * Returns a list of Post objects after fetching data from
     * Reddit using the JSON API.
     *
     * @return
     */
    public List<Post> fetchPosts() {
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
                        .withId(cur.optString("id"));
                if (p.getTitle() != null)
                    list.add(p);
            }
        } catch (Exception e) {
            Log.e("fetchPosts()", e.toString());
        }
        return list;
    }

    List<Post> fetchMorePosts() {
        generateURL();
        return fetchPosts();
    }
}
