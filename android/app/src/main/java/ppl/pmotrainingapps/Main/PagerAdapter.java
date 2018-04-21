package ppl.pmotrainingapps.Main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ppl.pmotrainingapps.BlankFragment;
import ppl.pmotrainingapps.Home.HomeFragment;
import ppl.pmotrainingapps.Materi.MateriFragment;
import ppl.pmotrainingapps.berita.BeritaFragment;
import ppl.pmotrainingapps.calendar.CalendarFragment;

/**
 * Created by David on 2/4/2018.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    HomeFragment mHome;
    BeritaFragment mBerita;
    CalendarFragment mCalendar;
    MateriFragment mMateri;
    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                mHome = mHome != null ? mHome : new HomeFragment();
                return mHome;
            case 1:
                mBerita = mBerita != null ? mBerita : new BeritaFragment();
                return mBerita;
            case 2:
                mCalendar = mCalendar != null ? mCalendar : new CalendarFragment();
                return mCalendar;
            case 3:
                mMateri = mMateri != null ? mMateri : new MateriFragment();
                return mMateri;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
