package com.helloworldapps.campusbuzz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotificationFragment extends Fragment {
    ListView mlistView;
    private NotificationListAdapter mAdapter;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;


    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_notification, container, false);
        mlistView = (ListView) view.findViewById(R.id.mlistView);
        List<Data> datas = new ArrayList<>();

        mFirebaseDatabase = FirebaseDatabase.getInstance("https://campus-buzz-7528e-default-rtdb.asia-southeast1.firebasedatabase.app");
        mDatabaseReference = mFirebaseDatabase.getReference("data");

        //progressBar.setVisibility(View.VISIBLE);

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                datas.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Data data = postSnapshot.getValue(Data.class);
                    datas.add(data);
                }
                Collections.reverse(datas);
                mAdapter = new NotificationListAdapter(getActivity(), datas);
                mlistView.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors here
                Log.w("MyFragment", "loadPost:onCancelled", databaseError.toException());
            }
        });


        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(),ViewMessageActivity.class);

                intent.putExtra("subject",datas.get(i).getSubject());
                intent.putExtra("message",datas.get(i).getMessage());
                intent.putExtra("date",datas.get(i).getDate());
                intent.putExtra("time",datas.get(i).getTime());
                startActivity(intent);
            }
        });





        return view;
    }
}