package ppl.pmotrainingapps.calendar;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.List;

import ppl.pmotrainingapps.R;

/**
 * Created by ayamberkakienam on 4/5/2018.
 */

public class CalendarDetailAdapter extends RecyclerView.Adapter<CalendarDetailAdapter.CalendarViewHolder> {

    private List<Kegiatan> kegiatanList;

    public class CalendarViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        TextView target;
        TextView lokasi;
        TextView waktu;
        TextView deskripsi;

        CalendarViewHolder(View itemView) {
            super(itemView);
            title       = itemView.findViewById(R.id.kegiatan_nama);
            target      = itemView.findViewById(R.id.kegiatan_target);
            lokasi      = itemView.findViewById(R.id.kegiatan_lokasi);
            waktu       = itemView.findViewById(R.id.kegiatan_waktu);
            deskripsi   = itemView.findViewById(R.id.kegiatan_deskripsi);
        }
    }

    CalendarDetailAdapter(List<Kegiatan> kegiatanList) {
        this.kegiatanList = kegiatanList;
    }

    @Override
    public CalendarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_calendar_kegiatan, parent, false);

        return new CalendarViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CalendarViewHolder holder, int position) {
        Kegiatan kegiatan = kegiatanList.get(position);
        holder.title.setText(kegiatan.getNama());
        holder.target.setText(kegiatan.getTarget());
        holder.lokasi.setText(kegiatan.getLokasi());
        holder.waktu.setText(kegiatan.getTanggal());
        holder.deskripsi.setText(kegiatan.getDeskripsi());
    }

    @Override
    public int getItemCount() {
        return kegiatanList.size();
    }
}
