package com.tntlinking.tntdev.ui.activity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnFileDownloadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppActivity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public final class PDFViewActivity extends AppActivity{
    private PDFView pdfView;

    @Override
    protected int getLayoutId() {
        return R.layout.pdf_view_activity;
    }

    @Override
    protected void initView() {
        pdfView = findViewById(R.id.pdfView);

        getPdf("https://talent-operation.stage-ttchain.tntlinking.com/api/manpower_operate/minio/files/recruit_guide.pdf");
    }

    @Override
    protected void initData() {
    }



    private void getPdf(String url) {

        final InputStream[] input = new InputStream[1];
        new AsyncTask<Void, Void, Void>() {
            @SuppressLint({"WrongThread", "StaticFieldLeak"})
            @Override
            protected Void doInBackground(Void... voids) {
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
                pdfView.fromStream(input[0])
                        .load();
            }
        }.execute();
    }

}