package ppl.pmotrainingapps.calendar;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ayamberkakienam on 4/5/2018.
 */

public class CalendarDetailAdapter extends RecyclerView.Adapter<CalendarDetailAdapter.CalendarViewHolder> {

    public class CalendarViewHolder extends RecyclerView.ViewHolder {

        public CalendarViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public CalendarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(CalendarViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
