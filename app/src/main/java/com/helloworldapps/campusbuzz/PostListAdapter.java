package com.helloworldapps.campusbuzz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PostListAdapter extends ArrayAdapter<Data> {

    private Context mContext;
    private List<Data> mPostList;

    public PostListAdapter( Context context, List<Data> postList) {
        super(context,0,postList);
        mContext = context;
        mPostList = postList;
    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.post_item, parent, false);

        Data currentPost = mPostList.get(position);

        ImageView image = listItem.findViewById(R.id.post_image);
        // Use Glide or Picasso library to load the image from the URL
         Glide.with(mContext).load(currentPost.getImageUrl()).into(image);
        // Picasso.get().load(currentPost.getImageUrl()).into(image);

        TextView text = listItem.findViewById(R.id.post_sub);
        text.setText(currentPost.getSubject());

        return listItem;
    }
}