package com.apps.dbrah_Provider.uis.activity_preview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.apps.dbrah_Provider.R;
import com.apps.dbrah_Provider.adapter.OfferAdapter;
import com.apps.dbrah_Provider.databinding.ActivityPreviewBinding;
import com.apps.dbrah_Provider.model.AddOFFerDataModel;
import com.apps.dbrah_Provider.model.OfferDataModel;
import com.apps.dbrah_Provider.model.OrderModel;
import com.apps.dbrah_Provider.mvvm.ActivityPreviewMvvm;
import com.apps.dbrah_Provider.uis.activity_base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class PreviewActivity extends BaseActivity {
    private ActivityPreviewBinding binding;
    private OfferAdapter offerAdapter;
    private List<OfferDataModel> offerList;
    private AddOFFerDataModel addOFFerDataModel;
    private ActivityPreviewMvvm activityPreviewMvvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_preview);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();

        addOFFerDataModel = (AddOFFerDataModel) intent.getSerializableExtra("data");
    }

    private void initView() {
        activityPreviewMvvm= ViewModelProviders.of(this).get(ActivityPreviewMvvm.class);
        activityPreviewMvvm.save.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
        setUpToolbar(binding.toolbar, getString(R.string.preview), R.color.white, R.color.black);
        offerList = new ArrayList<>();
        binding.setModel(addOFFerDataModel);
        offerList.addAll(addOFFerDataModel.getOffer_details());
        offerAdapter = new OfferAdapter(offerList, this, getLang());
        binding.recViewOffer.setLayoutManager(new LinearLayoutManager(this));
        binding.recViewOffer.setAdapter(offerAdapter);
        binding.btnSendOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityPreviewMvvm.addOffer(PreviewActivity.this,addOFFerDataModel);
            }
        });
    }
}