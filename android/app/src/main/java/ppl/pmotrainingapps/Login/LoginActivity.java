package ppl.pmotrainingapps.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import ppl.pmotrainingapps.Main.MainActivity;
import ppl.pmotrainingapps.PDF.PDFActivityExample;
import ppl.pmotrainingapps.R;

public class LoginActivity extends AppCompatActivity {
    public static JSONArray hasil = null;
    public final static String SHARED_PREFERENCE_KEY = "SharedPreferenceKey";
    EditText editTextNama;
    EditText editTextNIP;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFERENCE_KEY, MODE_PRIVATE);
        String nama = sharedPreferences.getString("nama", null);
        String NIP = sharedPreferences.getString("NIP", null);
        if(nama!=null && NIP!=null){

            Intent main = new Intent(getApplicationContext(), MainActivity.class);

            //Kalo mau tes PDF Viewer
            //Intent main = new Intent(getApplicationContext(), PDFActivityExample.class);

            main.putExtra("nama", nama);
            main.putExtra("NIP",NIP);
            main.putExtra("statusLogin", "success");
            startActivity(main);
            finish();
        }
        // titip buat debugging
        FirebaseMessaging.getInstance().subscribeToTopic("all");
        Button buttonLogin = (Button) findViewById(R.id.buttonLogin);
        editTextNama = (EditText) findViewById(R.id.isianNama);
        editTextNIP = (EditText) findViewById(R.id.isianNIP);
        final LoginActivity login = this;
        buttonLogin.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                new LoginTask(login).execute();
            }
        });
    }
    public void checkLogin(){
        if(hasil!=null) {
            String namaInput = editTextNama.getText().toString();
            String NIPInput = editTextNIP.getText().toString();
            Log.v("test", "nama: "+namaInput+" NIP: "+NIPInput);
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

                    int id = Integer.parseInt((String)json.get("id"));

                    if((usernameString.equals(namaInput)) && (passwordString.equals(NIPInput))){
                        Log.d("hore", "berhasil login");
                        berhasilLogin = true;
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("nama", usernameString);
                        editor.putString("NIP", passwordString);
                        editor.putInt("id_user", id);
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

    }
    private class LoginTask extends AsyncTask<Void, Void, Void> {

        private WeakReference<LoginActivity> activityReference;

        LoginTask(LoginActivity context) {
            activityReference = new WeakReference<>(context);
        }

        protected Void doInBackground(Void... urls) {
            try{
                URL url = new URL(getString(R.string.endpointURI) + "getAllUser.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                if(connection.getResponseCode() == 200) {
                    Log.d("test", "connection success");
                    InputStream responseBody = connection.getInputStream();
                    JSONParser jsonParser = new JSONParser();
                    JSONArray jsonArray = (JSONArray) jsonParser.parse(new InputStreamReader(responseBody, "UTF-8"));
                    Log.d("hasil", "hasil: "+jsonArray.toString());
                    LoginActivity.hasil = jsonArray;

                }else{
                    Log.d("test", "connection failed");
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            // get a reference to the activity if it is still there
            LoginActivity activity = activityReference.get();
            activity.checkLogin();
        }
    }
}
