package ppl.pmotrainingapps.content;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import ppl.pmotrainingapps.R;

public class Berita extends AppCompatActivity {

    public static JSONArray beritaContent = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita);

//        WebView webview = (WebView) findViewById(R.id.berita_webview);
        String tempdata =
                "<p>UPT PMO mengadakan kembali Pembekalan ITB Harmoni pada hari Jumat (13/10/2017) di Kampus ITB, Gedung CRCS Jalan Ganesa No. 10 Bandung, dengan pembicara Dr. H.M. Busyro Muqoddas, M.Hum. Acara ini langsung dibuka oleh Rektor ITB Prof.Dr.Ir. Kadarsah Suryadi DEA, dan dihadiri oleh Wakil Rektor Bidang Sumberdaya dan Organisasi ITB, Direktur Sarana dan Prasarana ITB, Direktur dan Wakil Direktur Eksekutif Kampus ITB Jatinangor, Kepala UPT PMO ITB, para Dekan Fakultas/Sekolah dan para tamu undangan.</p>\n" +
                "<p>Acara dimulai dengan santap siang bersama dan dilanjutkan dengan pembukaan oleh Rektor ITB serta pemaparan materi dari Ketua KPK Periode 2010-2011 (Dr. H.M. Busyro Muqoddas, M.Hum) tentang Demokrasi Politik dan Korupsi di Indonesia.</p>\n" +
                "<p>Akhir acara ditutup dengan sesi tanya jawab dan dilanjutkan dengan penyerahan cindera mata dari ITB yang diserahkan oleh Wakil Rektor Bidang Organisasi dan Sumberdaya. (dr/pmo)</p>\n";
//        webview.loadData(tempdata, "text/html", null);
//        TextView t = (TextView) findViewById(R.id.txxt);
//        t.setTextSize(20);
        LinearLayout l = (LinearLayout) findViewById(R.id.layout);
        for(int i=0;i<2;i++)
        {
            ImageView image = new ImageView(this);
            image.setLayoutParams(new android.view.ViewGroup.LayoutParams(80,60));
//            image.setImageResource(R.drawable.ppl_quotes);
            new DownloadImageTask(image).execute("http://pmo.itb.ac.id/wp-content/uploads/pembekalan-itb-harmoni-seri-8-1.jpg");
            image.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
            image.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
            image.setPadding(20,20,20,20);
            image.requestLayout();
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            image.setAdjustViewBounds(true);


            // Adds the view to the layout
            l.addView(image);
        }
        TextView t = new TextView(this);
        t.setTextSize(20);
        t.setText(Html.fromHtml(tempdata));
        l.addView(t);
        // buka koneksi
        final Berita berita = this;
//        new BeritaTask(berita,1).execute();
    }

    private void displayBerita() {

    }

    private static class BeritaTask extends AsyncTask<Void, Void, Void> {

        private WeakReference<Berita> activityReference;
        private int idBerita;

        BeritaTask(Berita context, int idBerita) {
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
                    JSONArray jsonArray = (JSONArray) jsonParser.parse(new InputStreamReader(responseBody, "UTF-8"));
                    Log.d("hasil", "hasil: "+jsonArray.toString());
                    Berita.beritaContent = jsonArray;

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
            Berita activity = activityReference.get();
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
