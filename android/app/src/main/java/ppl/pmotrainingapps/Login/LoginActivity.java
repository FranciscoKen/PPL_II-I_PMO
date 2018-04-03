package ppl.pmotrainingapps.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.messaging.FirebaseMessaging;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.security.spec.ECField;

import ppl.pmotrainingapps.Main.MainActivity;
import ppl.pmotrainingapps.R;

public class LoginActivity extends AppCompatActivity {
    public static JSONArray hasil = null;
    public final static String SHARED_PREFERENCE_KEY = "SharedPreferenceKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFERENCE_KEY, MODE_PRIVATE);
        String nama = sharedPreferences.getString("nama", null);
        String NIP = sharedPreferences.getString("NIP", null);
        if(nama!=null && NIP!=null){
            Intent main = new Intent(getApplicationContext(), MainActivity.class);
            main.putExtra("nama", nama);
            main.putExtra("NIP",NIP);
            main.putExtra("statusLogin", "success");
            startActivity(main);
            finish();
        }
        // titip buat debugging
        FirebaseMessaging.getInstance().subscribeToTopic("all");
        Button buttonLogin = (Button) findViewById(R.id.buttonLogin);
        final EditText editTextNama = (EditText) findViewById(R.id.isianNama);
        final EditText editTextNIP = (EditText) findViewById(R.id.isianNIP);
        buttonLogin.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String namaInput = editTextNama.getText().toString();
                String NIPInput = editTextNIP.getText().toString();



                Log.v("test", "nama: "+namaInput+" NIP: "+NIPInput);
                // kirim intent ke main klo misal login berhasil..
                // klo ga..kembali ke login
                Intent getUser = new Intent(getApplicationContext(), UserGetterService.class);
                getUser.putExtra("url", "http://pplk2a.if.itb.ac.id/ppl/getAllUser.php");
                startService(getUser);

                if(hasil!=null) {
                    Log.d("testing", "hasil yang didapat:"+ hasil.get(0).toString());
                    boolean berhasilLogin = false;
                    for(int iterator = 0; iterator < hasil.size(); iterator++) {
                        String hasilFetch = hasil.get(iterator).toString();

                        try{
                            JSONObject json = (JSONObject) new JSONParser().parse(hasilFetch);

//                        Log.d("testing final", "json: "+json);
//                        JSONObject username = (JSONObject) json.get("username");
//                        JSONObject password = (JSONObject) json.get("passwd");
                            String usernameString = (String)json.get("username");
                            String passwordString = (String)json.get("passwd");
                            if((usernameString.equals(namaInput)) && (passwordString.equals(NIPInput))){
                                Log.d("hore", "berhasil login");
                                berhasilLogin = true;
                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                editor.putString("nama", usernameString);
                                editor.putString("NIP", passwordString);
                                editor.commit();

                                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                                main.putExtra("nama", editTextNama.getText().toString());
                                main.putExtra("NIP",editTextNIP.getText().toString());
                                main.putExtra("statusLogin", "success");
                                startActivity(main);
                                finish();
                            }
//                        String passwordString = password.toString();
                            Log.d("testing final", "username: "+usernameString+" password: "+passwordString);
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if(!berhasilLogin) {
                        Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                        login.putExtra("nama", editTextNama.getText().toString());
                        startActivity(login);
                        finish();
                    }

                }

//                Intent login = new Intent(getApplicationContext(), LoginActivity.class);
//                login.putExtra("nama", editTextNama.getText().toString());
//                startActivity(login);
//                finish();
//                Intent main = new Intent(getApplicationContext(), MainActivity.class);
//                main.putExtra("nama", editTextNama.getText().toString());
//                main.putExtra("NIP",editTextNIP.getText().toString());
//                main.putExtra("statusLogin", "success");
//                startActivity(main);
            }
        });
    }
}
