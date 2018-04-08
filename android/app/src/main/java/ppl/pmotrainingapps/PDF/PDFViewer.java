package ppl.pmotrainingapps.PDF;

/**
 * Created by David on 4/5/2018.
 */


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;
import java.util.List;

import ppl.pmotrainingapps.R;

public class PDFViewer extends Activity implements OnPageChangeListener,OnLoadCompleteListener{
    private static final String TAG = PDFViewer.class.getSimpleName();
    public static final String SAMPLE_FILE = "android_tutorial.pdf";
    PDFView pdfView;
    TextView title;
    Integer pageNumber = 0;
    String pdfFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);


        pdfView= (PDFView)findViewById(R.id.pdfView);
        title= (TextView) findViewById(R.id.tv_header);
        displayFromAsset();
    }

    private void displayFromAsset() {
        Log.d("sss",getIntent().getData().toString());
       File pdf = new File(getIntent().getData().toString());
        Bundle b = getIntent().getExtras();
        if(b != null) {
            title.setText(b.getString("title"));
        }
       if(pdf != null){

           pdfView.fromUri(getIntent().getData())
                   .defaultPage(pageNumber)
                   .enableSwipe(true)

                   .swipeHorizontal(false)
                   .onPageChange(this)
                   .enableAnnotationRendering(true)
                   .onLoad(this)
                   .scrollHandle(new DefaultScrollHandle(this))
                   .load();
       }


    }


    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }


    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        printBookmarksTree(pdfView.getTableOfContents(), "-");

    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

}