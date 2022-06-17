package com.tntlinking.tntdev.ui.activity;

import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppActivity;

public final class ResumeAnalysisActivity extends AppActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.analysis_activity;


    }

    @Override
    protected void initView() {
        PDFView pdfView = findViewById(R.id.pdfView);

        pdfView.fromAsset("杨志毅个人简历 .pdf").defaultPage(1).onPageChange((page, pageCount) -> {
            // 当用户在翻页时候将回调。
            Toast.makeText(getApplicationContext(), page + " / " + pageCount, Toast.LENGTH_SHORT).show();
        }).load();


    }

    @Override
    protected void initData() {

    }
}
