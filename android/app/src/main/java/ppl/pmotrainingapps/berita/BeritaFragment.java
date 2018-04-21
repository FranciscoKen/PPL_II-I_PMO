package ppl.pmotrainingapps.berita;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
// * {@link BeritaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BeritaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BeritaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static JSONArray berita = null;
    private RecyclerView recyclerView;
    private NestedScrollView nestedScrollView;
    private List<AdapterBerita.Berita> beritaList;
    private AdapterBerita adapterBerita;
    private SwipeRefreshLayout mrefreshLayout;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

//    private OnFragmentInteractionListener mListener;

    public BeritaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BeritaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BeritaFragment newInstance(String param1, String param2) {
        BeritaFragment fragment = new BeritaFragment();
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
        View view = inflater.inflate(R.layout.fragment_berita, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.berita_recycler_view);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.berita_nestedscroll);
        recyclerView.setFocusable(false);
        nestedScrollView.requestFocus();
        beritaList = new ArrayList<>();
        adapterBerita = new AdapterBerita(getContext(), beritaList);
        mrefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mrefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                prepareBerita();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterBerita);
        recyclerView.setNestedScrollingEnabled(false);

        prepareBerita();
        return view;
    }

    private void prepareBerita() {
        new BeritaTask(this).execute();
    }

    public void setBerita(){
        if(berita != null) {
            beritaList.clear();
            for(int iterator = 0; iterator < berita.size(); iterator++) {
                String hasilFetch = berita.get(iterator).toString();

                try{
                    JSONObject json = (JSONObject) new JSONParser().parse(hasilFetch);
                    int id = Integer.parseInt((String) json.get("id"));
                    String judul = (String)json.get("judul");
                    String tanggal = (String)json.get("tgl");

                    AdapterBerita.Berita a = new AdapterBerita.Berita(id, judul, tanggal);
                    beritaList.add(a);

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        adapterBerita.notifyDataSetChanged();
        mrefreshLayout.setRefreshing(false);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    private static class BeritaTask extends AsyncTask<Void, Void, Void> {

        private WeakReference<BeritaFragment> activityReference;

        BeritaTask(BeritaFragment context) {
            activityReference = new WeakReference<>(context);
        }

        protected Void doInBackground(Void... urls) {
            try {
                URL url = new URL("http://pplk2a.if.itb.ac.id/ppl/getAllBerita.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                if (connection.getResponseCode() == 200) {
                    InputStream responseBody = connection.getInputStream();
                    JSONParser jsonParser = new JSONParser();
                    JSONArray jsonArray = (JSONArray) jsonParser.parse(new InputStreamReader(responseBody, "UTF-8"));
                    BeritaFragment.berita = jsonArray;

                } else {
                    Log.d("test", "connection failed");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            // get a reference to the activity if it is still there
            BeritaFragment activity = activityReference.get();
            activity.setBerita();
        }
    }

}
