package ppl.pmotrainingapps.calendar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import ppl.pmotrainingapps.R;

public class CalendarFragment extends Fragment {
    private Toolbar toolbar;
    CompactCalendarView compactCalendarView;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonthOnly = new SimpleDateFormat("MM", Locale.getDefault());
    private SimpleDateFormat dateFormatForYearOnly = new SimpleDateFormat("yyyy", Locale.getDefault());

    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    private TextView monthHeader;

    public static final String TAG = "CalendarFragment";
    public static JSONArray hasilEvent = null;
    public static JSONArray hasilHariBesar = null;

    public RequestedDate thisYear;

    public CalendarFragment() {

    }

    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_layout, container, false);

        monthHeader = (TextView) view.findViewById(R.id.nama_bulan);
        compactCalendarView = (CompactCalendarView) view.findViewById(R.id.compactcalendar_view);

        compactCalendarView.shouldDrawIndicatorsBelowSelectedDays(true);
        compactCalendarView.setUseThreeLetterAbbreviation(true);

        //set initial title
        monthHeader.setText(dateFormatForMonth.format(new Date()));
        //set title on calendar scroll
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                if(hasilEvent != null){
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
                    Intent event = new Intent(getActivity(), CalendarDetail.class);
                    event.putExtra("date", dt.format(dateClicked));
                    startActivity(event);
                } else {
                    Toast.makeText(getActivity(),"Loading events.. please try again",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                // Changes toolbar title on monthChange
                monthHeader.setText(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });

        addDummyEvents();

        gotoToday();

        return view;

    }

    // Adding dummy events in calendar view for April, may, june 2016
    private void addDummyEvents() {
        int temp_this_year = Calendar.getInstance().get(Calendar.YEAR);
        thisYear = new RequestedDate(temp_this_year);
        prepareCalendar(thisYear);

        // Refresh calendar to update events
        compactCalendarView.invalidate();
    }


    // Adding events from 1 to 6 days

    private void addEvents(CompactCalendarView compactCalendarView, int month) {
        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = currentCalender.getTime();
        for (int i = 0; i < 6; i++) {
            currentCalender.setTime(firstDayOfMonth);
            if (month > -1) {
                currentCalender.set(Calendar.MONTH, month);
            }
            currentCalender.add(Calendar.DATE, i);
            setToMidnight(currentCalender);
            compactCalendarView.addEvent(new Event(Color.argb(255, 0, 253, 0), currentCalender.getTimeInMillis()), false);
        }
    }

    @SuppressLint("LongLogTag")
    private void addKegiatan(CompactCalendarView compactCalendarView, int year){
        if (year > -1) {
            try {
                Iterator i = hasilEvent.iterator();
                while(i.hasNext()){
                    JSONObject JDate = (JSONObject) i.next();
                    String temp_date = (String) JDate.get("tanggal_kegiatan").toString().split(" ")[0];
                    Log.d(TAG+" PARSEDATE_KEGIATAN", temp_date);
                    long dateInMillis = dateFormat.parse(temp_date).getTime();
                    Event event = new Event(Color.argb(255, 0, 253, 0), dateInMillis);
                    Log.d(TAG+" OBJEV_HARIBESAR", event.toString());
                    compactCalendarView.addEvent(event, true);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            Log.d(TAG+" YEARVAL_EVENT","<=-1");
        }
    }

    @SuppressLint("LongLogTag")
    private void addHariBesar(CompactCalendarView compactCalendarView, int year){
        if (year > -1) {
            try {
                Iterator i = hasilHariBesar.iterator();
                while(i.hasNext()){
                    JSONObject JDate = (JSONObject) i.next();
                    String temp_date = JDate.get("tgl").toString().split(" ")[0];
                    Log.d(TAG+" PARSEDATE_HARIBESAR", temp_date);
                    long dateInMillis = dateFormat.parse(temp_date).getTime();
                    Event event = new Event(Color.argb(255, 130, 0, 114), dateInMillis);
                    Log.d(TAG+" OBJEV_HARIBESAR", event.toString());
                    compactCalendarView.addEvent(event, true);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            Log.d(TAG+" YEARVAL_HARIBESAR","<=-1");
        }
    }


    private void setToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private void setTime(Calendar calendar,int hour, int min, int sec, int millisec) {
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, sec);
        calendar.set(Calendar.MILLISECOND, millisec);
    }


    public void gotoToday() {

        // Set any date to navigate to particular date
        compactCalendarView.setCurrentDate(Calendar.getInstance(Locale.getDefault()).getTime());


    }
    private void prepareCalendar(RequestedDate monthToGet) {
        new CalendarTask(this).execute(monthToGet);
    }

    private class CalendarTask extends AsyncTask<RequestedDate, Void, Void> {

        private WeakReference<CalendarFragment> activityReference;
        public int month;
        public int year;

        CalendarTask(CalendarFragment context) {
            activityReference = new WeakReference<>(context);
        }

        @SuppressLint("LongLogTag")
        protected Void doInBackground(RequestedDate... monthToget) {
            StringBuilder result = new StringBuilder();
            month = monthToget[0].getMonth();
            year = monthToget[0].getYear();
            try{
                URL url = new URL(getString(R.string.endpointURI) + "getAllTanggalKegiatanTigaTahun.php?year="+ year);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                if(connection.getResponseCode() == 200) {
                    InputStream responseBody = connection.getInputStream();
                    JSONParser jsonParser = new JSONParser();
                    org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) jsonParser.parse(new InputStreamReader(responseBody, "UTF-8"));
                    CalendarFragment.hasilEvent = jsonArray;
                    Log.d(TAG+" JSONRESULT_EVENT",jsonArray.toString());

                }else{
                    Log.d(TAG+" CONN_EVENT", "failed");
                }
            }catch(Exception e){
                e.printStackTrace();
            }

            try{
                URL url = new URL(getString(R.string.endpointURI) + "getAllTanggalHariBesarTigaTahun.php?year="+year);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                if(connection.getResponseCode() == 200) {
                    InputStream responseBody = connection.getInputStream();
                    JSONParser jsonParser = new JSONParser();
                    org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) jsonParser.parse(new InputStreamReader(responseBody, "UTF-8"));
                    CalendarFragment.hasilHariBesar = jsonArray;
                    Log.d(TAG+" JSONRESULT_HARIBESAR", jsonArray.toString());
                }else{
                    Log.d(TAG+" CONN_HARIBESAR", "failed");
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            // get a reference to the activity if it is still there
            CalendarFragment activity = activityReference.get();
            activity.addKegiatan(activity.compactCalendarView,year);
            activity.addHariBesar(activity.compactCalendarView,year);
        }
    }

    private static class RequestedDate { /* kelas tipe data buat di-pass ke asynctask*/
        int month;
        int year;

        RequestedDate(){
            this.month = -1;
            this.year = -1;
        }

        RequestedDate(int _month, int _year) {
            this.month = _month;
            this.year = _year;
        }

        RequestedDate(int _year) {
            this.month = -1;
            this.year = _year;
        }

        public int getMonth(){
            return this.month;
        }

        public int getYear(){
            return this.year;
        }
    }
}
