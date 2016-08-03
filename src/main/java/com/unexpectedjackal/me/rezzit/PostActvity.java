package com.unexpectedjackal.me.rezzit;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.unexpectedjackal.me.rezzit.model.Post;

import layout.PostFragment;

public class PostActvity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_actvity);

        Bundle extras = getIntent().getExtras();


        addFragment(extras.getParcelable("post"));
    }

    void addFragment(Parcelable parcelablePost) {
        Post post = (Post) parcelablePost;


        getSupportFragmentManager()
                .beginTransaction()
                .add(com.unexpectedjackal.me.rezzit.R.id.post_fragments_holder
                        , PostFragment.newInstance(post))
                .commit();
    }
}
