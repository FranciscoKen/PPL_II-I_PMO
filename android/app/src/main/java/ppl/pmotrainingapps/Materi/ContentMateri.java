package ppl.pmotrainingapps.Materi;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;

import ppl.pmotrainingapps.PDF.FileDownloader;
import ppl.pmotrainingapps.PDF.PDFViewer;
import ppl.pmotrainingapps.R;
import ppl.pmotrainingapps.Video.VideoActivity;

public class ContentMateri extends AppCompatActivity {
    private TextView judulMateri;
    private TextView kontenMateri;
    private ProgressDialog mDialog;
    private ImageButton mPlayButton;
    private VideoView videoView;
    private String pdfURL;
    private String title;
    private String pdfFileName = "materi.pdf";
    private int position = 0;
    private int id_video;
    public static final String EXTRA_MESSAGE = "Send Video URL";
    String videoURL;
    Bitmap thumbnail;


    static final Integer WRITE_EXST = 0x3;
    static final Integer READ_EXST = 0x4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_materi);

        judulMateri = findViewById(R.id.judulMateri);
        kontenMateri = findViewById(R.id.kontenMateri);


        Bundle b = getIntent().getExtras();

        int value = -1;

        if(b != null){
            judulMateri.setText(b.getString("judul_materi"));
            String konten = b.getString("konten_materi");
            if(konten==null){
                findViewById(R.id.kontenMateri).setVisibility(View.GONE);
            }else{
                kontenMateri.setText(b.getString("konten_materi"));
            }

            videoURL = b.getString("video_materi");
            if(videoURL == null){
                findViewById(R.id.playButtonMateri).setVisibility(View.GONE);
            } else {
                new DownloadThumbnail(this).execute();
            }


            pdfURL = b.getString("pdf_materi");

            if(pdfURL==null){
                findViewById(R.id.buttonDownload).setVisibility(View.GONE);
            }else{
                title = b.getString("judul_materi");
            }
        }

    }
    public void playVideo(View v){
        Intent intent = new Intent(this, VideoActivity.class);
        intent.putExtra(EXTRA_MESSAGE, videoURL);
        startActivity(intent);
    }
    public void setThumbnail(){
        if(this != null){
            ImageView videoThumb = (ImageView) findViewById(R.id.playsButtonMateri);
            Glide.with(this).load(thumbnail).into(videoThumb);
        }

    }
    //OnButton Download Click
    public void download(View v)
    {
        Toast.makeText(ContentMateri.this, "Download started", Toast.LENGTH_SHORT).show();
        askForPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,WRITE_EXST);
        askForPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE,READ_EXST);
        if (ContextCompat.checkSelfPermission(ContentMateri.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(ContentMateri.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            new DownloadFile(this).execute(pdfURL, pdfFileName);
        }
    }

    //OnButton View Click
    public void view()
    {
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/pmotrainingapps/" + pdfFileName);  // -> filename = maven.pdf
        Uri path = Uri.fromFile(pdfFile);
        //Penggunaan intent
        Intent pdfIntent = new Intent(getApplicationContext(), PDFViewer.class);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pdfIntent.putExtra("title", title);
        pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try{
            startActivity(pdfIntent);
        }catch(ActivityNotFoundException e){
            Toast.makeText(ContentMateri.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
        }
    }

    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(ContentMateri.this, permission) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(ContentMateri.this, permission)) {
                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(ContentMateri.this, new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions(ContentMateri.this, new String[]{permission}, requestCode);
            }
        }
    }

    public static Bitmap retriveVideoFrameFromVideo(String videoPath)
    {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try
        {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoPath);
            //   mediaMetadataRetriever.setDataSource(videoPath);
            bitmap = mediaMetadataRetriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (mediaMetadataRetriever != null)
            {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }

    private class DownloadFile extends AsyncTask<String, Void, Void> {
        private WeakReference<ContentMateri> activityReference;

        DownloadFile(ContentMateri context) {
            activityReference = new WeakReference<ContentMateri>(context);
        }
        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "pmotrainingapps");
            folder.mkdir();

            File pdfFile = new File(folder, fileName);

            try{
                pdfFile.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            FileDownloader.downloadFile(fileUrl, pdfFile);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(ContentMateri.this, "Download complete", Toast.LENGTH_SHORT).show();
            // get a reference to the activity if it is still there
            ContentMateri activity = activityReference.get();
            activity.view();
        }


    }
    private class DownloadThumbnail extends AsyncTask<Void, Void, Void> {

        private WeakReference<ContentMateri> activityReference;

        DownloadThumbnail(ContentMateri context) {
            activityReference = new WeakReference<ContentMateri>(context);
        }
        @Override
        protected Void doInBackground(Void... strings) {
            thumbnail = retriveVideoFrameFromVideo(videoURL);
            return null;
        }
        protected void onPostExecute(Void A2) {
            super.onPostExecute(A2);
            // get a reference to the activity if it is still there
            ContentMateri activity = activityReference.get();
            if(!activity.isDestroyed())
            activity.setThumbnail();
        }
    }
}
