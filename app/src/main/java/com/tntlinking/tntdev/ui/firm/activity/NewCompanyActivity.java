package com.tntlinking.tntdev.ui.firm.activity;


import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.hjq.bar.TitleBar;
import com.hjq.base.BaseDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.EasyLog;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.SettingBar;
import com.hjq.widget.view.ClearEditText;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.CreateNewCompanyApi;
import com.tntlinking.tntdev.http.api.CreateNewCompanyChangeApi;
import com.tntlinking.tntdev.http.api.FirmCertificationApi;
import com.tntlinking.tntdev.http.api.GetDictionaryApi;
import com.tntlinking.tntdev.http.api.UpdateCompanyImageApi;
import com.tntlinking.tntdev.http.glide.GlideApp;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.BitmapUtil;
import com.tntlinking.tntdev.other.FileSizeUtil;
import com.tntlinking.tntdev.ui.activity.ImageSelectActivity;
import com.tntlinking.tntdev.ui.bean.DeveloperInfoBean;
import com.tntlinking.tntdev.ui.dialog.DictionarySelectDialog;
import com.tntlinking.tntdev.ui.dialog.IndustrySelectDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;

/**
 * 新建公司页面
 */
public final class NewCompanyActivity extends AppActivity {
    private TitleBar title_bar;
    private LinearLayout ll_add_photo;
    private FrameLayout fl_add_photo;
    private ImageView iv_photo_avatar;
    private TextView tv_social_credit_code;
    private TextView tv_full_company_name;
    private ClearEditText et_as_company_name;
    private SettingBar sb_company_industry;
    private SettingBar sb_company_person_num;
    private ClearEditText et_email;
    private AppCompatButton btn_commit;

    private String mBusinessLicense;
    private String mCompanyName;
    private String mTaxInvoice;
    private String mShortName;
    private int mIndustryId;
    private String mIndustryName;
    private int mPersonSizeId;
    private String mPersonSizeName;
    private String mEmailSuffix;

    private List<GetDictionaryApi.DictionaryBean> mDictionaryList;
    private boolean isChange = false;

    @Override
    protected int getLayoutId() {
        return R.layout.new_company_activity;
    }

    @Override
    protected void initView() {
        title_bar = findViewById(R.id.title_bar);
        ll_add_photo = findViewById(R.id.ll_add_photo);
        fl_add_photo = findViewById(R.id.fl_add_photo);
        iv_photo_avatar = findViewById(R.id.iv_photo_avatar);
        tv_social_credit_code = findViewById(R.id.tv_social_credit_code);
        tv_full_company_name = findViewById(R.id.tv_full_company_name);
        et_as_company_name = findViewById(R.id.et_as_company_name);
        sb_company_industry = findViewById(R.id.sb_company_industry);
        sb_company_person_num = findViewById(R.id.sb_company_person_num);
        et_email = findViewById(R.id.et_email);
        btn_commit = findViewById(R.id.btn_commit);


        setOnClickListener(ll_add_photo, fl_add_photo, btn_commit, sb_company_industry, sb_company_person_num);


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initData() {
        getDictionaryList("1");//1->行业 所属行业
        mDictionaryList = getDictionaryList("2");// 2->人员规模

        isChange = getBoolean("isChange");
        if (isChange) {// 从我的公司页面跳转过来的，
            title_bar.setTitle("更换公司");
            btn_commit.setText("更换");
        } else {// 新建公司过来的，
            title_bar.setTitle("新建公司");
            btn_commit.setText("新建");
        }
    }


    /**
     * (1->行业 || 2->人员规模 || 3->常用协作工具 || 4->经验要求 || 5->学历要求 6->职业方向
     * || 7->培养方式 || 8->远程工作原因 || 9->职业状态 || 10->工作方式 || 11 ->面试方式)
     */

    public List<GetDictionaryApi.DictionaryBean> getDictionaryList(String parentId) {
        List<GetDictionaryApi.DictionaryBean> mList = new ArrayList();
        EasyHttp.get(this)
                .api(new GetDictionaryApi().setParentId(parentId))
                .request(new HttpCallback<HttpData<List<GetDictionaryApi.DictionaryBean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<GetDictionaryApi.DictionaryBean>> data) {
                        if (!data.getData().isEmpty()) {
                            mList.addAll(data.getData());
                            if (parentId.equals("1")) {
                                SPUtils.getInstance().put("industry", GsonUtils.toJson(data.getData()));
                            }

                        }
                    }
                });

        return mList;
    }

    @SuppressLint("NonConstantResourceId")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @SingleClick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_add_photo:
            case R.id.fl_add_photo:
                ImageSelectActivity.start(this, data -> {
                    // 裁剪头像
                    cropImageFile(new File(data.get(0)));
                });
                break;

            case R.id.sb_company_industry:
                new IndustrySelectDialog.Builder(this).setTitle("选择所在行业")
                        .setListener(new IndustrySelectDialog.OnListener() {
                            @Override
                            public void onSelected(BaseDialog dialog, GetDictionaryApi.DictionaryBean bean, GetDictionaryApi.ChildrenBean childrenBean) {

                                sb_company_industry.setLeftText(bean.getName() + "-" + childrenBean.getName());
                                mIndustryId = childrenBean.getId();
                                mIndustryName = bean.getName() + "-" + childrenBean.getName();

                            }
                        }).show();

                break;
            case R.id.sb_company_person_num:
                new DictionarySelectDialog.Builder(this)
                        .setTitle("选择人员规模")
                        .setList(mDictionaryList).setListener(new DictionarySelectDialog.OnListener() {
                            @Override
                            public void onSelected(BaseDialog dialog, int type) {
                                sb_company_person_num.setLeftText(mDictionaryList.get(type).getName());

                                mPersonSizeId = mDictionaryList.get(type).getId();
                                mPersonSizeName = mDictionaryList.get(type).getName();
                            }
                        }).show();
                break;
            case R.id.btn_commit:
                mShortName = et_as_company_name.getText().toString();
                mEmailSuffix = et_email.getText().toString();
//                startActivity(MyCompanyActivity.class);

                if (isChange) {
                    changeNewCompany();
                } else {
                    createNewCompany();
                }
                break;

        }

    }

    // 新建企业
    public void createNewCompany() {
        EasyHttp.post(this)
                .api(new CreateNewCompanyApi()
                        .setBusinessLicense(mBusinessLicense)
                        .setCompanyName(mCompanyName)
                        .setEmailSuffix(mEmailSuffix)
                        .setIndustryId(mIndustryId)
                        .setPersonSizeId(mPersonSizeId)
                        .setShortName(mShortName)
                        .setTaxInvoice(mTaxInvoice))
                .request(new HttpCallback<HttpData<DeveloperInfoBean.DeveloperWork>>(this) {

                    @Override
                    public void onSucceed(HttpData<DeveloperInfoBean.DeveloperWork> data) {

                        toast("新建成功");
                        finish();
                    }
                });
    }

    // 更换企业
    public void changeNewCompany() {
        EasyHttp.post(this)
                .api(new CreateNewCompanyChangeApi()
                        .setBusinessLicense(mBusinessLicense)
                        .setCompanyName(mCompanyName)
                        .setEmailSuffix(mEmailSuffix)
                        .setIndustryId(mIndustryId)
                        .setPersonSizeId(mPersonSizeId)
                        .setShortName(mShortName)
                        .setTaxInvoice(mTaxInvoice))
                .request(new HttpCallback<HttpData<DeveloperInfoBean.DeveloperWork>>(this) {

                    @Override
                    public void onSucceed(HttpData<DeveloperInfoBean.DeveloperWork> data) {

                        toast("更换成功");
                        finish();
                    }
                });
    }

    /**
     * 裁剪图片
     */
    private void cropImageFile(File sourceFile) {
        double fileSize = FileSizeUtil.getFileOrFilesSize(sourceFile, 3);
        EasyLog.print("===FileUtils=111===" + FileUtils.getSize(sourceFile));
        if (fileSize > 2) {//图片大于2M 压缩再上传，小于2M 直接上传
            File file = new File(BitmapUtil.compressImage(sourceFile.getAbsolutePath(), 90));
            EasyLog.print("===getSize=222===" + FileUtils.getSize(file));
            updateCropImage(file, false);
            toast("图片压缩大小==>>>" + FileUtils.getSize(file));
        } else {
            updateCropImage(sourceFile, false);
        }

    }


    /**
     * 上传裁剪后的图片
     */
    private void updateCropImage(File file, boolean deleteFile) {
        ll_add_photo.setVisibility(View.GONE);
        fl_add_photo.setVisibility(View.VISIBLE);
        EasyHttp.post(this)
                .api(new UpdateCompanyImageApi().setFiles(file))
                .request(new HttpCallback<HttpData<UpdateCompanyImageApi.Bean>>(this) {

                    @Override
                    public void onSucceed(HttpData<UpdateCompanyImageApi.Bean> data) {
                        UpdateCompanyImageApi.Bean bean = data.getData();
                        mCompanyName = bean.getCompanyName();
                        mTaxInvoice = bean.getTaxInvoice();
                        mBusinessLicense = bean.getBusinessLicense();
                        GlideApp.with(getActivity())
                                .load(file)
                                .transform(new MultiTransformation<>(new CenterCrop(), new RoundedCorners((int) getResources().getDimension(R.dimen.dp_8))))
                                .into(iv_photo_avatar);
                        tv_social_credit_code.setText(mTaxInvoice);
                        tv_full_company_name.setText(mCompanyName);
                        if (deleteFile) {
                            file.delete();
                        }
                    }
                });
    }
}