package com.app.dbrah_Provider.mvvm;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.app.dbrah_Provider.model.ProductDataModel;
import com.app.dbrah_Provider.model.ProductModel;
import com.app.dbrah_Provider.model.SettingDataModel;
import com.app.dbrah_Provider.remote.Api;
import com.app.dbrah_Provider.tags.Tags;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ActivityOfferMvvm extends AndroidViewModel {

    private MutableLiveData<List<ProductModel>> onRecentProductDataModel;
    private Context context;

    private CompositeDisposable disposable = new CompositeDisposable();

    private MutableLiveData<Boolean> isLoadingRecentProduct;
    private MutableLiveData<SettingDataModel.Data> onDataSuccess;

    public ActivityOfferMvvm(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();

    }

    public MutableLiveData<SettingDataModel.Data> getOnDataSuccess() {
        if (onDataSuccess == null) {
            onDataSuccess = new MutableLiveData<>();
        }
        return onDataSuccess;
    }

    public MutableLiveData<Boolean> getIsLoadingRecentProduct() {
        if (isLoadingRecentProduct == null) {
            isLoadingRecentProduct = new MutableLiveData<>();
        }
        return isLoadingRecentProduct;
    }



    public MutableLiveData<List<ProductModel>> getOnRecentProductDataModel() {
        if (onRecentProductDataModel == null) {
            onRecentProductDataModel = new MutableLiveData<>();
        }
        return onRecentProductDataModel;
    }



    public void getProduct(String provider_id) {
        getIsLoadingRecentProduct().setValue(true);
        Api.getService(Tags.base_url).getProduct(provider_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<ProductDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<ProductDataModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getData() != null && response.body().getStatus() == 200) {
                           onRecentProductDataModel.postValue(response.body().getData());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("error", e.toString());
                    }
                });
    }

    public void getSettings(Context context) {
        Api.getService(Tags.base_url)
                .getSettings()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<SettingDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<SettingDataModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getData() != null && response.body().getStatus() == 200) {
                                getOnDataSuccess().setValue(response.body().getData());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("error", e.toString());
                    }
                });
    }



}
