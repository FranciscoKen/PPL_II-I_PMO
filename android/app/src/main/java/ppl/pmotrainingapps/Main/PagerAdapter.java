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
    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new BeritaFragment();
            case 2:
                return new CalendarFragment();
            case 3:
                return new MateriFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
