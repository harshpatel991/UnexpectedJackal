package com.unexpectedjackal.me.rezzit.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Post implements Parcelable {


    String subreddit;
    String title;
    String author;
    int points;
    int numComments;
    String permalink;
    String url;
    String domain;
    String id;
    Long created;
    Bitmap thumbnailBitmap;

    public Long getCreated() {
        return created;
    }

    public Bitmap getThumbnailBitmap() {
        return thumbnailBitmap;
    }



    public String getSubreddit() {
        return subreddit;
    }

    public String getAuthor() {
        return author;
    }

    public String getPoints() {
        return Integer.toString(points);
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

    public Post withThumbnailBitmap(Bitmap thumbnailBitmap) {
        this.thumbnailBitmap = thumbnailBitmap;
        return this;
    }

    public Post withCreated(Long created) {
        this.created = created;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(subreddit);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeInt(points);
        dest.writeInt(numComments);
        dest.writeString(permalink);
        dest.writeString(url);
        dest.writeString(domain);
        dest.writeString(id);
        dest.writeLong(created);
        dest.writeParcelable(thumbnailBitmap, 0);

    }

    public Post( ) {

    }

    public Post(Parcel in ) {
        subreddit = in.readString();
        title = in.readString();
        author = in.readString();
        points = in.readInt();
        numComments = in.readInt();
        permalink = in.readString();
        url = in.readString();
        domain = in.readString();
        id = in.readString();
        created = in.readLong();
        thumbnailBitmap = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Parcelable.Creator<Post> CREATOR = new Creator<Post>() {

        public Post createFromParcel(Parcel source) {

            return new Post(source);
        }

        public Post[] newArray(int size) {

            return new Post[size];
        }

    };
}
