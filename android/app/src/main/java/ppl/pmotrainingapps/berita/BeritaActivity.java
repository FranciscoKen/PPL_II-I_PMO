package ppl.pmotrainingapps.berita;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import ppl.pmotrainingapps.Comment.CommentFragment;
import ppl.pmotrainingapps.R;

public class BeritaActivity extends AppCompatActivity {

    public static JSONObject beritaContent = null;
    private Fragment mCommentCardFragment;
    private int berita_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_berita);
        Bundle b = getIntent().getExtras();
        berita_id = b.getInt("id");
        if (berita_id == 0) {
            berita_id = Integer.parseInt(b.getString("id"));
        }
        new BeritaTask(this, berita_id).execute();
//        WebView webview = (WebView) findViewById(R.id.berita_webview);
        String tempdata = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\" \"http://www.w3.org/TR/REC-html40/loose.dtd\">\n" +
                "<?xml encoding=\"utf-8\" ?><html><body><div style=\"text-align:center;\"></div>\n" +
                "<p>UPT PMO mengadakan kembali Pembekalan ITB Harmoni pada hari Jumat (13/10/2017) di Kampus ITB, Gedung CRCS Jalan Ganesa No. 10 Bandung, dengan pembicara Dr. H.M. Busyro Muqoddas, M.Hum. Acara ini langsung dibuka oleh Rektor ITB Prof.Dr.Ir. Kadarsah Suryadi DEA, dan dihadiri oleh Wakil Rektor Bidang Sumberdaya dan Organisasi ITB, Direktur Sarana dan Prasarana ITB, Direktur dan Wakil Direktur Eksekutif Kampus ITB Jatinangor, Kepala UPT PMO ITB, para Dekan Fakultas/Sekolah dan para tamu undangan.</p>\n" +
                "<p>Acara dimulai dengan santap siang bersama dan dilanjutkan dengan pembukaan oleh Rektor ITB serta pemaparan materi dari Ketua KPK Periode 2010-2011 (Dr. H.M. Busyro Muqoddas, M.Hum) tentang Demokrasi Politik dan Korupsi di Indonesia.</p>\n" +
                "<p>Akhir acara ditutup dengan sesi tanya jawab dan dilanjutkan dengan penyerahan cindera mata dari ITB yang diserahkan oleh Wakil Rektor Bidang Organisasi dan Sumberdaya. (dr/pmo)</p>\n" +
                "</body></html>\n";
        //        webview.loadData(tempdata, "text/html", null);
        String jsonPic = "[\"http:\\/\\/pmo.itb.ac.id\\/wp-content\\/uploads\\/pembekalan-itb-harmoni-seri-8-1.jpg\",\"http:\\/\\/pmo.itb.ac.id\\/wp-content\\/uploads\\/pembekalan-itb-harmoni-seri-8-2.jpg\"]";
    }

    public void displayBerita() {

        TextView judul = (TextView) findViewById(R.id.judul_berita);
        judul.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        judul.setTypeface(null, Typeface.BOLD);
        judul.setText((String) beritaContent.get("judul"));
        judul.setTextColor(Color.BLACK);
        judul.setPadding(30,20,30,20);
        LinearLayout l = (LinearLayout) findViewById(R.id.layout);
        JSONArray json = (JSONArray) beritaContent.get("foto");
//            judul.setText(json.get(0).toString());
        for(int i=0;i<json.size();i++)
        {
            ImageView image = new ImageView(this);
            image.setLayoutParams(new android.view.ViewGroup.LayoutParams(80,60));
//            image.setImageResource(R.drawable.ppl_quotes);
            new DownloadImageTask(image).execute(json.get(i).toString());
            image.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
            image.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
            image.setPadding(0,20,0,20);
            image.requestLayout();
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            image.setAdjustViewBounds(true);


            // Adds the view to the layout
            l.addView(image);
        }

        TextView t = new TextView(this);
        t.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        t.setText(Html.fromHtml(beritaContent.get("konten").toString()));
        t.setPadding(30,20,30,20);
        t.setTextColor(Color.BLACK);
        l.addView(t);

        initComment();
    }
    public void initComment(){
        final FragmentManager fragmentManager = getSupportFragmentManager();
        if (mCommentCardFragment == null) {
            mCommentCardFragment = CommentFragment.newInstance(CommentFragment.JENIS_BERITA, berita_id);

            fragmentManager.beginTransaction()
                    .replace(R.id.product_comment_fragment_container, mCommentCardFragment)
                    .commit();
        }
    }
    private static class BeritaTask extends AsyncTask<Void, Void, Void> {

        private WeakReference<BeritaActivity> activityReference;
        private int idBerita;

        BeritaTask(BeritaActivity context, int idBerita) {
            activityReference = new WeakReference<>(context);
            this.idBerita = idBerita;
        }

        protected Void doInBackground(Void... urls) {
            try{
                URL url = new URL("http://pplk2a.if.itb.ac.id/ppl/getBerita.php?id=" + idBerita);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                if(connection.getResponseCode() == 200) {
                    Log.d("test", "connection success");
                    InputStream responseBody = connection.getInputStream();
                    JSONParser jsonParser = new JSONParser();
                    JSONObject jsonArray = (JSONObject) jsonParser.parse(new InputStreamReader(responseBody, "UTF-8"));
                    Log.d("hasil", "hasil: "+jsonArray.toString());
                    BeritaActivity.beritaContent = jsonArray;

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
            // get a reference to the activity if it is still there
            BeritaActivity activity = activityReference.get();
            if(!activity.isDestroyed())
                activity.displayBerita();
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
