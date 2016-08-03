package layout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.unexpectedjackal.me.rezzit.R;
import com.unexpectedjackal.me.rezzit.model.Post;

import java.io.IOException;
import java.io.InputStream;


public class PostFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String POST = "post";

    private Post post;



    public PostFragment() {
        // Required empty public constructor
    }

    public static PostFragment newInstance(Post post) {
        PostFragment fragment = new PostFragment();
        Bundle args = new Bundle();
        args.putParcelable(POST, post);
        fragment.setArguments(args);
        fragment.post = post;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            post = getArguments().getParcelable(POST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_post, container, false);

        TextView titleTextView = (TextView) v.findViewById(R.id.post_title);
        TextView descriptionTextView = (TextView) v.findViewById(R.id.post_description);
        ImageView thumbnailImageView = (ImageView) v.findViewById(R.id.post_thumbnail);


        titleTextView.setText(post.getTitle());
        descriptionTextView.setText("by " + post.getAuthor() + " | " + post.getPoints() + " points");

        if (post.getDomain().equals("imgur.com")) {
            if(!post.getUrl().contains(".")) {
                post.withUrl(post.getUrl()+".jpg");
            }

            InputStream thumbnailStream = null;
            try {
                thumbnailStream = new java.net.URL(post.getUrl()).openStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bitmap thumbnailBitmap = BitmapFactory.decodeStream(thumbnailStream);
            post.withThumbnailBitmap(thumbnailBitmap);

        }


        thumbnailImageView.setImageBitmap(post.getThumbnailBitmap());

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
