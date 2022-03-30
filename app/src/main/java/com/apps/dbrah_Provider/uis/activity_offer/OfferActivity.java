package com.apps.dbrah_Provider.uis.activity_offer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.apps.dbrah_Provider.R;
import com.apps.dbrah_Provider.databinding.ActivityOfferBinding;
import com.apps.dbrah_Provider.uis.activity_base.BaseActivity;
import com.apps.dbrah_Provider.uis.activity_preview.PreviewActivity;

public class OfferActivity extends BaseActivity {
    private ActivityOfferBinding binding;
    private int avilable = 1;
    private int less = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_offer);
        initView();
    }

    private void initView() {

        setUpToolbar(binding.toolbar, getString(R.string.offer), R.color.white, R.color.black);
        binding.tvUnAvilable.setOnClickListener(view -> {
            if (avilable == 1) {
                avilable = 0;
                binding.tvUnAvilable.setBackground(getResources().getDrawable(R.drawable.small_rounded_primary));
                binding.flUnAvilable.setVisibility(View.VISIBLE);
                binding.tvUnAvilable.setTextColor(getResources().getColor(R.color.white));
                binding.tvLess.setBackground(getResources().getDrawable(R.drawable.rounded_grey9_stroke_grey7));
                binding.tvLess.setTextColor(getResources().getColor(R.color.grey7));
                binding.tvOTher.setBackground(getResources().getDrawable(R.drawable.rounded_grey9_stroke_grey7));
                binding.tvOTher.setTextColor(getResources().getColor(R.color.grey7));
                binding.llData.setVisibility(View.GONE);
            } else {
                avilable = 1;
                binding.flUnAvilable.setVisibility(View.GONE);
                binding.tvUnAvilable.setBackground(getResources().getDrawable(R.drawable.rounded_shape_gray1_strock6));
                binding.tvUnAvilable.setTextColor(getResources().getColor(R.color.colorAccent));
                binding.llData.setVisibility(View.GONE);

            }
        });
        binding.tvLess.setOnClickListener(view -> {

            less = 1;
            binding.tvLess.setBackground(getResources().getDrawable(R.drawable.small_rounded_primary));
            binding.tvLess.setVisibility(View.VISIBLE);
            binding.llItem.setVisibility(View.VISIBLE);
            binding.flBrand.setVisibility(View.GONE);
            binding.tvLess.setEnabled(false);
            binding.tvOTher.setEnabled(true);
            binding.llData.setVisibility(View.VISIBLE);
            binding.tvLess.setTextColor(getResources().getColor(R.color.white));
            binding.tvOTher.setTextColor(getResources().getColor(R.color.grey7));

            binding.tvOTher.setBackground(getResources().getDrawable(R.drawable.rounded_grey9_stroke_grey7));


        });
        binding.btnNext.setOnClickListener(view -> {
            Intent intent=new Intent(OfferActivity.this, PreviewActivity.class);
            startActivity(intent);
        });
        binding.tvOTher.setOnClickListener(view -> {

            less = 0;
            binding.tvOTher.setBackground(getResources().getDrawable(R.drawable.small_rounded_primary));
            binding.flBrand.setVisibility(View.VISIBLE);
            binding.tvLess.setEnabled(true);
            binding.tvOTher.setEnabled(false);
            binding.llItem.setVisibility(View.GONE);
            binding.llData.setVisibility(View.VISIBLE);
            binding.tvLess.setBackground(getResources().getDrawable(R.drawable.rounded_grey9_stroke_grey7));
            binding.tvLess.setTextColor(getResources().getColor(R.color.grey7));
            binding.tvOTher.setTextColor(getResources().getColor(R.color.white));


        });
    }
}