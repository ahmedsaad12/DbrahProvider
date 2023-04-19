package com.app.dbrah_Provider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.dbrah_Provider.R;
import com.app.dbrah_Provider.databinding.SubCategoryRowBinding;
import com.app.dbrah_Provider.model.CategoryModel;
import com.app.dbrah_Provider.uis.activity_control_products.ControlProductsActivity;

import java.util.List;

public class SubSubProductCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CategoryModel> list;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
    private MyHolder oldHolder;
    private int selectedPos = 0;

    public SubSubProductCategoryAdapter(Context context, String lang) {
        this.context = context;
        this.lang = lang;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SubCategoryRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.sub_category_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        if (oldHolder==null){
            if (selectedPos == position){
                oldHolder = myHolder;
                CategoryModel model=list.get(position);
                model.setSelected(true);
                list.set(position,model);
                myHolder.binding.setModel(model);
                selectedPos=position;
                if (context instanceof ControlProductsActivity) {
                    ControlProductsActivity activity = (ControlProductsActivity) context;
                    activity.showProducts(list.get(myHolder.getAdapterPosition()));
                }
            }
        }


        myHolder.binding.setLang(lang);
        myHolder.binding.setModel(list.get(position));
        myHolder.itemView.setOnClickListener(v -> {
            CategoryModel categoryModel = list.get(myHolder.getAdapterPosition());
            if (!categoryModel.isSelected()) {
                if (oldHolder != null) {
                    CategoryModel oldModel = list.get(oldHolder.getAdapterPosition());
                    oldModel.setSelected(false);
                    list.set(oldHolder.getAdapterPosition(), oldModel);
                    oldHolder.binding.setModel(oldModel);
                }
                categoryModel.setSelected(true);
                selectedPos = myHolder.getAdapterPosition();
                myHolder.binding.setModel(categoryModel);
                list.set(myHolder.getAdapterPosition(), categoryModel);
                oldHolder = myHolder;


                if (context instanceof ControlProductsActivity){
                    ControlProductsActivity activity = (ControlProductsActivity) context;
                    activity.showProducts(categoryModel);
                }
            }


        });

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }


    public static class MyHolder extends RecyclerView.ViewHolder {
        public SubCategoryRowBinding binding;

        public MyHolder(SubCategoryRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public void updateList(List<CategoryModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setSelectedPos(int pos){
        selectedPos = pos;
    }}
