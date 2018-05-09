package ppl.pmotrainingapps.berita;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import ppl.pmotrainingapps.R;
import ppl.pmotrainingapps.Pengumuman.Content;

/**
 * Created by Arya Pradipta on 08/04/2018.
 */

public class AdapterBerita extends RecyclerView.Adapter<AdapterBerita.MyViewHolder> {

    private Context mContext;
    private List<Berita> beritaList;

    public static class Berita {
        private int id;
        private String judul;
        private String tanggal;
        private String image;

        public Berita(int id, String judul, String tanggal, String image) {
            this.id = id;
            this.judul = judul;
            this.tanggal = tanggal;
            this.image = image;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getJudul() {
            return judul;
        }

        public void setJudul(String judul) {
            this.judul = judul;
        }

        public String getTanggal() {
            return tanggal;
        }

        public void setTanggal(String tanggal) {
            this.tanggal = tanggal;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView judul, tanggal;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            judul = (TextView) view.findViewById(R.id.judul);
            tanggal = (TextView) view.findViewById(R.id.tanggal);
            image = (ImageView) view.findViewById(R.id.imageView);
        }
    }

    public AdapterBerita(Context mContext, List<Berita> beritaList) {
        this.mContext = mContext;
        this.beritaList = beritaList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_berita, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Berita berita = beritaList.get(position);
        final int id = berita.getId();
        holder.judul.setText(berita.getJudul());
        holder.tanggal.setText(berita.getTanggal());
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        Glide.with(mContext).load(berita.getImage()).apply(options).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                ItemClick(v, berita);

            }
        });
    }

    public void ItemClick(View v, Berita berita) {
        Intent intent = new Intent(v.getContext(), BeritaActivity.class);
        Bundle b = new Bundle();
        b.putInt("id", berita.getId()); //Your id
        intent.putExtras(b); //Put your id to your next Intent
        v.getContext().startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return beritaList.size();
    }


}
