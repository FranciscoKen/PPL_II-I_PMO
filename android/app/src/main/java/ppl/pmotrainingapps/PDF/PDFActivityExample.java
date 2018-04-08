package ppl.pmotrainingapps.PDF;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import ppl.pmotrainingapps.R;

public class PDFActivityExample extends Activity {

    public final String pdfURL = "http://maven.apache.org/maven-1.x/maven.pdf"; // URL File
    public final String title = "Maven"; //Judul yang ditampilin di viewer
    public final String pdfFileName = "materi.pdf"; //nama file di storage
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloadpdf);
    }


    //OnButton Download Click
    public void download(View v)
    {
        new DownloadFile().execute(pdfURL, pdfFileName);
    }

    //OnButton View Click
    public void view(View v)
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
            Toast.makeText(PDFActivityExample.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
        }
    }

    private class DownloadFile extends AsyncTask<String, Void, Void>{

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
            Toast.makeText(PDFActivityExample.this, "Download complete", Toast.LENGTH_SHORT).show();

        }
    }


}