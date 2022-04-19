package com.apps.dbrah_Provider.uis.activity_offer;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.apps.dbrah_Provider.R;
import com.apps.dbrah_Provider.adapter.SpinnerProductAdapter;
import com.apps.dbrah_Provider.databinding.ActivityOfferBinding;
import com.apps.dbrah_Provider.model.AddOFFerDataModel;
import com.apps.dbrah_Provider.model.OfferDataModel;
import com.apps.dbrah_Provider.model.OrderModel;
import com.apps.dbrah_Provider.model.ProductModel;
import com.apps.dbrah_Provider.mvvm.ActivityOfferMvvm;
import com.apps.dbrah_Provider.uis.activity_base.BaseActivity;
import com.apps.dbrah_Provider.uis.activity_preview.PreviewActivity;

import java.util.ArrayList;
import java.util.List;
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
    private String date;
    private int index = 0;
    private List<OfferDataModel> offerDataModelList;
    private AddOFFerDataModel addOFFerDataModel;
    private SpinnerProductAdapter spinnerProductAdapter;
    private ActivityOfferMvvm activityOfferMvvm;

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
        date = intent.getStringExtra("date");
        orderModel = (OrderModel) intent.getSerializableExtra("order");
    }

    private void initView() {
        binding.tvindex.setText((index + 1) + "");
        binding.tvSize.setText((orderModel.getDetails().size()) + "");
        activityOfferMvvm = ViewModelProviders.of(this).get(ActivityOfferMvvm.class);
        spinnerProductAdapter = new SpinnerProductAdapter(this, getLang());
        binding.spBrand.setAdapter(spinnerProductAdapter);
        offerDataModelList = new ArrayList<>();
        addOFFerDataModel = new AddOFFerDataModel();
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
                        offerDataModel.setOther_product_id(activityOfferMvvm.getOnRecentProductDataModel().getValue().get(pos - 1).getId());
                        offerDataModel.setPrice(price);
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
                    binding.tvSinglePrice.setText("");
                    binding.tvTotalPrice.setText("");
                    binding.edtAprice.setText("");
                    binding.edtQuantity.setText("");
                    binding.spBrand.setSelection(0);
                    binding.tvindex.setText((index + 1) + "");
                } else {
                    Intent intent = new Intent(OfferActivity.this, PreviewActivity.class);
                    startActivity(intent);
                }
            }
//
        });
        binding.tvOTher.setOnClickListener(view -> {
            binding.edtprice.setText("");
            binding.tvSinglePrice.setText("");
            binding.tvTotalPrice.setText("");
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
                  binding.tvSinglePrice.setText(query);
                  binding.tvTotalPrice.setText((Double.parseDouble(query)*Double.parseDouble(orderModel.getDetails().get(index).getQty()))+"");
                });
    }
}