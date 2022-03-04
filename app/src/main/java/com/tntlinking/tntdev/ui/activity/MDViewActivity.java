package com.tntlinking.tntdev.ui.activity;
import com.hjq.bar.TitleBar;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppActivity;
import br.tiagohm.markdownview.MarkdownView;
import br.tiagohm.markdownview.css.styles.Github;

public final class MDViewActivity extends AppActivity {
    private String PDFUrl = "https://stage-ttchain.tntlinking.com/api/minio/manpower-pages/service_guide.md";

    private TitleBar title_bar;
    private MarkdownView mark_view;

    @Override
    protected int getLayoutId() {
        return R.layout.md_view_activity;
    }

    @Override
    protected void initView() {

        String title = getString("title");
        String md_url = getString("md_url");
        title_bar = findViewById(R.id.title_bar);
        mark_view = findViewById(R.id.mark_view);
        mark_view.addStyleSheet(new Github());
//        mark_view.loadMarkdown("**MarkdownView**");
//        mark_view.loadMarkdownFromAsset("markdown1.md");
//        mark_view.loadMarkdownFromFile(new File());
        title_bar.setTitle(title);
        mark_view.loadMarkdownFromUrl(md_url);
    }

    @Override
    protected void initData() {

    }

}