package ppl.pmotrainingapps.Home;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.simple.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.List;

import ppl.pmotrainingapps.Pengumuman.Pengumuman;
import ppl.pmotrainingapps.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private NestedScrollView nestedScrollView;
    private AdapterPengumuman adapter;
    private List<Pengumuman> pengumumanList;
    private TextView quote_content;
    private TextView quote_author;;

    public static JSONObject hasilQuote = null;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedscroll);
        recyclerView.setFocusable(false);
        nestedScrollView.requestFocus();
        pengumumanList = new ArrayList<>();
        adapter = new AdapterPengumuman(getContext(), pengumumanList);

        quote_content = (TextView) view.findViewById(R.id.quotes_content);
        quote_author = (TextView) view.findViewById(R.id.quotes_author);

        //Initialize quote content
        Intent getQuote = new Intent(getContext(), QuoteGetterService.class);
        getQuote.putExtra("url", "http://pplk2a.if.itb.ac.id/ppl/getAllQuotes.php");
        this.getContext().startService(getQuote);

        if (hasilQuote != null) {
            //Log.d("testing", "hasil yang didapat:" + hasilQuote.get(0).toString());
//            for (int iterator = 0; iterator < hasilQuote.size(); iterator++) {
//                String hasilFetch = hasilQuote.get(iterator).toString();
//                try {
//                    JSONObject json = (JSONObject) new JSONParser().parse(hasilFetch);
//                    String quoteString = (String) json.get("quote");
//                    String authorString = (String) json.get("author");
//
//                    Log.d("testing final", "username: " + quoteString + " password: " + authorString);
//                    quote_content.setText(quoteString);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
            try {
                String quoteString = (String) hasilQuote.get("quote");
                String authorString = (String) hasilQuote.get("author");

                Log.d("testing final", "username: " + quoteString + " password: " + authorString);
                quote_content.setText(quoteString);
                quote_author.setText(authorString);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        Glide.with(getContext()).load(R.drawable.ppl_quotes).into((ImageView) view.findViewById(R.id.quotes_frame));
        preparePengumuman(inflater,container);


        return view;
    }

    private void preparePengumuman (LayoutInflater inflater, ViewGroup container) {


        Pengumuman a = new Pengumuman(1, "Pelatihan Kepemimpinan", "Minggu, 3 Maret 2018");
        pengumumanList.add(a);

        a = new Pengumuman(2, "Pelatihan Kepemimpinan", "Minggu, 3 Maret 2018");
        pengumumanList.add(a);

        a = new Pengumuman(3, "Pelatihan Kepemimpinan", "Minggu, 3 Maret 2018");
        pengumumanList.add(a);

        a = new Pengumuman(4, "Pelatihan Kepemimpinan", "Minggu, 3 Maret 2018");
        pengumumanList.add(a);

        a = new Pengumuman(5, "Pelatihan Kepemimpinan", "Minggu, 3 Maret 2018");
        pengumumanList.add(a);

        a = new Pengumuman(6, "Pelatihan Kepemimpinan", "Minggu, 3 Maret 2018");
        pengumumanList.add(a);

        a = new Pengumuman(7, "Pelatihan Kepemimpinan", "Minggu, 3 Maret 2018");
        pengumumanList.add(a);

        a = new Pengumuman(8, "Pelatihan Kepemimpinan", "Minggu, 3 Maret 2018");
        pengumumanList.add(a);

        a = new Pengumuman(9, "Pelatihan Kepemimpinan", "Minggu, 3 Maret 2018");
        pengumumanList.add(a);

        adapter.notifyDataSetChanged();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx ( int dp){
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
