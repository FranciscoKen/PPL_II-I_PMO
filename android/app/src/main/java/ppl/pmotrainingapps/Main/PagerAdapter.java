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
    HomeFragment mhome;
    BeritaFragment mberita;
    CalendarFragment mkalender;
    MateriFragment mmateri;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                mhome = mhome != null ? mhome : new HomeFragment();
                return mhome;
            case 1:
                mberita = mberita != null ? mberita : new BeritaFragment();
                return mberita;
            case 2:
                mkalender = mkalender != null ? mkalender : new CalendarFragment();
                return  mkalender;
            case 3:
                mmateri = mmateri != null ? mmateri : new MateriFragment();
                return mmateri;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
