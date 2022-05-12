package com.apps.dbrah_Provider.uis.activity_current_prev_order_details;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.apps.dbrah_Provider.R;
import com.apps.dbrah_Provider.adapter.OfferAdapter;
import com.apps.dbrah_Provider.adapter.OfferDetialsAdapter;
import com.apps.dbrah_Provider.databinding.ActivityCurrentPreviousOrderDetailsBinding;
import com.apps.dbrah_Provider.model.ChatUserModel;
import com.apps.dbrah_Provider.model.NotiFire;
import com.apps.dbrah_Provider.model.OrderModel;
import com.apps.dbrah_Provider.model.SingleOrderDataModel;
import com.apps.dbrah_Provider.mvvm.ActivityCurrentPreviousOrderDetailsMvvm;
import com.apps.dbrah_Provider.uis.activity_base.BaseActivity;
import com.apps.dbrah_Provider.uis.activity_chat.ChatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Locale;

public class CurrentPreviousOrderDetailsActivity extends BaseActivity {
    private ActivityCurrentPreviousOrderDetailsBinding binding;
    private OfferDetialsAdapter offerDetialsAdapter;
    private ActivityCurrentPreviousOrderDetailsMvvm activityCurrentPreviousOrderDetailsMvvm;
    private String order_id;
    private SingleOrderDataModel singleOrderDataModel;
    private Intent intent;
    private static final int REQUEST_PHONE_CALL = 1;
    private boolean isDatachanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_current_previous_order_details);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        order_id = intent.getStringExtra("order_id");
    }

    private void initView() {
        activityCurrentPreviousOrderDetailsMvvm = ViewModelProviders.of(this).get(ActivityCurrentPreviousOrderDetailsMvvm.class);
        activityCurrentPreviousOrderDetailsMvvm.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.llData.setVisibility(View.GONE);
                    binding.progBar.setVisibility(View.VISIBLE);
                } else {
                    binding.progBar.setVisibility(View.GONE);
                    binding.llData.setVisibility(View.VISIBLE);
                }
            }
        });
        activityCurrentPreviousOrderDetailsMvvm.getOnStatuschangeSuccess().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    isDatachanged = true;
                    if (singleOrderDataModel.getData().getOrder().getOffer_status_code().equals("202")) {
                        singleOrderDataModel.getData().getOrder().setOffer_status_code("203");
                    } else if (singleOrderDataModel.getData().getOrder().getOffer_status_code().equals("203")) {
                        singleOrderDataModel.getData().getOrder().setOffer_status_code("204");

                    } else if (singleOrderDataModel.getData().getOrder().getOffer_status_code().equals("204")) {
                        singleOrderDataModel.getData().getOrder().setOffer_status_code("206");

                    }
                    binding.setModel(singleOrderDataModel.getData().getOrder());
                }
            }
        });
        activityCurrentPreviousOrderDetailsMvvm.getOnDataSuccess().observe(this, new Observer<SingleOrderDataModel>() {
            @Override
            public void onChanged(SingleOrderDataModel singleOrderDataModel) {
                if (singleOrderDataModel != null) {
                    binding.tvTotalPrice.setText(singleOrderDataModel.getData().getOffer().getTotal_price());
                    CurrentPreviousOrderDetailsActivity.this.singleOrderDataModel = singleOrderDataModel;
                    offerDetialsAdapter.updateList(singleOrderDataModel.getData().getOffer().getOffer_details());
                    binding.setModel(singleOrderDataModel.getData().getOrder());
                }
            }
        });
        binding.imCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (singleOrderDataModel.getData().getOrder().getUser() != null) {
                    intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", singleOrderDataModel.getData().getOrder().getUser().getPhone_code() + singleOrderDataModel.getData().getOrder().getUser().getPhone(), null));
                }
                if (intent != null) {
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(CurrentPreviousOrderDetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(CurrentPreviousOrderDetailsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                        } else {
                            startActivity(intent);
                        }
                    } else {
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(CurrentPreviousOrderDetailsActivity.this, getResources().getString(R.string.not_avail_now), Toast.LENGTH_SHORT).show();

                    // Common.CreateAlertDialog(QuestionsActivity.this, getResources().getString(R.string.phone_not_found));
                }

            }
        });
        binding.llMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", Double.parseDouble(singleOrderDataModel.getData().getOrder().getAddress().getLatitude()), Double.parseDouble(singleOrderDataModel.getData().getOrder().getAddress().getLongitude()));
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });

        setUpToolbar(binding.toolbar, getString(R.string.orderDetails), R.color.white, R.color.black);
        offerDetialsAdapter = new OfferDetialsAdapter(this, getLang());
        binding.recViewOffer.setLayoutManager(new LinearLayoutManager(this));
        binding.recViewOffer.setAdapter(offerDetialsAdapter);

        activityCurrentPreviousOrderDetailsMvvm.getOrderDetails(order_id, getUserModel().getData().getId());
        binding.imChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CurrentPreviousOrderDetailsActivity.this, ChatActivity.class);
                ChatUserModel model = new ChatUserModel(singleOrderDataModel.getData().getOrder().getUser_id(), singleOrderDataModel.getData().getOrder().getUser().getName(), singleOrderDataModel.getData().getOrder().getUser().getPhone(), singleOrderDataModel.getData().getOrder().getUser().getImage(), order_id);
                intent.putExtra("data", model);

                startActivity(intent);
            }
        });
        binding.flStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (singleOrderDataModel.getData().getOrder().getOffer_status_code().equals("202")) {
                    activityCurrentPreviousOrderDetailsMvvm.changgeOrderStatus(order_id, "preparing", CurrentPreviousOrderDetailsActivity.this);
                } else if (singleOrderDataModel.getData().getOrder().getOffer_status_code().equals("203")) {
                    activityCurrentPreviousOrderDetailsMvvm.changgeOrderStatus(order_id, "on_way", CurrentPreviousOrderDetailsActivity.this);

                } else if (singleOrderDataModel.getData().getOrder().getOffer_status_code().equals("204")) {
                    activityCurrentPreviousOrderDetailsMvvm.changgeOrderStatus(order_id, "delivered", CurrentPreviousOrderDetailsActivity.this);

                }
            }
        });
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PHONE_CALL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    Activity#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for Activity#requestPermissions for more details.
                            return;
                        }
                    }
                    startActivity(intent);
                } else {

                }
                return;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (isDatachanged) {

            setResult(RESULT_OK);
        }
        finish();

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOrderStatusChanged(NotiFire model) {
        if (!model.getOrder_status().isEmpty()) {
            activityCurrentPreviousOrderDetailsMvvm.getOrderDetails(order_id,getUserModel().getData().getId());
        }
    }
}