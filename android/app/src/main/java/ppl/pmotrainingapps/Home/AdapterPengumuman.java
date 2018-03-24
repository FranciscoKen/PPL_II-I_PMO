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
import ppl.pmotrainingapps.content.Content;

public class AdapterPengumuman extends RecyclerView.Adapter<AdapterPengumuman.MyViewHolder> {

    private Context mContext;
    private List<Pengumuman> pengumumanList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, details;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            details = (TextView) view.findViewById(R.id.details);
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
        Pengumuman pengumuman = pengumumanList.get(position);
        final int id = pengumuman.getId();
        holder.title.setText(pengumuman.getTitle());
        holder.details.setText(pengumuman.getDetails());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                ItemClick(v, id);

            }
        });
    }

    public void ItemClick(View v, int id) {
        Intent intent = new Intent(v.getContext(), Content.class);
        Bundle b = new Bundle();
        b.putInt("idpengumuman", id); //Your id
        intent.putExtras(b); //Put your id to your next Intent
        v.getContext().startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return pengumumanList.size();
    }


}
