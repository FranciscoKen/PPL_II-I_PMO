package ppl.pmotrainingapps.calendar;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.json.JSONObject;
import org.json.simple.JSONArray;

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
}
