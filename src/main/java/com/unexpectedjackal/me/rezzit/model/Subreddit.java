package com.unexpectedjackal.me.rezzit.model;

public class Subreddit extends PostRetriever {
    private final String URL_TEMPLATE =
            "http://www.reddit.com/r/SUBREDDIT"
                    + ".json"
                    + "?after=AFTER";

    String subreddit;

    public Subreddit(String subreddit) {
        this.subreddit = subreddit;
        generateURL("");
    }

    String generateURL(String after) {
        return URL_TEMPLATE
                .replace("SUBREDDIT", subreddit)
                .replace("AFTER", after);
    }
}
