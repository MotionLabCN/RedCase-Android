package com.tntlinking.tntdev.ui.firm.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppAdapter;
import com.tntlinking.tntdev.http.api.SearchCompanyApi;

import androidx.annotation.NonNull;


public final class CompanySearchAdapter extends AppAdapter<SearchCompanyApi.Bean> {

    public CompanySearchAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    private final class ViewHolder extends AppAdapter<?>.ViewHolder {


        private final TextView tv_company_name;

        private ViewHolder() {
            super(R.layout.item_company_search);
            tv_company_name = findViewById(R.id.tv_company_name);

        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindView(int position) {
            SearchCompanyApi.Bean item = getItem(position);
            tv_company_name.setText(item.getCompanyName());


        }
    }
}