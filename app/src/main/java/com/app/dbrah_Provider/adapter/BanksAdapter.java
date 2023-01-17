package com.app.dbrah_Provider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.dbrah_Provider.R;
import com.app.dbrah_Provider.databinding.BankRowBinding;
import com.app.dbrah_Provider.databinding.NotificationRowBinding;
import com.app.dbrah_Provider.model.BankModel;
import com.app.dbrah_Provider.model.NotificationModel;
import com.app.dbrah_Provider.uis.activity_bank_account.BanksActivity;
import com.app.dbrah_Provider.uis.activity_edit_account.EditAccountActivity;
import com.app.dbrah_Provider.uis.activity_sign_up.SignUpActivity;

import java.util.List;

public class BanksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<BankModel> list;
    private Context context;
    private LayoutInflater inflater;
    public BanksAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        BankRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.bank_row, parent, false);
        return new MyHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
myHolder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(context instanceof BanksActivity){
            BanksActivity activity=(BanksActivity) context;
            activity.update(list.get(holder.getAdapterPosition()));
        }
    }
});
        myHolder.binding.imageClose.setOnClickListener(view -> {
            if (context instanceof BanksActivity){
                BanksActivity activity=(BanksActivity) context;
                activity.deleteBank(list.get(myHolder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private BankRowBinding binding;

        public MyHolder(BankRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

    }

    public void updateList(List<BankModel> list){
        if (list!=null){
            this.list = list;
        }
        notifyDataSetChanged();
    }

}
