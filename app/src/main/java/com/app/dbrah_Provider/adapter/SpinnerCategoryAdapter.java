package com.app.dbrah_Provider.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.app.dbrah_Provider.R;
import com.app.dbrah_Provider.databinding.SpinnerCategoryRowBinding;
import com.app.dbrah_Provider.model.CategoryModel;

import java.util.List;

public class SpinnerCategoryAdapter extends BaseAdapter {
    private List<CategoryModel> dataList;
    private Context context;
    private String lang;


    public SpinnerCategoryAdapter(List<CategoryModel> dataList,Context context,String lang) {
        this.dataList=dataList;
        this.context = context;
        this.lang=lang;

    }

    @Override
    public int getCount() {
        if (dataList == null)
            return 0;
        else
            return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        @SuppressLint("ViewHolder") SpinnerCategoryRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.spinner_category_row, viewGroup, false);
        binding.setModel(dataList.get(i));
        binding.setLang(lang);
        return binding.getRoot();
    }

    public void updateList(List<CategoryModel> dataList){
        if (dataList!=null){
            this.dataList = dataList;
        }
        notifyDataSetChanged();
    }
}
