package ppl.pmotrainingapps.Pengumuman;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import ppl.pmotrainingapps.Comment.CommentFragment;
import ppl.pmotrainingapps.Home.HomeFragment;
import ppl.pmotrainingapps.R;

public class Content extends AppCompatActivity {

    TextView headerText;
    TextView dateText;
    TextView contentText;
    ImageView headerImage;
    private Fragment mCommentCardFragment;
    int id_pengumuman;
    int id_kegiatan;
    int id;

    public static JSONArray pengumuman;
    public static JSONArray kegiatan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        headerImage = findViewById(R.id.headerImage);
        contentText = findViewById(R.id.contentText);
        headerText =  findViewById(R.id.headerText);
        dateText =  findViewById(R.id.headerDate);

        Bundle b = getIntent().getExtras();
        int value = -1; // or other values
        id = b.getString("id") == null ? -1 : Integer.parseInt(b.getString("id"));

        if(id == -1){
            contentText.setText(b.getString("konten_teks"));
            headerText.setText(b.getString("judul"));
            dateText.setText(b.getString("tanggal"));
            String image_url = b.getString("konten_gambar");
            id_kegiatan = b.getInt("id_kegiatan");
            id_pengumuman = b.getInt("id_pengumuman");

            if(!image_url.equals("") ){
                RequestOptions options = new RequestOptions()
                        .centerCrop().override(1000,1000)
                        .placeholder(R.mipmap.ic_launcher_round)
                        .error(R.mipmap.ic_launcher_round);
                Glide.with(this).load(image_url).apply(options).into(headerImage);
            }
            //tidak ada arr_kegiatan
            if(id_kegiatan == -1){
                findViewById(R.id.kegiatan).setVisibility(View.GONE);
            } else{
                ((TextView)findViewById(R.id.namakegiatan)).setText(Integer.toString(id_kegiatan));
                ((TextView)findViewById(R.id.targetpeserta)).setText("");
                ((TextView)findViewById(R.id.waktu)).setText("");
                ((TextView)findViewById(R.id.deskripsi)).setText("");
                ((TextView)findViewById(R.id.lokasi)).setText("");
                new KegiatanTask(this).execute();
            }
            initComment();

        } else{
            new PengumumanTask(this).execute();
        }
    }
    public void setContent(){
        if(pengumuman != null) {
            for(int iterator = 0; iterator < pengumuman.size(); iterator++) {
                String hasilFetch = pengumuman.get(iterator).toString();
                try{
                    JSONObject json = (JSONObject) new JSONParser().parse(hasilFetch);
                    int id_peng = Integer.parseInt((String) json.get("id_pengumuman"));
                    if(id_peng == id){
                        String judul = (String)json.get("judul");
                        String tanggal = (String)json.get("tanggal");
                        int id_kegiatan = json.get("kegiatan_id") != null ? Integer.parseInt((String) json.get("kegiatan_id")) : -1;
                        String konten_teks = json.get("konten_teks") != null ? (String)json.get("konten_teks") : "";
                        String konten_gambar = json.get("konten_gambar") != null ? (String)json.get("konten_gambar") : "";

                        contentText.setText(konten_teks);
                        headerText.setText(judul);
                        dateText.setText(tanggal);

                        if(!konten_gambar.equals("") ){
                            RequestOptions options = new RequestOptions()
                                    .centerCrop().override(1000,1000)
                                    .placeholder(R.mipmap.ic_launcher_round)
                                    .error(R.mipmap.ic_launcher_round);
                            Glide.with(this).load(konten_gambar).apply(options).into(headerImage);
                        }
                        //tidak ada arr_kegiatan
                        if(id_kegiatan == -1){
                            findViewById(R.id.kegiatan).setVisibility(View.GONE);
                        } else{
                            new KegiatanTask(this).execute();
                        }
                        id_pengumuman = id;
                        initComment();
                    }

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }
    public void initComment(){
        final FragmentManager fragmentManager = getSupportFragmentManager();
        if (mCommentCardFragment == null) {
            mCommentCardFragment = CommentFragment.newInstance(CommentFragment.JENIS_PENGUMUMAN, id_pengumuman);

            fragmentManager.beginTransaction()
                    .replace(R.id.product_comment_fragment_container, mCommentCardFragment)
                    .commit();
        }
    }

    public void setKegiatan(){
        if(kegiatan != null){
            for(int iterator = 0; iterator < kegiatan.size(); iterator++) {
                String hasilFetch = kegiatan.get(iterator).toString();

                try{
                    JSONObject json = (JSONObject) new JSONParser().parse(hasilFetch);
                    int id_kegiatan_cek = Integer.parseInt((String) json.get("id_kegiatan"));
                    if(id_kegiatan_cek == id_kegiatan) {
                        String nama = (String)json.get("nama_kegiatan");
                        String target = (String)json.get("target_peserta");
                        String waktu = json.get("tanggal_kegiatan") != null ? (String)json.get("tanggal_kegiatan") : "Tentatif";
                        String deskripsi = json.get("deskripsi_kegiatan") != null ? (String)json.get("deskripsi_kegiatan") : "";
                        String lokasi = json.get("lokasi_kegiatan") != null ? (String)json.get("lokasi_kegiatan") : "Tentatif";
                        ((TextView)findViewById(R.id.namakegiatan)).setText(nama);
                        ((TextView)findViewById(R.id.targetpeserta)).setText(target);
                        ((TextView)findViewById(R.id.waktu)).setText(waktu);
                        ((TextView)findViewById(R.id.deskripsi)).setText(deskripsi);
                        ((TextView)findViewById(R.id.lokasi)).setText(lokasi);

                    }

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    }
    private static class PengumumanTask extends AsyncTask<Void, Void, Void> {

        private WeakReference<Content> activityReference;

        PengumumanTask(Content context) {
            activityReference = new WeakReference<>(context);
        }

        protected Void doInBackground(Void... urls) {
            try {
                URL url = new URL("http://pplk2a.if.itb.ac.id/ppl/getAllPengumuman.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                if (connection.getResponseCode() == 200) {
                    InputStream responseBody = connection.getInputStream();
                    JSONParser jsonParser = new JSONParser();
                    Content.pengumuman = (JSONArray) jsonParser.parse(new InputStreamReader(responseBody, "UTF-8"));

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
            Content activity = activityReference.get();
            activity.setContent();
        }
    }
    private static class KegiatanTask extends AsyncTask<Void, Void, Void> {

        private WeakReference<Content> activityReference;

        KegiatanTask(Content context) {
            activityReference = new WeakReference<>(context);
        }

        protected Void doInBackground(Void... urls) {
            try {
                URL url = new URL("http://pplk2a.if.itb.ac.id/ppl/getAllKegiatan.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                if (connection.getResponseCode() == 200) {
                    InputStream responseBody = connection.getInputStream();
                    JSONParser jsonParser = new JSONParser();
                    JSONArray jsonArray = (JSONArray) jsonParser.parse(new InputStreamReader(responseBody, "UTF-8"));
                    Content.kegiatan = jsonArray;

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
            Content activity = activityReference.get();
            activity.setKegiatan();
        }
    }
}
