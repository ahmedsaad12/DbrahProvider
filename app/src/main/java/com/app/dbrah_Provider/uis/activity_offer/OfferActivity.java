package com.app.dbrah_Provider.uis.activity_offer;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.dbrah_Provider.R;
import com.app.dbrah_Provider.adapter.SpinnerProductAdapter;
import com.app.dbrah_Provider.databinding.ActivityOfferBinding;
import com.app.dbrah_Provider.model.AddOFFerDataModel;
import com.app.dbrah_Provider.model.OfferDataModel;
import com.app.dbrah_Provider.model.OrderModel;
import com.app.dbrah_Provider.model.ProductModel;
import com.app.dbrah_Provider.model.SettingDataModel;
import com.app.dbrah_Provider.mvvm.ActivityOfferMvvm;
import com.app.dbrah_Provider.uis.activity_base.BaseActivity;
import com.app.dbrah_Provider.uis.activity_preview.PreviewActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OfferActivity extends BaseActivity {
    private ActivityOfferBinding binding;
    private int avilable = 1;
    private int less = 1;
    private OrderModel orderModel;
    private String time;
    private String time_id;
    private String date;
    private int index = 0;
    private List<OfferDataModel> offerDataModelList;
    private AddOFFerDataModel addOFFerDataModel;
    private SpinnerProductAdapter spinnerProductAdapter;
    private ActivityOfferMvvm activityOfferMvvm;
    private double total = 0;
    private ActivityResultLauncher<Intent> launcher;
    private SettingDataModel.Data setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_offer);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        time = intent.getStringExtra("time");
        time_id = intent.getStringExtra("time_id");

        date = intent.getStringExtra("date");

        orderModel = (OrderModel) intent.getSerializableExtra("order");
    }

    @SuppressLint("CheckResult")
    private void initView() {
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                setResult(RESULT_OK);
                finish();
            }
        });
        binding.tvindex.setText((index + 1) + "");
        binding.tvSize.setText((orderModel.getDetails().size()) + "");
        activityOfferMvvm = ViewModelProviders.of(this).get(ActivityOfferMvvm.class);
         activityOfferMvvm.getOnDataSuccess().observe(this, model -> {
            setting = model;
        });
        binding.edtprice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().startsWith("0")) {
                    binding.edtprice.setText("");
                }
            }
        });
        binding.edtAprice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().startsWith("0")) {
                    binding.edtAprice.setText("");
                }
            }
        });
        binding.edtQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().startsWith("0")) {
                    binding.edtQuantity.setText("");
                }
            }
        });

        spinnerProductAdapter = new SpinnerProductAdapter(this, getLang());
        binding.spBrand.setAdapter(spinnerProductAdapter);
        offerDataModelList = new ArrayList<>();
        addOFFerDataModel = new AddOFFerDataModel();
        addOFFerDataModel.setOrderModel(orderModel);
        addOFFerDataModel.setDelivery_date_time_id(time_id);
        addOFFerDataModel.setTime(time);
        binding.setModel(orderModel.getDetails().get(index));
        binding.setLang(getLang());
        activityOfferMvvm.getIsLoadingRecentProduct().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

            }
        });
        activityOfferMvvm.getOnRecentProductDataModel().observe(this, new Observer<List<ProductModel>>() {
            @Override
            public void onChanged(List<ProductModel> productModels) {
                productModels.add(0, new ProductModel("اختر المنتج", "Choose Product"));
                spinnerProductAdapter.updateList(productModels);
            }
        });
        activityOfferMvvm.getProduct(getUserModel().getData().getId());
        setUpToolbar(binding.toolbar, getString(R.string.offer), R.color.white, R.color.black);
        binding.tvUnAvilable.setOnClickListener(view -> {
            binding.edtprice.setText("");
            binding.tvSinglePrice.setText("0");
            binding.tvTotalPrice.setText("0");
            binding.edtAprice.setText("");
            binding.edtQuantity.setText("");
            binding.spBrand.setSelection(0);
            if (avilable == 1) {
                binding.edtprice.setEnabled(false);

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
                binding.edtprice.setEnabled(true);

                binding.flUnAvilable.setVisibility(View.GONE);
                binding.tvUnAvilable.setBackground(getResources().getDrawable(R.drawable.rounded_shape_gray1_strock6));
                binding.tvUnAvilable.setTextColor(getResources().getColor(R.color.colorAccent));
                binding.llData.setVisibility(View.GONE);

            }
        });
        binding.tvLess.setOnClickListener(view -> {
            binding.edtprice.setText("");
            binding.tvSinglePrice.setText("0");
            binding.tvTotalPrice.setText("0");
            binding.edtAprice.setText("");
            binding.edtQuantity.setText("");
            binding.spBrand.setSelection(0);
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
            OfferDataModel offerDataModel = new OfferDataModel();
            offerDataModel.setProductModel(orderModel.getDetails().get(index).getProduct());
            boolean add = false;
            if (avilable == 1) {

                String price = binding.edtprice.getText().toString();
                if (!price.isEmpty()) {
                    binding.edtprice.setError(null);
                    offerDataModel.setQty(orderModel.getDetails().get(index).getQty());
                    offerDataModel.setAvailable_qty(orderModel.getDetails().get(index).getQty());
                    offerDataModel.setTotal_price((Double.parseDouble(price) * Double.parseDouble(orderModel.getDetails().get(index).getQty()) + ""));
                    offerDataModel.setProduct_id(orderModel.getDetails().get(index).getProduct_id());
                    offerDataModel.setPrice(price);
                    total += Double.parseDouble(offerDataModel.getTotal_price());
                    offerDataModel.setType("price");
                    add = true;
                } else {
                    binding.edtprice.setError(getResources().getString(R.string.field_required));
                }
            } else {
                binding.edtprice.setError(null);
                binding.edtAprice.setError(null);
                binding.edtQuantity.setError(null);
                String price = binding.edtAprice.getText().toString();

                if (less == 1) {
                    String qty = binding.edtQuantity.getText().toString();
                    if (!price.isEmpty() && !qty.isEmpty()) {
                        offerDataModel.setQty(orderModel.getDetails().get(index).getQty());
                        offerDataModel.setAvailable_qty(qty);
                        offerDataModel.setTotal_price((Double.parseDouble(price) * Double.parseDouble(qty) + ""));
                        offerDataModel.setProduct_id(orderModel.getDetails().get(index).getProduct_id());
                        total += Double.parseDouble(offerDataModel.getTotal_price());
                        offerDataModel.setPrice(price);
                        offerDataModel.setType("less");
                        add = true;
                    } else {
                        if (price.isEmpty()) {
                            binding.edtAprice.setError(getResources().getString(R.string.field_required));
                        } else {
                            binding.edtAprice.setError(null);

                        }
                        if (qty.isEmpty()) {
                            binding.edtQuantity.setError(getResources().getString(R.string.field_required));
                        } else {
                            binding.edtQuantity.setError(null);

                        }
                    }
                } else {
                    int pos = binding.spBrand.getSelectedItemPosition();
                    if (!price.isEmpty() && pos != 0) {
                        offerDataModel.setQty(orderModel.getDetails().get(index).getQty());
                        offerDataModel.setAvailable_qty(orderModel.getDetails().get(index).getQty());
                        offerDataModel.setTotal_price((Double.parseDouble(price) * Double.parseDouble(orderModel.getDetails().get(index).getQty()) + ""));
                        offerDataModel.setProduct_id(orderModel.getDetails().get(index).getProduct_id());
                        offerDataModel.setOther_product_id(activityOfferMvvm.getOnRecentProductDataModel().getValue().get(pos).getId());
                        offerDataModel.setPrice(price);
                        total += Double.parseDouble(offerDataModel.getTotal_price());
                        offerDataModel.setOther(activityOfferMvvm.getOnRecentProductDataModel().getValue().get(pos));
                        offerDataModel.setType("other");
                        add = true;
                    } else {
                        if (price.isEmpty()) {
                            binding.edtAprice.setError(getResources().getString(R.string.field_required));
                        } else {
                            binding.edtAprice.setError(null);

                        }
                        if (pos == 0) {
                            Toast.makeText(this, getResources().getString(R.string.ch_product), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
            if (add) {
                offerDataModelList.add(offerDataModel);
                index += 1;
                if (index < orderModel.getDetails().size()) {
                    binding.setModel(orderModel.getDetails().get(index));
                    binding.edtprice.setText("");
                    binding.edtprice.setEnabled(true);
                    binding.tvSinglePrice.setText("0");
                    binding.tvTotalPrice.setText("0");
                    binding.edtAprice.setText("");
                    binding.edtQuantity.setText("");
                    binding.spBrand.setSelection(0);
                    binding.tvindex.setText((index + 1) + "");
                } else {
                    String expectedtime = date ;
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

                    addOFFerDataModel.setOffer_details(offerDataModelList);
                    addOFFerDataModel.setOrder_id(orderModel.getId());
                    addOFFerDataModel.setTime(time);
                    addOFFerDataModel.setDate(date);
                    addOFFerDataModel.setTotal_before_tax(total + "");
                    addOFFerDataModel.setTotal_tax(((total*Double.parseDouble(setting.getTax()))/100)+"");
                    addOFFerDataModel.setTotal_price((total+Double.parseDouble(addOFFerDataModel.getTotal_tax()))+"");
                    try {
                        addOFFerDataModel.setDelivery_date_time(dateFormat.parse(expectedtime).getTime() + "");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    addOFFerDataModel.setProvider_id(getUserModel().getData().getId());
                    Intent intent = new Intent(OfferActivity.this, PreviewActivity.class);
                    intent.putExtra("data", addOFFerDataModel);
                    launcher.launch(intent);
                }
            }
//
        });
        binding.tvOTher.setOnClickListener(view -> {
            binding.edtprice.setText("");
            binding.tvSinglePrice.setText("0");
            binding.tvTotalPrice.setText("0");
            binding.edtAprice.setText("");
            binding.edtQuantity.setText("");
            binding.spBrand.setSelection(0);
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
        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            binding.edtprice.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    emitter.onNext(editable.toString());
                }
            });

        }).debounce(2, TimeUnit.SECONDS)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(query -> {
                    if (query != null && !query.isEmpty() && index < orderModel.getDetails().size()) {
                        binding.tvSinglePrice.setText(query);
                        binding.tvTotalPrice.setText((Double.parseDouble(query) * Double.parseDouble(orderModel.getDetails().get(index).getQty())) + "");
                    } else {
                        binding.tvSinglePrice.setText("0");
                        binding.tvTotalPrice.setText("0");
                    }
                });
        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            binding.edtAprice.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    emitter.onNext(editable.toString());
                }
            });

        }).debounce(2, TimeUnit.SECONDS)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(query -> {
                    if (query != null && !query.isEmpty() && index < orderModel.getDetails().size()) {
                        if (less == 0) {
                            binding.tvSinglePrice.setText(query);
                            binding.tvTotalPrice.setText((Double.parseDouble(query) * Double.parseDouble(orderModel.getDetails().get(index).getQty())) + "");
                        } else {
                            String qty = binding.edtQuantity.getText().toString();
                            if (!qty.isEmpty()) {
                                binding.tvSinglePrice.setText(query);
                                binding.tvTotalPrice.setText((Double.parseDouble(query) * Double.parseDouble(qty)) + "");
                            } else {
                                binding.tvSinglePrice.setText(query);

                            }
                        }
                    } else {
                        binding.tvSinglePrice.setText("0");
                        binding.tvTotalPrice.setText("0");
                    }
                });
        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            binding.edtQuantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    emitter.onNext(editable.toString());
                }
            });

        }).debounce(2, TimeUnit.SECONDS)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(query -> {
                    if (!query.isEmpty() && index < orderModel.getDetails().size()) {
                        String price = binding.edtAprice.getText().toString();
                        if (!price.isEmpty()) {
                            binding.tvSinglePrice.setText(price);
                            binding.tvTotalPrice.setText((Double.parseDouble(query) * Double.parseDouble(price)) + "");
                        } else {
                            binding.tvSinglePrice.setText("0");
                            binding.tvTotalPrice.setText("0");

                        }
                    } else {
                        binding.tvSinglePrice.setText("0");
                        binding.tvTotalPrice.setText("0");
                    }

                });
        activityOfferMvvm.getSettings(this);
    }
}