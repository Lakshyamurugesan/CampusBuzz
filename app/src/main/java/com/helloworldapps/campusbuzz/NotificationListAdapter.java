package com.helloworldapps.campusbuzz;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class NotificationListAdapter extends ArrayAdapter<Data> {

    private Context mContext;
    private List<Data> mnotificationList;

    public NotificationListAdapter( Context context, List<Data> postList) {
        super(context,0,postList);
        mContext = context;
        mnotificationList = postList;
    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);

        Data currentPost = mnotificationList.get(position);

        TextView text = listItem.findViewById(R.id.title);
        TextView date = listItem.findViewById(R.id.date);
        //TextView time = listItem.findViewById(R.id.time);

        text.setText(currentPost.getSubject());

        String dates = currentPost.getDate().replace("-","/");

        date.setText(dates);



        return listItem;
    }
}