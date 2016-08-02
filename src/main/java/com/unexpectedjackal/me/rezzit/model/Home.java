package com.unexpectedjackal.me.rezzit.model;

public class Home extends PostRetriever {
    private final String URL_TEMPLATE =
            "http://www.reddit.com/"
                    + ".json"
                    + "?after=AFTER";


    public Home() {
        generateURL("");
    }

    String generateURL(String after) {
        return URL_TEMPLATE.replace("AFTER", after);
    }
}
