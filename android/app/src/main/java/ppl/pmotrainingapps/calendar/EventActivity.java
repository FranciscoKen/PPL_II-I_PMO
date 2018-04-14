package ppl.pmotrainingapps.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


import ppl.pmotrainingapps.R;

public class EventActivity extends AppCompatActivity {
    private String temp_data;
    private TextView temp_text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Intent eventIntent = getIntent();
        temp_data = eventIntent.getStringExtra("data");

        temp_text = (TextView) findViewById(R.id.textEvent);
        temp_text.setText(temp_data);

    }
}
