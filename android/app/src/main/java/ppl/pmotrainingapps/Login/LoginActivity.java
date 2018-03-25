package ppl.pmotrainingapps.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.messaging.FirebaseMessaging;

import ppl.pmotrainingapps.Main.MainActivity;
import ppl.pmotrainingapps.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // titip buat debugging
        FirebaseMessaging.getInstance().subscribeToTopic("all");
        Button buttonLogin = (Button) findViewById(R.id.buttonLogin);
        final EditText editTextNama = (EditText) findViewById(R.id.isianNama);
        final EditText editTextNIP = (EditText) findViewById(R.id.isianNIP);
        buttonLogin.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Log.v("test", "nama: "+editTextNama.getText().toString()+" NIP: "+editTextNIP.getText().toString());
                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                main.putExtra("nama", editTextNama.getText().toString());
                main.putExtra("NIP",editTextNIP.getText().toString());
                main.putExtra("statusLogin", "success");
                startActivity(main);
            }
        });
    }
}
