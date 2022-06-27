package com.tntlinking.tntdev.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.Button;

import com.blankj.utilcode.util.SPUtils;
import com.github.barteksc.pdfviewer.PDFView;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.ParseAnalysisApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.AppConfig;
import com.tntlinking.tntdev.ui.bean.DeveloperInfoBean;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static com.tntlinking.tntdev.ui.activity.EnterDeveloperActivity.INTENT_KEY_DEVELOPER_INFO;

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
        btn_parse.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(fileName)) {
                parseResume(fileName);
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
                pdfView.fromStream(input[0]).onLoad(nbPages -> hideDialog()).load();

            }
        }.execute();
    }

    private void parseResume(String fileName) {
        EasyHttp.post(this)
                .api(new ParseAnalysisApi().setFile(fileName))
                .request(new HttpCallback<HttpData<DeveloperInfoBean>>(this) {
                    @Override
                    public void onSucceed(HttpData<DeveloperInfoBean> data) {
                        SPUtils.getInstance().put(AppConfig.RESUME_ANALYSIS, true);//简历解析成功

                        Intent intent = new Intent(ResumeAnalysisActivity.this, EnterDeveloperActivity.class);
                        intent.putExtra(INTENT_KEY_DEVELOPER_INFO, data.getData());
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }
}
