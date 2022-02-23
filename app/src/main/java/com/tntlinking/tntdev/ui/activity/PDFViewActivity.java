package com.tntlinking.tntdev.ui.activity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnFileDownloadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.other.AppConfig;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * 合作模式
 */
public final class PDFViewActivity extends AppActivity {
    private PDFView pdfView;
    //    private String PDFUrl = "https://stage-ttchain.tntlinking.com/api/minio/pdf/manpower-pages/recruit_guide.pdf";
    private String PDFUrl = AppConfig.RECRUIT_GUIDE_URL;


    @Override
    protected int getLayoutId() {
        return R.layout.pdf_view_activity;
    }

    @Override
    protected void initView() {
        pdfView = findViewById(R.id.pdfView);

        getPdf(PDFUrl);

    }

    @Override
    protected void initData() {
    }


    @SuppressLint({"WrongThread", "StaticFieldLeak"})
    private void getPdf(String url) {
        final InputStream[] input = new InputStream[1];
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                showDialog();
                try {
                    input[0] = new URL(url).openStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                pdfView.fromStream(input[0]).onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        hideDialog();
                    }
                }).load();

            }
        }.execute();
    }

}