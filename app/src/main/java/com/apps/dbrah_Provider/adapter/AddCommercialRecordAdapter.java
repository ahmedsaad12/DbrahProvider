package com.apps.dbrah_Provider.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.dbrah_Provider.R;
import com.apps.dbrah_Provider.databinding.AddCommercialRowBinding;
import com.apps.dbrah_Provider.uis.activity_sign_up.SignUpActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AddCommercialRecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<String> list;
    private Context context;
    private LayoutInflater inflater;

    public AddCommercialRecordAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);

    }


    public void updateList(List<String> images) {
        this.list=images;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AddCommercialRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.add_commercial_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        Picasso.get().load(Uri.parse(list.get(position))).fit().into(myHolder.binding.image);
        myHolder.binding.cardViewDelete.setOnClickListener(view -> {
            if (context instanceof SignUpActivity) {
                SignUpActivity activity = (SignUpActivity) context;
                activity.deleteImage(myHolder.getAdapterPosition());
            }

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private AddCommercialRowBinding binding;

        public MyHolder(@NonNull AddCommercialRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
