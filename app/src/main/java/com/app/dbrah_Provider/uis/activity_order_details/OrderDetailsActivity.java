package com.app.dbrah_Provider.uis.activity_order_details;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.dbrah_Provider.R;
import com.app.dbrah_Provider.adapter.OrderAdapter;
import com.app.dbrah_Provider.adapter.ProductAdapter;
import com.app.dbrah_Provider.adapter.SpinnerTimeAdapter;
import com.app.dbrah_Provider.databinding.ActivityOrderDetailsBinding;
import com.app.dbrah_Provider.model.NotiFire;
import com.app.dbrah_Provider.model.OrderModel;
import com.app.dbrah_Provider.model.TimeModel;
import com.app.dbrah_Provider.mvvm.ActivityOrderDetailsMvvm;
import com.app.dbrah_Provider.uis.activity_base.BaseActivity;
import com.app.dbrah_Provider.uis.activity_offer.OfferActivity;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderDetailsActivity extends BaseActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private ActivityOrderDetailsBinding binding;
    private ProductAdapter productAdapter;
    private ActivityOrderDetailsMvvm activityOrderDetailsMvvm;
    private String order_id;
    private TimePickerDialog timePickerDialog;
    private DatePickerDialog datePickerDialog;
    private String time = null, date = null, time_id;
    private OrderModel orderModel;
    private ActivityResultLauncher<Intent> launcher;
    private boolean isDatachanged;
    private SpinnerTimeAdapter spinnerTimeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_details);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        order_id = intent.getStringExtra("order_id");
    }

    private void initView() {
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                setResult(RESULT_OK);
                finish();
            }
        });
        activityOrderDetailsMvvm = ViewModelProviders.of(this).get(ActivityOrderDetailsMvvm.class);
        activityOrderDetailsMvvm.getIsOrderDataLoading().observe(this, new Observer<Boolean>() {
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
        activityOrderDetailsMvvm.getOnOrderStatusSuccess().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == 1) {
                    isDatachanged = true;
                    activityOrderDetailsMvvm.getOrderDetails(order_id, getUserModel().getData().getId());
                } else {
                    Intent intent = getIntent();
                    intent.putExtra("data", "hidden");
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
        activityOrderDetailsMvvm.getOnOrderDetailsSuccess().observe(this, new Observer<OrderModel>() {
            @Override
            public void onChanged(OrderModel orderModel) {
                if (orderModel != null) {
                    OrderDetailsActivity.this.orderModel = orderModel;
                    binding.setModel(orderModel);
                    productAdapter.updateList(orderModel.getDetails());
                    if (orderModel.getIs_pin().equals("1")) {
                        binding.imPin.setColorFilter(ContextCompat.getColor(OrderDetailsActivity.this, R.color.white), PorterDuff.Mode.SRC_IN);

                    }
                    else{
                        binding.imPin.setColorFilter(ContextCompat.getColor(OrderDetailsActivity.this, R.color.grey7), PorterDuff.Mode.SRC_IN);

                    }
                }
            }
        });
        activityOrderDetailsMvvm.getOrderDetails(order_id, getUserModel().getData().getId());
        activityOrderDetailsMvvm.getOnDataSuccess().observe(this, timeModels -> {

            if (spinnerTimeAdapter != null) {
                spinnerTimeAdapter.updateList(timeModels);

            }


        });
        spinnerTimeAdapter = new SpinnerTimeAdapter(this);

        binding.spinner.setAdapter(spinnerTimeAdapter);

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    time = null;
                    time_id = "";
                } else {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
                    TimeModel timeModel = (TimeModel) parent.getSelectedItem();
                    time_id = timeModel.getId();
                    time = dateFormat.format(new Date(timeModel.getFrom() * 1000)) + " - " + dateFormat.format(new Date(timeModel.getTo() * 1000));
                    ;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.llpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityOrderDetailsMvvm.pinOrder(order_id, getUserModel().getData().getId(), OrderDetailsActivity.this);
            }
        });
        binding.llMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", Double.parseDouble(orderModel.getAddress().getLatitude()), Double.parseDouble(orderModel.getAddress().getLongitude()));
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });
        binding.btnHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityOrderDetailsMvvm.hideOrder(order_id, getUserModel().getData().getId(), OrderDetailsActivity.this);
            }
        });
        binding.setLang(getLang());
        setUpToolbar(binding.toolbar, getString(R.string.orderDetails), R.color.white, R.color.black);

        productAdapter = new ProductAdapter(this, getLang());
        binding.recViewProducts.setLayoutManager(new LinearLayoutManager(this));
        binding.recViewProducts.setAdapter(productAdapter);
        binding.btnAddOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.flExpectedTime.setVisibility(View.VISIBLE);
            }
        });
        binding.btnConfirm.setOnClickListener(view -> {
            if (time != null && date != null) {
                binding.flExpectedTime.setVisibility(View.GONE);
                binding.tvDate.setError(null);

                // binding.tvTime.setError(null);
                Intent intent = new Intent(OrderDetailsActivity.this, OfferActivity.class);
                intent.putExtra("time", time);
                intent.putExtra("time_id", time_id);

                intent.putExtra("date", date);
                intent.putExtra("order", orderModel);
                launcher.launch(intent);
            }
            else {
                if (time == null) {
                    Toast.makeText(this, getResources().getString(R.string.ch_time), Toast.LENGTH_LONG).show();
                    //binding.tvTime.setError(getResources().getString(R.string.field_required));
                } else {
                    // binding.tvTime.setError(null);

                }
                if (date == null) {
                    binding.tvDate.setError(getResources().getString(R.string.field_required));
                } else {
                    binding.tvDate.setError(null);

                }
            }

        });
        //binding.tvTime.setOnClickListener(view -> timePickerDialog.show(getSupportFragmentManager(), ""));
        binding.tvDate.setOnClickListener(view -> datePickerDialog.show(getSupportFragmentManager(), ""));


        createDateDialog();
        createTimeDialog();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        activityOrderDetailsMvvm.getTime(this);

    }

    private void createDateDialog() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        datePickerDialog = DatePickerDialog.newInstance(OrderDetailsActivity.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.dismissOnPause(true);
        datePickerDialog.setAccentColor(ActivityCompat.getColor(this, R.color.colorPrimary));
        datePickerDialog.setCancelColor(ActivityCompat.getColor(this, R.color.grey4));
        datePickerDialog.setOkColor(ActivityCompat.getColor(this, R.color.colorPrimary));
        datePickerDialog.setMinDate(calendar);
        datePickerDialog.setOkText(getString(R.string.select));
        datePickerDialog.setCancelText(getString(R.string.cancel));
        datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);

    }

    private void createTimeDialog() {

        Calendar calendar = Calendar.getInstance();
        timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND), true);
        timePickerDialog.dismissOnPause(true);
        timePickerDialog.setAccentColor(ActivityCompat.getColor(this, R.color.colorPrimary));
        timePickerDialog.setCancelColor(ActivityCompat.getColor(this, R.color.grey4));
        timePickerDialog.setOkColor(ActivityCompat.getColor(this, R.color.colorPrimary));

        // datePickerDialog.setOkText(getString(R.string.select));
        //datePickerDialog.setCancelText(getString(R.string.cancel));
        timePickerDialog.setVersion(TimePickerDialog.Version.VERSION_2);
        //  timePickerDialog.setMinTime(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));

    }


    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
        time = dateFormat.format(new Date(calendar.getTimeInMillis()));
        //binding.tvTime.setText(time);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.MONTH, monthOfYear);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        date = dateFormat.format(new Date(calendar.getTimeInMillis()));
        binding.tvDate.setText(date);
    }

    @Override
    public void onBackPressed() {
        if (isDatachanged) {
            Intent intent = getIntent();
            intent.putExtra("data", "hidden");
            setResult(RESULT_OK, intent);
        }
        finish();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOrderStatusChanged(NotiFire model) {
        if (!model.getOrder_status().isEmpty()) {
            activityOrderDetailsMvvm.getOrderDetails(order_id, getUserModel().getData().getId());
        }
    }
}