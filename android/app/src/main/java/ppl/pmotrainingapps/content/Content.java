package ppl.pmotrainingapps.content;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ppl.pmotrainingapps.R;

public class Content extends AppCompatActivity {

    TextView headerText;
    TextView dateText;
    TextView contentText;
    ImageView headerImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        headerImage = findViewById(R.id.headerImage);
        contentText = findViewById(R.id.contentText);
        headerText =  findViewById(R.id.headerText);
        dateText =  findViewById(R.id.headerDate);


        Glide.with(this).load(R.drawable.header1).into(headerImage);
        Bundle b = getIntent().getExtras();
        int value = -1; // or other values
        if(b != null){
            contentText.setText(b.getString("konten_teks"));
            headerText.setText(b.getString("judul"));
            dateText.setText(b.getString("tanggal"));

        }

    }
}
