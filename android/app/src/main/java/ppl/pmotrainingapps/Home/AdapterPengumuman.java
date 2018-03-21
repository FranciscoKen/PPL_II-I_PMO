package ppl.pmotrainingapps.Home;

/**
 * Created by David on 3/21/2018.
 */


import android.content.Context;
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

import ppl.pmotrainingapps.Pengumuman.Pengumuman;
import ppl.pmotrainingapps.R;

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
        holder.title.setText(pengumuman.getTitle());
        holder.details.setText(pengumuman.getDetails());

    }




    @Override
    public int getItemCount() {
        return pengumumanList.size();
    }
}
