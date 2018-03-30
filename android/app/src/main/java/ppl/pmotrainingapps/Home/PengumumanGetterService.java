package ppl.pmotrainingapps.Home;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ppl.pmotrainingapps.Login.LoginActivity;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class PengumumanGetterService extends IntentService {


    public PengumumanGetterService() {
        super("PengumumanGetterService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        String url = intent.getStringExtra("url");
        Log.d("hi", "url1: "+url);
        getUserData(url);
    }

    private void getUserData(String stringUrl) {
        try{
            URL url = new URL(stringUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            if(connection.getResponseCode() == 200) {
                Log.d("test", "connection success");
                InputStream responseBody = connection.getInputStream();
                JSONParser jsonParser = new JSONParser();
                JSONArray jsonArray = (JSONArray) jsonParser.parse(new InputStreamReader(responseBody, "UTF-8"));
                Log.d("hasil", "hasil: "+jsonArray.toString());
                HomeFragment.pengumuman = jsonArray;

            }else{
                Log.d("test", "connection failed");
            }
        }catch(Exception e){
            e.printStackTrace();
        }


    }


}
