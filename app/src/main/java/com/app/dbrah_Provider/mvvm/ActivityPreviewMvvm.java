package com.app.dbrah_Provider.mvvm;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.app.dbrah_Provider.R;
import com.app.dbrah_Provider.model.AddOFFerDataModel;
import com.app.dbrah_Provider.model.CategoryDataModel;
import com.app.dbrah_Provider.model.CategoryModel;
import com.app.dbrah_Provider.model.EditProductModel;
import com.app.dbrah_Provider.model.ProductModel;
import com.app.dbrah_Provider.model.RecentProductDataModel;
import com.app.dbrah_Provider.model.StatusResponse;
import com.app.dbrah_Provider.model.UserModel;
import com.app.dbrah_Provider.remote.Api;
import com.app.dbrah_Provider.share.Common;
import com.app.dbrah_Provider.tags.Tags;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ActivityPreviewMvvm extends AndroidViewModel {


    public MutableLiveData<Boolean> save = new MutableLiveData<>();

    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivityPreviewMvvm(@NonNull Application application) {
        super(application);
    }





    public void addOffer(Context context, AddOFFerDataModel addOFFerDataModel){
        ProgressDialog dialog = Common.createProgressDialog(context, context.getResources().getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();

        Api.getService(Tags.base_url).addOffer(addOFFerDataModel)
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
                        try {
                            Log.e("Elllfl",response.code()+""+response.errorBody().string());
                        } catch (Exception e) {
                            //e.printStackTrace();
                        }
                        if (response.isSuccessful()){
                            if (response.body().getStatus()==200){
                                save.postValue(true);
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dialog.dismiss();
                        Log.e("error",e.toString());
                    }
                });
    }

}
