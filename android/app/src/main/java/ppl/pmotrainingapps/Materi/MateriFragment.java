package ppl.pmotrainingapps.Materi;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ppl.pmotrainingapps.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link MateriFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MateriFragment extends Fragment {
    private final static String TAG = "MateriFragment";
    public static JSONArray materi = null;

    private RecyclerView recyclerView;
    private NestedScrollView nestedScrollView;
    private AdapterMateri adapterMateri;
    private List<Materi> materiList;
    private SwipeRefreshLayout mrefreshLayout;

    public MateriFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MateriFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MateriFragment newInstance(String param1, String param2) {
        MateriFragment fragment = new MateriFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_materi, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_materi);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedScrollMateri);
        recyclerView.setFocusable(false);
        nestedScrollView.requestFocus();
        materiList = new ArrayList<>();
        adapterMateri = new AdapterMateri(getContext(), materiList);
        mrefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mrefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                prepareMateri();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterMateri);
        recyclerView.setNestedScrollingEnabled(false);

        prepareMateri();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void prepareMateri() {
        new MateriTask(this).execute();
    }
    public void setMateri() {
        if(materi != null) {
            materiList.clear();
            for(int iterator = 0; iterator < materi.size(); iterator++) {
                String hasilFetch = materi.get(iterator).toString();

                try {
                    JSONObject json = (JSONObject) new JSONParser().parse(hasilFetch);
                    int id_materi = Integer.parseInt((String)json.get("id"));
                    String topik = (String)json.get("topik");
                    String judul = (String)json.get("judul");
                    String konten = (String)json.get("konten");
                    String video = (String)json.get("video");
                    String pdf = (String)json.get("pdf");

                    Materi materiSekarang = new Materi(id_materi, topik, judul, konten, video, pdf);
                    materiList.add(materiSekarang);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            adapterMateri.notifyDataSetChanged();
            mrefreshLayout.setRefreshing(false);

        }
    }

    private class MateriTask extends AsyncTask<Void, Void, Void> {
        private WeakReference<MateriFragment> activityReference;
        MateriTask(MateriFragment context) {
            activityReference = new WeakReference<>(context);
        }

        protected Void doInBackground(Void... urls) {
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL(getString(R.string.endpointURI) + "getAllMateri.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                if(connection.getResponseCode() == 200) {
                    InputStream responseBody = new BufferedInputStream(connection.getInputStream());

                    JSONParser jsonParser = new JSONParser();
                    JSONArray jsonArray = (JSONArray) jsonParser.parse(new InputStreamReader(responseBody, "UTF-8"));
                    MateriFragment.materi = jsonArray;


                }else{
                    Log.d("test", "connection failed");
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            MateriFragment activity = activityReference.get();
            activity.setMateri();
        }

    }
}
