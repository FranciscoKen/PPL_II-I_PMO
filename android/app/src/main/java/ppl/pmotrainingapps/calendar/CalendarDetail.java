package ppl.pmotrainingapps.calendar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import ppl.pmotrainingapps.R;

public class CalendarDetail extends AppCompatActivity {

    public static JSONArray arr_kegiatan = null;
    public static JSONArray arr_hari_besar = null;

    static String dateString;
    private TextView num;
    private TextView haribesar;
    private CalendarDetailAdapter adapter;
    private List<Kegiatan> kegiatanList;

    public CalendarDetail() {
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_detail);

        Intent intent = getIntent();
        dateString = intent.getStringExtra("date");
        String[] date_arr = dateString.split("-");

        String day = date_arr[2];
        String month = new DateFormatSymbols().getMonths()[Integer.parseInt(date_arr[1]) - 1];
        String year = date_arr[0];

        TextView date = findViewById(R.id.calendar_detail_date);
        date.setText(day + " " + month + " " + year);

        num = findViewById(R.id.calendar_detail_num);
        haribesar = findViewById(R.id.calendar_detail_haribesar);
        RecyclerView events = findViewById(R.id.calendar_detail_events);

        haribesar.setTextColor(Color.parseColor("#980000"));

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
        new CalendarDetailTask(this).execute();
    }

    public void setKegiatan() {
        if (arr_kegiatan != null) {
            Log.d("arr_kegiatan_size", "arr_kegiatan size: " + arr_kegiatan.size());
            for (int i = 0; i < arr_kegiatan.size(); i++) {
                String hasilFetch = arr_kegiatan.get(i).toString();
                Log.d("kegiatan_fetch", "hasilFetch " + i + ": " + hasilFetch);
                try {
                    JSONObject json = (JSONObject) new JSONParser().parse(hasilFetch);
                    int id_kegiatan = json.get("id_kegiatan") != null ? Integer.parseInt((String) json.get("id_kegiatan")) : -1;
                    String nama_kegiatan = json.get("nama_kegiatan") != null ? (i + 1) + ". " + json.get("nama_kegiatan") : "";
                    String target_peserta = json.get("target_peserta") != null ? (String) json.get("target_peserta") : "";
                    String deskripsi_kegiatan = json.get("deskripsi_kegiatan") != null ? (String) json.get("deskripsi_kegiatan") : "";
                    String tanggal_kegiatan = json.get("tanggal_kegiatan") != null ? (String) json.get("tanggal_kegiatan") : "";
                    String lokasi_kegiatan = json.get("lokasi_kegiatan") != null ? (String) json.get("lokasi_kegiatan") : "";

                    Kegiatan kegiatan = new Kegiatan(id_kegiatan, nama_kegiatan, target_peserta, deskripsi_kegiatan, tanggal_kegiatan, lokasi_kegiatan);
                    Log.d("kegiatan_add_status", "Success: " + i);
                    kegiatanList.add(kegiatan);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void setHariBesar() {
        String semuaHariBesar = "";
        if (arr_hari_besar != null) {
            Log.d("arr_kegiatan_size", "arr_kegiatan size: " + arr_kegiatan.size());
            for (int i = 0; i < arr_hari_besar.size(); i++) {
                String hasilFetch = arr_hari_besar.get(i).toString();
                Log.d("kegiatan_fetch", "hasilFetch " + i + ": " + hasilFetch);
                try {
                    JSONObject json = (JSONObject) new JSONParser().parse(hasilFetch);
                    int id_haribesar = json.get("id") != null ? Integer.parseInt((String) json.get("id")) : -1;
                    String nama_haribesar = json.get("nama") != null ? json.get("nama").toString() : "";

                    semuaHariBesar = semuaHariBesar.concat(nama_haribesar+'\n');
                    Log.d("haribesar_add_status", "Success: " + i);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if(arr_hari_besar.size()>0){
                JSONObject json = (JSONObject) arr_hari_besar.get(0);
            }

            haribesar.setText(semuaHariBesar);
        }
    }

    @SuppressLint("SetTextI18n")
    public void setKegiatanNum() {
        if (arr_kegiatan != null) {
            int knum = arr_kegiatan.size();
            num.setText(knum + " agenda(s)");
        }
    }

    private class CalendarDetailTask extends AsyncTask<Void, Void, Void> {

        private WeakReference<CalendarDetail> activityReference;

        CalendarDetailTask(CalendarDetail context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL(getString(R.string.endpointURI) + "getCalendarDetailByDate.php?date=" + dateString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                if (connection.getResponseCode() == 200) {
                    InputStream responseBody = connection.getInputStream();
                    JSONParser jsonParser = new JSONParser();
                    JSONArray jsonArray = (JSONArray) jsonParser.parse(new InputStreamReader(responseBody, "UTF-8"));
                    CalendarDetail.arr_hari_besar = (JSONArray) jsonArray.get(0);
                    CalendarDetail.arr_kegiatan = (JSONArray) jsonArray.get(1);
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
            activity.setHariBesar();
            activity.setKegiatanNum();
        }
    }
}
