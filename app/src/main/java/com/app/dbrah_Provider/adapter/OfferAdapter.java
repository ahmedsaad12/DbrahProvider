package com.app.dbrah_Provider.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.app.dbrah_Provider.R;
import com.app.dbrah_Provider.databinding.OfferRowBinding;
import com.app.dbrah_Provider.model.OfferDataModel;

import java.util.List;

public class OfferAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<OfferDataModel> list;
    private Context context;
    private LayoutInflater inflater;
    private String lang;

    public OfferAdapter(List<OfferDataModel> list, Context context, String lang) {
        this.list = list;
        this.context = context;
        this.lang = lang;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OfferRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.offer_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        myHolder.binding.setLang(lang);
        if (list.get(position).getType().equals("other")) {
            myHolder.binding.tvStatus.setText(R.string.alternative_product);
            ((MyHolder) holder).binding.imDot.setColorFilter(ContextCompat.getColor(context, R.color.color2), PorterDuff.Mode.SRC_IN);
        } else if (list.get(position).getType().equals("less")) {
            if (list.get(position).getAvailable_qty().equals("0")) {
                myHolder.binding.tvStatus.setText(R.string.not_available);

                ((MyHolder) holder).binding.imDot.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent), PorterDuff.Mode.SRC_IN);


            } else {
                myHolder.binding.tvStatus.setText(R.string.less_quantitiy);

                ((MyHolder) holder).binding.imDot.setColorFilter(ContextCompat.getColor(context, R.color.color11), PorterDuff.Mode.SRC_IN);

            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public OfferRowBinding binding;

        public MyHolder(OfferRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
