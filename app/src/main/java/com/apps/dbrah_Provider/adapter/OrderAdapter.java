package com.apps.dbrah_Provider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.dbrah_Provider.R;
import com.apps.dbrah_Provider.databinding.OrderRowBinding;
import com.apps.dbrah_Provider.uis.activity_home.order_module.fragments.FragmentCurrentOrders;
import com.apps.dbrah_Provider.uis.activity_home.order_module.fragments.FragmentNewOrders;
import com.apps.dbrah_Provider.uis.activity_home.order_module.fragments.FragmentPreviousOrders;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Object> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment fragment;
    private String lang;

    public OrderAdapter(List<Object> list, Context context, Fragment fragment,String lang) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.fragment = fragment;
        this.lang=lang;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OrderRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.order_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setLang(lang);
        myHolder.binding.llDetails.setOnClickListener(view -> {
            if (fragment instanceof FragmentNewOrders) {
                FragmentNewOrders fragmentNew = (FragmentNewOrders) fragment;
                fragmentNew.navigateToDetails();
            }
        });
        myHolder.itemView.setOnClickListener(view -> {
           if (fragment instanceof FragmentCurrentOrders) {
                FragmentCurrentOrders fragmentCurrent = (FragmentCurrentOrders) fragment;
                fragmentCurrent.navigateToDetails();
            } else if (fragment instanceof FragmentPreviousOrders) {
                FragmentPreviousOrders fragmentPrevious = (FragmentPreviousOrders) fragment;
                fragmentPrevious.navigateToDetails();
            }
        });
        if (fragment instanceof FragmentNewOrders) {
            FragmentNewOrders fragmentNew = (FragmentNewOrders) fragment;
            myHolder.binding.llNewOrders.setVisibility(View.VISIBLE);

        } else if (fragment instanceof FragmentCurrentOrders) {
            FragmentCurrentOrders fragmentCurrent = (FragmentCurrentOrders) fragment;
            myHolder.binding.llWaiting.setVisibility(View.VISIBLE);
        } else if (fragment instanceof FragmentPreviousOrders) {
            FragmentPreviousOrders fragmentPrevious = (FragmentPreviousOrders) fragment;
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public OrderRowBinding binding;

        public MyHolder(OrderRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public void updateList(List<Object> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
