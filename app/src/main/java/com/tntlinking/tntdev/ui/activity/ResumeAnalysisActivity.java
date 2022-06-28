package com.tntlinking.tntdev.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.blankj.utilcode.util.SPUtils;
import com.github.barteksc.pdfviewer.PDFView;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.ParseAnalysisApi;
import com.tntlinking.tntdev.http.glide.GlideApp;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.AppConfig;
import com.tntlinking.tntdev.ui.bean.DeveloperInfoBean;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import androidx.annotation.RequiresApi;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.os.FileUtils.copy;
import static com.tntlinking.tntdev.ui.activity.EnterDeveloperActivity.INTENT_KEY_DEVELOPER_INFO;

public final class ResumeAnalysisActivity extends AppActivity {
    private PDFView pdfView;
    private ImageView ivView;
    private Button btn_parse;

    @Override
    protected int getLayoutId() {
        return R.layout.analysis_activity;


    }

    @Override
    protected void initView() {
        pdfView = findViewById(R.id.pdfView);
        ivView = findViewById(R.id.ivView);
        btn_parse = findViewById(R.id.btn_parse);
    }

    @Override
    protected void initData() {
        String url = getString("url");
        String fileName = getString("fileName");
        if (!TextUtils.isEmpty(url)) {
            if (url.contains(".pdf")) {
                pdfView.setVisibility(View.VISIBLE);
                ivView.setVisibility(View.GONE);
                getPdf(url);
            } else {
                pdfView.setVisibility(View.GONE);
                ivView.setVisibility(View.VISIBLE);
//                GlideApp.with(this).load(url).into(ivView);
                setImageview(url);
            }

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

    // 根据返回的url 下载显示图片 因为每次返回的地址都是一样的，照片能容不一样而已
    public void setImageview(String url) {
        final OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                byte[] bytes = response.body().bytes();
                //把byte字节组装成图片
                final Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                //数据请求成功后，在主线程中更新
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //网络图片请求成功，更新到主线程的ImageView
                        ivView.setImageBitmap(bmp);
                    }
                });
            }
        });
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
