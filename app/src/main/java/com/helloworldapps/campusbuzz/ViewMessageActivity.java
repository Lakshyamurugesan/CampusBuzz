package com.helloworldapps.campusbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ViewMessageActivity extends AppCompatActivity {

    String subject,message,date,time;

    TextView subject_text_view,message_text_view,date_value_text_view,time_value_text_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_message);

        subject_text_view = findViewById(R.id.subject_text_view);
        message_text_view = findViewById(R.id.message_text_view);
        date_value_text_view = findViewById(R.id.date_value_text_view);
        time_value_text_view = findViewById(R.id.time_value_text_view);

        subject = getIntent().getStringExtra("subject");
        message = getIntent().getStringExtra("message");
        date = getIntent().getStringExtra("date");
        time = getIntent().getStringExtra("time");

        subject_text_view.setText(subject);
        message_text_view.setText(message);
        date_value_text_view.setText(date);
        time_value_text_view.setText(time);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}