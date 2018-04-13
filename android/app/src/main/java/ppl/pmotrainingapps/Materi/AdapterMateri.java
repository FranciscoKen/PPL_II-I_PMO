package ppl.pmotrainingapps.Materi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import ppl.pmotrainingapps.Home.AdapterPengumuman;
import ppl.pmotrainingapps.Pengumuman.Pengumuman;
import ppl.pmotrainingapps.R;
import ppl.pmotrainingapps.content.Content;

public class AdapterMateri extends RecyclerView.Adapter<AdapterMateri.MyViewHolder>{
    private Context mContext;
    private List<Materi> materiList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView judul, topik;
        public MyViewHolder(View view) {
            super(view);
            judul = (TextView) view.findViewById(R.id.judulMateri);
            topik = (TextView) view.findViewById(R.id.topikMateri);
        }
    }

    public AdapterMateri(Context mContext, List<Materi> materiList) {
        this.mContext = mContext;
        this.materiList = materiList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_materi, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Materi materi = materiList.get(position);
        final int id = materi.getIdMateri();
        holder.judul.setText(materi.getJudul());
        holder.topik.setText(materi.getTopik());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ItemClick(v, materi);
           }
        });
    }

    public void ItemClick(View v, Materi materi) {
        //Intent intent = new Intent(v.getContext(), Content.class);
        Bundle b = new Bundle();
        b.putInt("id_materi", materi.getIdMateri());
        b.putString("topik_materi", materi.getTopik());
        b.putString("judul_materi", materi.getJudul());
        b.putString("konten_materi", materi.getKonten());
        b.putString("video_materi", materi.getVideo());
        b.putString("pdf_materi", materi.getPdf());
        Log.d("test", "berhasil diklik");
//        intent.putExtras(b);
//        v.getContext().startActivity(intent);
    }

    @Override
    public int getItemCount() {return materiList.size();}
}
