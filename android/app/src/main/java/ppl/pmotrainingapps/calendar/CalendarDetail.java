package ppl.pmotrainingapps.calendar;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import ppl.pmotrainingapps.Pengumuman.Pengumuman;
import ppl.pmotrainingapps.R;

public class CalendarDetail extends AppCompatActivity {

    private final static String TAG = "CalendarDetail";

    public static JSONArray arr_kegiatan = null;

    private TextView date;
    private TextView num;
    private TextView haribesar;
    private RecyclerView events;
    private CalendarDetailAdapter adapter;
    private List<Kegiatan> kegiatanList;

    public CalendarDetail() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_detail);

        date = (TextView) findViewById(R.id.calendar_detail_date);
        num = (TextView) findViewById(R.id.calendar_detail_num);
        haribesar = (TextView) findViewById(R.id.calendar_detail_haribesar);
        events = (RecyclerView) findViewById(R.id.calendar_detail_events);

        kegiatanList = new ArrayList<>();
        adapter = new CalendarDetailAdapter(kegiatanList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        events.setLayoutManager(mLayoutManager);
        events.setItemAnimator(new DefaultItemAnimator());
        events.setAdapter(adapter);
        events.setNestedScrollingEnabled(false);

        prepareKegiatan();
    }

    private void prepareKegiatan() {
        new KegiatanTask(this).execute();
    }

    public void setKegiatan() {
        if (arr_kegiatan != null) {
            for (int i = 0; i < arr_kegiatan.size(); i++) {
                String hasilFetch = arr_kegiatan.get(i).toString();
                try {
                    JSONObject json = (JSONObject) new JSONParser().parse(hasilFetch);
                    int id_kegiatan = json.get("id_kegiatan") != null ? Integer.parseInt((String) json.get("id_kegiatan")) : -1;
                    String nama_kegiatan = json.get("nama_kegiatan") != null ? (String) json.get("nama_kegiatan") : "";
                    String target_peserta = json.get("target_peserta") != null ? (String) json.get("target_peserta") : "";
                    String deskripsi_kegiatan = json.get("deskripsi_kegiatan") != null ? (String) json.get("deskripsi_kegiatan") : "";
                    Date tanggal_kegiatan = Date.valueOf(json.get("tanggal_kegiatan") != null ? (String) json.get("tanggal_kegiatan") : "");
                    String lokasi_kegiatan = json.get("lokasi_kegiatan") != null ? (String) json.get("lokasi_kegiatan") : "";

                    Kegiatan kegiatan = new Kegiatan(id_kegiatan, nama_kegiatan, target_peserta, deskripsi_kegiatan, tanggal_kegiatan, lokasi_kegiatan);
                    kegiatanList.add(kegiatan);
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    private static class KegiatanTask extends AsyncTask<Void, Void, Void> {

        private WeakReference<CalendarDetail> activityReference;

        KegiatanTask(CalendarDetail context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            StringBuilder result = new StringBuilder();
            try {
                // TODO: getAllWHAT?
                URL url = new URL("http://pplk2a.if.itb.ac.id/ppl/getAllWHAT.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                if (connection.getResponseCode() == 200) {
                    InputStream responseBody = connection.getInputStream();
                    JSONParser jsonParser = new JSONParser();
                    JSONArray jsonArray = (JSONArray) jsonParser.parse(new InputStreamReader(responseBody, "UTF-8"));
                    CalendarDetail.arr_kegiatan = jsonArray;
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
            activity.setKegiatan();
        }
    }
}
