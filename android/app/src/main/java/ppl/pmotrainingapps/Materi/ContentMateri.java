package ppl.pmotrainingapps.Materi;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

import ppl.pmotrainingapps.PDF.FileDownloader;
import ppl.pmotrainingapps.PDF.PDFActivityExample;
import ppl.pmotrainingapps.PDF.PDFViewer;
import ppl.pmotrainingapps.Pengumuman.Content;
import ppl.pmotrainingapps.R;
import ppl.pmotrainingapps.Video.FullScreenMediaController;
import ppl.pmotrainingapps.Video.VideoActivityExample;

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
                findViewById(R.id.VideoViewMateri).setVisibility(View.GONE);
                findViewById(R.id.playButtonMateri).setVisibility(View.GONE);
            }

            pdfURL = b.getString("pdf_materi");

            if(pdfURL==null){
                findViewById(R.id.buttonDownload).setVisibility(View.GONE);
            }else{
                title = b.getString("judul_materi");
            }
        }

    }
    public void playVideo(){
        Intent intent = new Intent(this, VideoActivityExample.class);
        intent.putExtra(EXTRA_MESSAGE, videoURL);
        startActivity(intent);
    }

    //OnButton Download Click
    public void download(View v)
    {
        new DownloadFile(this).execute(pdfURL, pdfFileName);
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
}
