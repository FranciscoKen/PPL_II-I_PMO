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
import ppl.pmotrainingapps.R;

public class Content extends AppCompatActivity {

    TextView headerText;
    TextView dateText;
    TextView contentText;
    ImageView headerImage;
    private Fragment mCommentCardFragment;
    int id_pengumuman;
    int id_kegiatan;

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
        if(b != null){
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
                new KegiatanTask(this).execute();
            }
        }
        initComment();
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
