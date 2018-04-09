package ppl.pmotrainingapps.calendar;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ppl.pmotrainingapps.R;

public class CalendarFragment extends Fragment {
    private Toolbar toolbar;
    CompactCalendarView compactCalendarView;

    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());

    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    private TextView monthHeader;

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

    //    @Override
//    protected void onViewCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_main);
//        return new R.layout.calendar_layout;
//
//
//        toolbar = (Toolbar) findViewById(getContext().R.id.toolbar);
//        setSupportActionBar(toolbar);
//
////        final ActionBar actionBar = getSupportActionBar();
//////        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24px);
//////        actionBar.setDisplayHomeAsUpEnabled(true);
////        // Setting default toolbar title to empty
////        actionBar.setNama(null);
//
//
//        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
//        compactCalendarView.shouldDrawIndicatorsBelowSelectedDays(true);
//        compactCalendarView.setUseThreeLetterAbbreviation(true);
//
//        //set initial title
//        //actionBar.setNama(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
//
//        //set title on calendar scroll
//        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
//            @Override
//            public void onDayClick(Date dateClicked) {
//
//
//                Toast.makeText(MainActivity.this, "Date : " + dateClicked.toString(), Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onMonthScroll(Date firstDayOfNewMonth) {
//                // Changes toolbar title on monthChange
//                //actionBar.setNama(dateFormatForMonth.format(firstDayOfNewMonth));
//                toolbar.setNama(dateFormatForMonth.format(firstDayOfNewMonth));
//
//            }
//
//        });
//
//
//        addDummyEvents();
//
//        //  gotoToday();
//
//
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_layout, container, false);

//        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
//        setSupportActionBar(toolbar);

//        final ActionBar actionBar = getSupportActionBar();
////        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24px);
////        actionBar.setDisplayHomeAsUpEnabled(true);
//        // Setting default toolbar title to empty
//        actionBar.setNama(null);
        monthHeader = (TextView) view.findViewById(R.id.nama_bulan);
        compactCalendarView = (CompactCalendarView) view.findViewById(R.id.compactcalendar_view);

        compactCalendarView.shouldDrawIndicatorsBelowSelectedDays(true);
        compactCalendarView.setUseThreeLetterAbbreviation(true);

        //set initial title
        //actionBar.setNama(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
        monthHeader.setText(dateFormatForMonth.format(new Date()));
        //set title on calendar scroll
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {


                Toast.makeText(getActivity(), "Date : " + dateClicked.toString(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                // Changes toolbar title on monthChange
                //actionBar.setNama(dateFormatForMonth.format(firstDayOfNewMonth));
                //toolbar.setNama(dateFormatForMonth.format(firstDayOfNewMonth));
                monthHeader.setText(dateFormatForMonth.format(firstDayOfNewMonth));
            }

        });


        addDummyEvents();

        //  gotoToday();

        return view;

    }

    // Adding dummy events in calendar view for April, may, june 2016
    private void addDummyEvents() {

        addEvents(compactCalendarView, Calendar.APRIL);
        addEvents(compactCalendarView, Calendar.MAY);
        addEvents(compactCalendarView, Calendar.JULY);

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


    private void setToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }


    public void gotoToday() {

        // Set any date to navigate to particular date
        compactCalendarView.setCurrentDate(Calendar.getInstance(Locale.getDefault()).getTime());


    }
}
