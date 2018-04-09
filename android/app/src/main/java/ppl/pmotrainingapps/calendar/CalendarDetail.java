package ppl.pmotrainingapps.calendar;

import android.os.AsyncTask;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import ppl.pmotrainingapps.Home.AdapterPengumuman;
import ppl.pmotrainingapps.Pengumuman.Pengumuman;
import ppl.pmotrainingapps.R;

public class CalendarDetail extends AppCompatActivity {

    private final static String TAG = "CalendarDetail";

    public static JSONArray kegiatan = null;
    public static JSONObject hasilKegiatan = null;

    private TextView date;
    private TextView num;
    private TextView haribesar;
    private RecyclerView events;
    private CalendarDetailAdapter adapter;
    private List<Kegiatan> kegiatanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_detail);

        date = (TextView) findViewById(R.id.calendar_detail_date);
        num = (TextView) findViewById(R.id.calendar_detail_num);
        haribesar = (TextView) findViewById(R.id.calendar_detail_haribesar);
        events = (RecyclerView) findViewById(R.id.calendar_detail_events);
    }

    private void prepareKegiatan() {}

    private static class KegiatanTask extends AsyncTask<Void, Void, Void> {

        private WeakReference<CalendarDetail> activityReference;

        KegiatanTask(CalendarDetail context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL("http://pplk2a.if.itb.ac.id/ppl/getAllWHAT.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                if (connection.getResponseCode() == 200) {
                    InputStream responseBody = new BufferedInputStream(connection.getInputStream());

                    BufferedReader reader = new BufferedReader(new InputStreamReader(responseBody));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    JSONObject jsonObject = (JSONObject) new JSONParser().parse(result.toString());
                    CalendarDetail.hasilKegiatan = jsonObject;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            CalendarDetail activity = activityReference.get();
            // TODO: activity.setKegiatan();
        }
    }
}
