package com.app.dbrah_Provider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.app.dbrah_Provider.R;
import com.app.dbrah_Provider.databinding.CountriesRowBinding;
import com.app.dbrah_Provider.model.CountryModel;
import com.app.dbrah_Provider.uis.activity_edit_account.EditAccountActivity;
import com.app.dbrah_Provider.uis.activity_forget_password.ForgetPasswordActivity;
import com.app.dbrah_Provider.uis.activity_login.LoginActivity;
import com.app.dbrah_Provider.uis.activity_sign_up.SignUpActivity;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CountryModel> list;
    private Context context;
    private LayoutInflater inflater;

    public CountryAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CountriesRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.countries_row, parent, false);
        return new MyHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        myHolder.itemView.setOnClickListener(view -> {
            if (context instanceof LoginActivity) {
                LoginActivity activity = (LoginActivity) context;
                activity.setItemData(list.get(holder.getLayoutPosition()));
            }else if (context instanceof SignUpActivity){
                SignUpActivity activity=(SignUpActivity) context;
                activity.setItemData(list.get(holder.getLayoutPosition()));
            }else if (context instanceof EditAccountActivity){
                EditAccountActivity activity=(EditAccountActivity) context;
                activity.setItemData(list.get(holder.getLayoutPosition()));
            }
            else if (context instanceof ForgetPasswordActivity){
                ForgetPasswordActivity activity=(ForgetPasswordActivity) context;
                activity.setItemData(list.get(holder.getLayoutPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private CountriesRowBinding binding;

        public MyHolder(CountriesRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

    }

    public void updateList(List<CountryModel> list) {
        if (list != null) {
            this.list = list;
        }
        notifyDataSetChanged();
    }

}
