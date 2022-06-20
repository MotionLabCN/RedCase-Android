package com.tntlinking.tntdev.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.hjq.base.BaseDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.EasyLog;
import com.hjq.http.listener.HttpCallback;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.ParseAnalysisApi;
import com.tntlinking.tntdev.http.api.ParseResumeApi;
import com.tntlinking.tntdev.http.model.HttpData;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public final class ResumeAnalysisActivity extends AppActivity {
    private PDFView pdfView;
    private Button btn_parse;

    @Override
    protected int getLayoutId() {
        return R.layout.analysis_activity;


    }

    @Override
    protected void initView() {
        pdfView = findViewById(R.id.pdfView);
        btn_parse = findViewById(R.id.btn_parse);

    }

    @Override
    protected void initData() {
        String pdf_url = getString("url");
        String fileName = getString("fileName");

        if (!TextUtils.isEmpty(pdf_url)) {
            getPdf(pdf_url);
        }
        btn_parse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(fileName)) {
                    parseResume(fileName);
                }
            }
        });
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

    private void parseResume(String fileName) {
        EasyHttp.post(this)
                .api(new ParseAnalysisApi().setFile(fileName))
                .request(new HttpCallback<HttpData<String>>(this) {
                    @Override
                    public void onSucceed(HttpData<String> data) {


                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }
}
