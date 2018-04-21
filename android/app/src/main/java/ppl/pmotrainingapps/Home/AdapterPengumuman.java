package ppl.pmotrainingapps.Home;

/**
 * Created by David on 3/21/2018.
 */


import android.app.Application;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

import ppl.pmotrainingapps.Main.MainActivity;
import ppl.pmotrainingapps.Pengumuman.Pengumuman;
import ppl.pmotrainingapps.R;
import ppl.pmotrainingapps.Pengumuman.Content;

public class AdapterPengumuman extends RecyclerView.Adapter<AdapterPengumuman.MyViewHolder> {

    private Context mContext;
    private List<Pengumuman> pengumumanList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView judul, tanggal;

        public MyViewHolder(View view) {
            super(view);
            judul = (TextView) view.findViewById(R.id.judul);
            tanggal = (TextView) view.findViewById(R.id.tanggal);
        }


    }


    public AdapterPengumuman(Context mContext, List<Pengumuman> pengumumanList) {
        this.mContext = mContext;
        this.pengumumanList = pengumumanList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_pengumuman, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Pengumuman pengumuman = pengumumanList.get(position);
        holder.judul.setText(pengumuman.getJudul());
        holder.tanggal.setText(pengumuman.getTanggal());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                ItemClick(v, pengumuman);

            }
        });
    }

    public void ItemClick(View v, Pengumuman pengumuman) {
        Intent intent = new Intent(v.getContext(), Content.class);
        Bundle b = new Bundle();
        b.putInt("id_pengumuman", pengumuman.getId_pengumuman()); //Your id
        b.putInt("id_kegiatan", pengumuman.getId_kegiatan()); //Your id
        b.putString("judul", pengumuman.getJudul()); //Your id
        b.putString("tanggal", pengumuman.getTanggal()); //Your id
        b.putString("konten_teks", pengumuman.getKonten_teks()); //Your id
        b.putString("konten_gambar", pengumuman.getKonten_gambar()); //Your id

        intent.putExtras(b); //Put your id to your next Intent
        v.getContext().startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return pengumumanList.size();
    }


}
