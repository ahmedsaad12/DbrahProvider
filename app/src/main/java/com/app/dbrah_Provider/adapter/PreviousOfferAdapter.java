package com.app.dbrah_Provider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.app.dbrah_Provider.R;
import com.app.dbrah_Provider.databinding.PreviousOfferRowBinding;
import com.app.dbrah_Provider.model.PreviousOfferDataModel;

import java.util.List;

public class PreviousOfferAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<PreviousOfferDataModel.Datum> list;
    private Context context;
    private LayoutInflater inflater;
    private String lang;

    public PreviousOfferAdapter(Context context, String lang) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.lang = lang;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PreviousOfferRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.previous_offer_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setLang(lang);
        myHolder.binding.setModel(list.get(position));
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public PreviousOfferRowBinding binding;

        public MyHolder(PreviousOfferRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public void updateList(List<PreviousOfferDataModel.Datum> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
