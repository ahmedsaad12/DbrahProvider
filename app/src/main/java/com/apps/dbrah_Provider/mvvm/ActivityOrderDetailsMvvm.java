package com.apps.dbrah_Provider.mvvm;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.apps.dbrah_Provider.R;
import com.apps.dbrah_Provider.model.OrderModel;
import com.apps.dbrah_Provider.model.SingleOrderDataModel;
import com.apps.dbrah_Provider.model.StatusResponse;
import com.apps.dbrah_Provider.remote.Api;
import com.apps.dbrah_Provider.share.Common;
import com.apps.dbrah_Provider.tags.Tags;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ActivityOrderDetailsMvvm extends AndroidViewModel {

    private MutableLiveData<Boolean> isOrderDataLoading;
    private MutableLiveData<OrderModel> onOrderDetailsSuccess;
    private MutableLiveData<Integer> onStatusSuccess;

    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivityOrderDetailsMvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getIsOrderDataLoading() {
        if (isOrderDataLoading == null) {
            isOrderDataLoading = new MutableLiveData<>();
        }
        return isOrderDataLoading;
    }

    public MutableLiveData<OrderModel> getOnOrderDetailsSuccess() {
        if (onOrderDetailsSuccess == null) {
            onOrderDetailsSuccess = new MutableLiveData<>();
        }
        return onOrderDetailsSuccess;
    }

    public void getOrderDetails(String order_id,String provider_id) {
        getIsOrderDataLoading().setValue(true);
        Api.getService(Tags.base_url).getOrderDetails(order_id,provider_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<SingleOrderDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<SingleOrderDataModel> response) {
                        getIsOrderDataLoading().setValue(false);
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                                onOrderDetailsSuccess.setValue(response.body().getData().getOrder());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("error", e.getMessage());
                    }
                });
    }

    public MutableLiveData<Integer> getOnOrderStatusSuccess() {
        if (onStatusSuccess == null) {
            onStatusSuccess = new MutableLiveData<>();
        }
        return onStatusSuccess;
    }

    public void pinOrder( String order_id, String provider_id, Context context) {
        ProgressDialog dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        Api.getService(Tags.base_url).PinOrder(order_id, provider_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<StatusResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<StatusResponse> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatus() == 200) {
                                    getOnOrderStatusSuccess().setValue(1);


                                }
                            }
                        } else {

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("error", e.getMessage());
                        dialog.dismiss();
                    }
                });
    }

}
