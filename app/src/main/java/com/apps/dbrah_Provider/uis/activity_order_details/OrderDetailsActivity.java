package com.apps.dbrah_Provider.uis.activity_order_details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.apps.dbrah_Provider.R;
import com.apps.dbrah_Provider.adapter.ProductAdapter;
import com.apps.dbrah_Provider.databinding.ActivityOrderDetailsBinding;
import com.apps.dbrah_Provider.uis.activity_base.BaseActivity;
import com.apps.dbrah_Provider.uis.activity_offer.OfferActivity;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsActivity extends BaseActivity {
    private ActivityOrderDetailsBinding binding;
    private ProductAdapter productAdapter;
    private List<Object> orderList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_order_details);
        initView();
    }

    private void initView() {
        binding.setLang(getLang());
        setUpToolbar(binding.toolbar, getString(R.string.orderDetails), R.color.white, R.color.black);

        orderList=new ArrayList<>();
        productAdapter=new ProductAdapter(orderList,this);
        binding.recViewProducts.setLayoutManager(new LinearLayoutManager(this));
        binding.recViewProducts.setAdapter(productAdapter);
        binding.btnAddOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.flExpectedTime.setVisibility(View.VISIBLE);
            }
        });
        binding.btnConfirm.setOnClickListener(view -> {
            binding.flExpectedTime.setVisibility(View.GONE);
            Intent intent=new Intent(OrderDetailsActivity.this, OfferActivity.class);
            startActivity(intent);
        });
    }
}