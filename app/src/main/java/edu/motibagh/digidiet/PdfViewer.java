package edu.motibagh.digidiet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.shockwave.pdfium.PdfDocument;

public class PdfViewer extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener {
    PDFView pdfView;
    String s1,s2,s3,s4;

    Integer pageNumber = 0;
    private static final String TAG = MainActivity.class.getSimpleName();
    String pdfFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);
        pdfView=(PDFView)findViewById(R.id.pdfView);

        Bundle extras = getIntent().getExtras();
        s1 = extras.getString("class6");
        s2 = extras.getString("class7");
        s3 = extras.getString("class8");

        if("class6".equals(s1)){
            pdfView.fromAsset("class6.pdf")
                    .pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
                    .enableSwipe(true) // allows to block changing pages using swipe
                    .swipeHorizontal(true)
                    .enableDoubletap(true)
                    .defaultPage(0)
                    .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                    .password(null)
                    .scrollHandle(null)
                    .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                    // spacing between pages in dp. To define spacing color, set view background
                    .spacing(2)
                    .autoSpacing(true) // add dynamic spacing to fit each page on its own on the screen
                    .pageFitPolicy(FitPolicy.WIDTH) // mode to fit pages in the view
                    .fitEachPage(true) // fit each page to the view, else smaller pages are scaled relative to largest page.
                    .pageSnap(true) // snap pages to screen boundaries
                    .pageFling(true) // make a fling change only a single page like ViewPager
                    .nightMode(false) // toggle night mode
                    .load();
        }
        if("class7".equals(s2)){
            pdfView.fromAsset("class7.pdf")
                    .enableSwipe(true) // allows to block changing pages using swipe
                    .swipeHorizontal(true)
                    .enableDoubletap(true)
                    .defaultPage(0)
                    .onPageChange(this)
                    .onLoad(this)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .pageFitPolicy(FitPolicy.BOTH)
                    .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                    // spacing between pages in dp. To define spacing color, set view background
                    .spacing(10)
                    .autoSpacing(true) // add dynamic spacing to fit each page on its own on the screen
                    .pageSnap(true) // snap pages to screen boundaries
                    .pageFling(true) // make a fling change only a single page like ViewPager
                    .nightMode(false) // toggle night mode
                    .load();
        }
        if("class8".equals(s3)){
            pdfView.fromAsset("class8.pdf")
                    .enableSwipe(true) // allows to block changing pages using swipe
                    .swipeHorizontal(true)
                    .enableDoubletap(true)
                    .defaultPage(0)
                    .onPageChange(this)
                    .onLoad(this)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .pageFitPolicy(FitPolicy.BOTH)
                    .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                    // spacing between pages in dp. To define spacing color, set view background
                    .spacing(10)
                    .autoSpacing(true) // add dynamic spacing to fit each page on its own on the screen
                    .pageSnap(true) // snap pages to screen boundaries
                    .pageFling(true) // make a fling change only a single page like ViewPager
                    .nightMode(false) // toggle night mode
                    .load();
        }
    }



    public void viewPdf(String urlString) {



    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", "Page", page + 1, pageCount));
    }
    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
    }

}