package com.tntlinking.tntdev.ui.activity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.other.AppConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public final class ResumeAnalysisActivity extends AppActivity {
    private PDFView pdfView;
    private String PDFUrl = AppConfig.RECRUIT_GUIDE_URL;
    @Override
    protected int getLayoutId() {
        return R.layout.analysis_activity;


    }

    @Override
    protected void initView() {
        pdfView = findViewById(R.id.pdfView);
        String pdf_url = "https://stage-ttchain.tntlinking.com/api/minio/pdf/manpower-pages/recruit_guide.pdf";
        if (!TextUtils.isEmpty(pdf_url)) {
//            getPdf(pdf_url);
        }
        pdfView.fromAsset("杨志毅个人简历 .pdf").defaultPage(1).onPageChange(new OnPageChangeListener() {

            @Override
            public void onPageChanged(int page, int pageCount) {
                // 当用户在翻页时候将回调。
                Toast.makeText(getApplicationContext(), page + " / " + pageCount, Toast.LENGTH_SHORT).show();
            }
        }).load();


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

    @Override
    protected void initData() {

    }
}
