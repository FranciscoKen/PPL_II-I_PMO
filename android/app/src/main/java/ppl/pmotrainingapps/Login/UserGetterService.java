package ppl.pmotrainingapps.Login;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.JsonReader;
import android.util.Log;

import org.json.simple.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class UserGetterService extends IntentService {
    public static JSONArray hasilFetchUser = null;
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "ppl.pmotrainingapps.Login.action.FOO";
    private static final String ACTION_BAZ = "ppl.pmotrainingapps.Login.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "ppl.pmotrainingapps.Login.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "ppl.pmotrainingapps.Login.extra.PARAM2";

    public UserGetterService() {
        super("UserGetterService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, UserGetterService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, UserGetterService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("hi", "halo halo");

        String url = intent.getStringExtra("url");
        Log.d("hi", "url1: "+url);
//        final ResultReceiver receiver = intent.getParcelableExtra("receiver");
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
                LoginActivity.hasil = jsonArray;

            }else{
                Log.d("test", "connection failed");
            }
        }catch(Exception e){
            e.printStackTrace();
        }


    }
    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
