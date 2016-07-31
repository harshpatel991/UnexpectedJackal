package layout;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.unexpectedjackal.me.rezzit.model.Post;
import com.unexpectedjackal.me.rezzit.PostsHolder;
import com.unexpectedjackal.me.rezzit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostsFragment extends Fragment {
    ListView postsListView;
    ArrayAdapter<Post> adapter;
    Handler handler;

    String subreddit;
    List<Post> posts;
    PostsHolder postsHolder;

    public PostsFragment() {
        handler = new Handler();
        posts = new ArrayList<Post>();
    }

    public static Fragment newInstance(String subreddit) {
        PostsFragment pf = new PostsFragment();
        pf.subreddit = subreddit;
        pf.postsHolder = new PostsHolder(pf.subreddit);
        return pf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_posts, container, false);
        postsListView = (ListView) v.findViewById(R.id.posts_list);
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
        initialize();
    }

    private void initialize() {
        // This should run only once for the fragment as the
        // setRetainInstance(true) method has been called on
        // this fragment

        if (posts.size() == 0) {

            // Must execute network tasks outside the UI
            // thread. So create a new thread.

            new Thread() {
                public void run() {
                    posts.addAll(postsHolder.fetchPosts());

                    // UI elements should be accessed only in
                    // the primary thread, so we must use the
                    // handler here.

                    handler.post(new Runnable() {
                        public void run() {
                            createAdapter();
                        }
                    });
                }
            }.start();
        } else {
            createAdapter();
        }
    }

    /**
     * This method creates the adapter from the list of posts
     * , and assigns it to the list.
     */
    private void createAdapter() {

        // Make sure this fragment is still a part of the activity.
        if (getActivity() == null) return;

        adapter = new ArrayAdapter<Post>(getActivity(), R.layout.post_item, posts) {
            @Override
            public View getView(int position,
                                View convertView,
                                ViewGroup parent) {

                if (convertView == null) {
                    convertView = getActivity()
                            .getLayoutInflater()
                            .inflate(R.layout.post_item, null);
                }

                TextView postTitle;
                postTitle = (TextView) convertView
                        .findViewById(R.id.post_title);

                TextView postDetails;
                postDetails = (TextView) convertView
                        .findViewById(R.id.post_details);

                TextView postScore;
                postScore = (TextView) convertView
                        .findViewById(R.id.post_score);

                Post post = posts.get(position);
                postTitle.setText(post.getTitle());
                postDetails.setText("by " + post.getAuthor() + " | " + post.getNumComments() + " comments");
                postScore.setText(post.getScore());
                return convertView;
            }
        };
        postsListView.setAdapter(adapter);
    }

}
