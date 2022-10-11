package com.app.dbrah_Provider.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import com.app.dbrah_Provider.R;
import com.app.dbrah_Provider.databinding.MainCategoryRowBinding;
import com.app.dbrah_Provider.model.CategoryModel;
import com.app.dbrah_Provider.uis.activity_control_products.ControlProductsActivity;

import java.util.List;

public class MainProductCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CategoryModel> list;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
    private String cat_id;
    private int currentPos = 0;
    private int oldPos = currentPos;
    private MyHolder oldHolder;

    public MainProductCategoryAdapter(Context context, String lang) {
        this.context = context;
        this.lang = lang;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MainCategoryRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.main_category_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        myHolder.binding.setLang(lang);

        if (oldHolder==null){
            oldHolder=myHolder;
            CategoryModel model=list.get(position);
            model.setSelected(true);
            list.set(position,model);
            myHolder.binding.setModel(model);
            oldPos=position;
            if (context instanceof ControlProductsActivity) {
                ControlProductsActivity activity = (ControlProductsActivity) context;
                activity.setItemCategory(list.get(myHolder.getAdapterPosition()));
            }
        }

        myHolder.itemView.setOnClickListener(view -> {
            if (oldHolder!=null){
                CategoryModel oldCategoryModel=list.get(oldPos);
                oldCategoryModel.setSelected(false);
                list.set(oldPos,oldCategoryModel);
                MyHolder oHolder=(MyHolder) oldHolder;
                oHolder.binding.setModel(oldCategoryModel);
            }

            currentPos=myHolder.getAdapterPosition();
            CategoryModel categoryModel=list.get(currentPos);
            categoryModel.setSelected(true);
            list.set(currentPos,categoryModel);
            myHolder.binding.setModel(categoryModel);

            oldHolder=myHolder;
            oldPos=currentPos;

            if (context instanceof ControlProductsActivity) {
                    ControlProductsActivity activity = (ControlProductsActivity) context;
                    activity.setItemCategory(list.get(myHolder.getAdapterPosition()));
                }
        });

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }


    public static class MyHolder extends RecyclerView.ViewHolder {
        public MainCategoryRowBinding binding;

        public MyHolder(MainCategoryRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public void updateList(List<CategoryModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setSelectedPos(int pos){
        currentPos = pos;
    }


}
