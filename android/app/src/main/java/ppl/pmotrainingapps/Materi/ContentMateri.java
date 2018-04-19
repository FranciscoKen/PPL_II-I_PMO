package ppl.pmotrainingapps.Materi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import ppl.pmotrainingapps.R;

public class ContentMateri extends AppCompatActivity {
    TextView judulMateri;
    TextView kontenMateri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_materi);

        judulMateri = findViewById(R.id.judulMateri);
        kontenMateri = findViewById(R.id.kontenMateri);

        Bundle b = getIntent().getExtras();

        int value = -1;

        if(b != null){
            judulMateri.setText(b.getString("judul_materi"));
            String konten = b.getString("konten_materi");
            Log.d("test", "konten: "+konten);
            if(konten==null){
                findViewById(R.id.kontenMateri).setVisibility(View.GONE);
            }else{
                kontenMateri.setText(b.getString("konten_materi"));
            }



        }

    }
}
