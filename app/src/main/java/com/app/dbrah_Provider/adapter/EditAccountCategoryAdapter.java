package com.app.dbrah_Provider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.dbrah_Provider.R;
import com.app.dbrah_Provider.databinding.CategoryRowBinding;
import com.app.dbrah_Provider.databinding.EditAccountCategoryRowBinding;
import com.app.dbrah_Provider.model.CategoryModel;
import com.app.dbrah_Provider.model.UserModel;
import com.app.dbrah_Provider.uis.activity_edit_account.EditAccountActivity;
import com.app.dbrah_Provider.uis.activity_sign_up.SignUpActivity;

import java.util.List;

public class EditAccountCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<UserModel.Data.Categories> list;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
    private AppCompatActivity appCompatActivity;

    public EditAccountCategoryAdapter(List<UserModel.Data.Categories> list, Context context, String lang) {
        this.list = list;
        this.context = context;
        this.lang=lang;
        inflater = LayoutInflater.from(context);
        appCompatActivity = (AppCompatActivity) context;
    }




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EditAccountCategoryRowBinding binding= DataBindingUtil.inflate(inflater, R.layout.edit_account_category_row,parent,false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder=(MyHolder) holder;
        myHolder.binding.setModel(list.get(position).getCategory());
        myHolder.binding.setLang(lang);
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public EditAccountCategoryRowBinding binding;

        public MyHolder(EditAccountCategoryRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
    public void updateList(List<UserModel.Data.Categories> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
