package com.helloworldapps.campusbuzz;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ListView mListView;
    private ArrayList<Data> mPostList;
    private PostListAdapter mAdapter;
    private ProgressBar progressBar;
    private ImageView imageview;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mListView = (ListView) view.findViewById(R.id.list_view);
        progressBar = view.findViewById(R.id.progress_bar);
        imageview = view.findViewById(R.id.imageview);
        mPostList = new ArrayList<Data>();

        mFirebaseDatabase = FirebaseDatabase.getInstance("https://campus-buzz-7528e-default-rtdb.asia-southeast1.firebasedatabase.app");
        mDatabaseReference = mFirebaseDatabase.getReference("data");

        progressBar.setVisibility(View.VISIBLE);

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mPostList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Data data = postSnapshot.getValue(Data.class);

                    if(getCurrentDate().equals(data.getDate())) {
                        mPostList.add(data);
                    }
                }

                if(mPostList.size() <= 0) {
                    imageview.setImageResource(R.drawable.noinbox);
                    imageview.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
                else {

                    Collections.reverse(mPostList);
                    mAdapter = new PostListAdapter(getActivity(), mPostList);
                    mListView.setAdapter(mAdapter);
                    System.out.println(getCurrentDate() + "==============================");

                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors here
                Log.w("MyFragment", "loadPost:onCancelled", databaseError.toException());
            }
        });

        return view;

    }

    private String getCurrentDate() {
        // Create a SimpleDateFormat object with the desired date and time pattern
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        // Get the current date as a Date object
        Date currentDate = new Date();

        // Format the date using the SimpleDateFormat object
        return dateFormat.format(currentDate);
    }
}