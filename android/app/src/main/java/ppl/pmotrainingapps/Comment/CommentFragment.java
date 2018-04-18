package ppl.pmotrainingapps.Comment;


import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import ppl.pmotrainingapps.Home.AdapterPengumuman;
import ppl.pmotrainingapps.Home.HomeFragment;
import ppl.pmotrainingapps.Login.LoginActivity;
import ppl.pmotrainingapps.Pengumuman.Pengumuman;
import ppl.pmotrainingapps.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommentFragment extends Fragment {

    private static final String ARG_PARAM1 = "Jenis";
    private static final String ARG_PARAM2 = "ID";
    public static final String JENIS_BERITA = "berita";
    public static final String JENIS_PENGUMUMAN = "pengumuman";

    private String mJenis;
    private int mID;
    private int mUser_ID;

    //RecyclerView Variables
    public JSONArray commentJSON = null;
    private RecyclerView comment_view;
    private CommentAdapter adapter;
    private List<Comment> commentList;

    public CommentFragment() {}

    public static CommentFragment newInstance(String jenis, int id) {
        CommentFragment fragment = new CommentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, jenis);
        args.putInt(ARG_PARAM2, id);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mJenis = getArguments().getString(ARG_PARAM1);
            mID = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(LoginActivity.SHARED_PREFERENCE_KEY, MODE_PRIVATE);
        mUser_ID = sharedPreferences.getInt("id_user", -1);
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        comment_view = (RecyclerView) view.findViewById(R.id.comment_list);
        commentList = new ArrayList<>();
        adapter = new CommentAdapter(getContext(), commentList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        comment_view.setLayoutManager(mLayoutManager);
        comment_view.setItemAnimator(new DefaultItemAnimator());
        comment_view.setAdapter(adapter);
        prepareComment();
        return view;
    }

    private void prepareComment() {
        new CommentFragment.CommentTask(this).execute();
    }


    public void setComment(){
        if(commentJSON != null) {
            for(int iterator = 0; iterator < commentJSON.size(); iterator++) {
                String hasilFetch = commentJSON.get(iterator).toString();

                try{
                    JSONObject json = (JSONObject) new JSONParser().parse(hasilFetch);
                    int id_komentar = Integer.parseInt((String) json.get("id"));
                    int user_id = Integer.parseInt((String) json.get("user_id"));
                    int konten_id = Integer.parseInt((String) json.get("id"));

                    if(mJenis.equals(CommentFragment.JENIS_PENGUMUMAN)){
                        konten_id = Integer.parseInt((String) json.get("pengumuman_id"));
                    } else if(mJenis.equals(CommentFragment.JENIS_BERITA)){
                        konten_id = Integer.parseInt((String) json.get("berita_id"));
                    }
                    String komentar = (String)json.get("komentar");
                    String tanggal = (String)json.get("tgl");
                    String username = (String)json.get("username");
                    if(username == null){
                        username = "Tanpa Nama";
                    }
                    Comment newcomment = new Comment(id_komentar, mJenis, konten_id, user_id, username, komentar,tanggal);
                    commentList.add(newcomment);

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        adapter.notifyDataSetChanged();
    }
    private static class CommentTask extends AsyncTask<Void, Void, Void> {

        private WeakReference<CommentFragment> activityReference;

        CommentTask(CommentFragment context) {
            activityReference = new WeakReference<>(context);
        }

        protected Void doInBackground(Void... urls) {
            try {
                CommentFragment activity = activityReference.get();
                String url_prefix;
                if(activity.mJenis.equals(CommentFragment.JENIS_PENGUMUMAN)){
                    url_prefix = "http://pplk2a.if.itb.ac.id/ppl/getKmtPengumuman.php";
                } else if(activity.mJenis.equals(CommentFragment.JENIS_BERITA)){
                    url_prefix = "http://pplk2a.if.itb.ac.id/ppl/getKmtBerita.php";

                } else{
                    return null;
                }
                URL url = new URL(url_prefix + "?id=" + activity.mID);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                if (connection.getResponseCode() == 200) {
                    InputStream responseBody = connection.getInputStream();
                    JSONParser jsonParser = new JSONParser();
                    JSONArray jsonArray = (JSONArray) jsonParser.parse(new InputStreamReader(responseBody, "UTF-8"));
                    activity.commentJSON = jsonArray;

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
            CommentFragment activity = activityReference.get();
            activity.setComment();
        }
    }
}
