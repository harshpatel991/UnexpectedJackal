package com.unexpectedjackal.me.rezzit.model;

public class Post {

    String subreddit;
    String title;
    String author;
    int points;
    int numComments;
    String permalink;
    String url;
    String domain;
    String id;

    public String getSubreddit() {
        return subreddit;
    }

    public String getAuthor() {
        return author;
    }

    public int getPoints() {
        return points;
    }

    public int getNumComments() {
        return numComments;
    }

    public String getPermalink() {
        return permalink;
    }

    public String getUrl() {
        return url;
    }

    public String getDomain() {
        return domain;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getScore() {
        return Integer.toString(points);
    }

    public Post withSubreddit(String subreddit) {
        this.subreddit = subreddit;
        return this;
    }

    public Post withTitle(String title) {
        this.title = title;
        return this;
    }

    public Post withAuthor(String author) {
        this.author = author;
        return this;
    }

    public Post withPoints(int points) {
        this.points = points;
        return this;
    }

    public Post withNumComments(int numComments) {
        this.numComments = numComments;
        return this;
    }

    public Post withPermalink(String permalink) {
        this.permalink = permalink;
        return this;
    }

    public Post withUrl(String url) {
        this.url = url;
        return this;
    }

    public Post withDomain(String domain) {
        this.domain = domain;
        return this;
    }

    public Post withId(String id) {
        this.id = id;
        return this;
    }
}
