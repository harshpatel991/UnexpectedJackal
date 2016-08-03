package layout;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.unexpectedjackal.me.rezzit.MainActivity;
import com.unexpectedjackal.me.rezzit.PostActvity;
import com.unexpectedjackal.me.rezzit.model.PostRetriever;
import com.unexpectedjackal.me.rezzit.R;
import com.unexpectedjackal.me.rezzit.model.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostsFragment extends Fragment {
    ListView postsListView;
    ArrayAdapter<Post> adapter;
    Handler handler;

    List<Post> posts;
    PostRetriever postsHolder;

    public PostsFragment() {
        handler = new Handler();
        posts = new ArrayList<>();
    }

    public static Fragment newInstance(PostRetriever posts) {
        PostsFragment pf = new PostsFragment();
        pf.postsHolder = posts;
        return pf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_posts, container, false);
        postsListView = (ListView) v.findViewById(R.id.posts_list);

        //TODO: this might go in onActivityCreated
        createAdapter();
        new loadMoreListView().execute();

        // Creating a button - Load More
        Button btnLoadMore = new Button(getActivity());
        btnLoadMore.setText("Load More");
        postsListView.addFooterView(btnLoadMore);

        btnLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                new loadMoreListView().execute();
            }
        });

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void createAdapter() {
        if (getActivity() == null) return;

        adapter = new ArrayAdapter<Post>(getActivity(), R.layout.post_item, posts) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if (convertView == null) {
                    convertView = getActivity()
                            .getLayoutInflater()
                            .inflate(R.layout.post_item, null);
                }

                TextView postTitle = (TextView) convertView.findViewById(R.id.post_title);
                TextView postDetails = (TextView) convertView.findViewById(R.id.post_details);
                TextView postScore = (TextView) convertView.findViewById(R.id.post_score);
                ImageView imageView = (ImageView) convertView.findViewById(R.id.post_thumbnail);

                final Post post = posts.get(position);
                String humanDiff = DateUtils.getRelativeTimeSpanString(post.getCreated()*1000, System.currentTimeMillis(), DateUtils.DAY_IN_MILLIS).toString();

                postTitle.setText(post.getTitle());
                postDetails.setText("by " + post.getAuthor() + " in " + post.getSubreddit() + " | " + post.getNumComments() + " comments");
                postScore.setText(humanDiff + " " + post.getPoints() + " points");
                imageView.setImageBitmap(post.getThumbnailBitmap());

                postTitle.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), PostActvity.class);
                        intent.putExtra("post", post);
                        startActivity(intent);
                    }
                });


                return convertView;
            }
        };
        postsListView.setAdapter(adapter);
    }

    private class loadMoreListView extends AsyncTask<String, Void, List<Post>> {

        @Override
        protected List<Post> doInBackground(String... params) {
            return postsHolder.fetchPosts();
        }

        @Override
        protected void onPostExecute(List<Post> posts) {
            posts.addAll(posts);
            adapter.addAll(posts);
        }

    }
}
