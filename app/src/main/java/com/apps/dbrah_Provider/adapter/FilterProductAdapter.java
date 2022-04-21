package com.apps.dbrah_Provider.adapter;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import com.apps.dbrah_Provider.R;
import com.apps.dbrah_Provider.databinding.FilterProductRowBinding;
import com.apps.dbrah_Provider.model.ProductModel;
import com.apps.dbrah_Provider.uis.activity_control_products.ControlProductsActivity;

import java.util.List;

public class FilterProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ProductModel> list;
    private Context context;
    private LayoutInflater inflater;



    public FilterProductAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FilterProductRowBinding rowBinding = DataBindingUtil.inflate(inflater, R.layout.filter_product_row, parent, false);
        return new MyHolder(rowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        ProductModel productModel = list.get(position);

        myHolder.binding.setModel(productModel);

        myHolder.binding.boxFilter.setOnClickListener(view -> {
            ProductModel productModel1 = list.get(holder.getLayoutPosition());
            if (productModel1.getHave_or_not().equals("have")){
                productModel1.setHave_or_not("not");
            }else if (productModel1.getHave_or_not().equals("not")){
                productModel1.setHave_or_not("have");
            }


            if (context instanceof ControlProductsActivity){
                ControlProductsActivity activity=(ControlProductsActivity) context;
                activity.addProductId(list.get(holder.getLayoutPosition()));
            }
        });
    }


    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        public FilterProductRowBinding binding;

        public MyHolder(FilterProductRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public void updateList(List<ProductModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
