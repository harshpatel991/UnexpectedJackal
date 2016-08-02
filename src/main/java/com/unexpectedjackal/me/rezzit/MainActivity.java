package com.unexpectedjackal.me.rezzit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.unexpectedjackal.me.rezzit.model.Home;
import com.unexpectedjackal.me.rezzit.model.PostRetriever;
import com.unexpectedjackal.me.rezzit.model.Subreddit;

import layout.PostsFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.unexpectedjackal.me.rezzit.R.layout.activity_main);

        Bundle extras = getIntent().getExtras();

        String subreddit = null;
        if (extras != null) {
            subreddit = extras.getString("subreddit");
        }

        addFragment(subreddit);
    }

    void addFragment(String subredditName){

        PostRetriever postRetriever;

        if(subredditName != null) {
            postRetriever = new Subreddit(subredditName);
        } else {
            postRetriever = new Home();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .add(com.unexpectedjackal.me.rezzit.R.id.fragments_holder
                        , PostsFragment.newInstance(postRetriever))
                .commit();
    }
}
