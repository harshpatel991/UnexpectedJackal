package com.unexpectedjackal.me.rezzit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import layout.PostsFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.unexpectedjackal.me.rezzit.R.layout.activity_main);
        addFragment();
        System.out.println("Hello!");
    }

    void addFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .add(com.unexpectedjackal.me.rezzit.R.id.fragments_holder
                        , PostsFragment.newInstance("aww"))
                .commit();
    }
}
