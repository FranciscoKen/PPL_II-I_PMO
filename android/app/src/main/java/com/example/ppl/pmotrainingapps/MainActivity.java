package com.example.ppl.pmotrainingapps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String statusLogin = intent.getStringExtra("statusLogin");
        Log.d("isi","statusLogin: "+statusLogin);
        if(statusLogin == null){
            Intent loginScreen = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(loginScreen);
            finish();
        }else{
            String nama = intent.getStringExtra("nama");
            String NIP = intent.getStringExtra("NIP");
            TextView textView = (TextView) findViewById(R.id.textMain);
            textView.setText("hai "+nama+" berNIP: "+NIP);
            Log.d("terlihat", "tertulisNama: "+nama+" berNIP: "+NIP);
        }




    }
}
