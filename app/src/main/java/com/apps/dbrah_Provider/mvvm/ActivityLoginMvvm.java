package com.apps.dbrah_Provider.mvvm;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.apps.dbrah_Provider.R;
import com.apps.dbrah_Provider.model.CountryModel;
import com.apps.dbrah_Provider.model.UserModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class ActivityLoginMvvm extends AndroidViewModel {
    private static final String TAG = "ActivityVerificationMvvm";
    private Context context;

    private String phone, phone_code;

    public MutableLiveData<UserModel> userModelMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> found = new MutableLiveData<>();
    private MutableLiveData<List<CountryModel>> coListMutableLiveData;
    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivityLoginMvvm(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();


    }

    public MutableLiveData<List<CountryModel>> getCoListMutableLiveData() {
        if (coListMutableLiveData == null) {
            coListMutableLiveData = new MutableLiveData<>();
        }
        return coListMutableLiveData;
    }

    public void setCountry() {
        CountryModel[] countries = new CountryModel[]{
                new CountryModel("EG", "Egypt", "+20", R.drawable.flag_eg, "EGP"), new CountryModel("SA", "Saudi Arabia", "+966", R.drawable.flag_sa, "SAR")};
        coListMutableLiveData.postValue(new ArrayList<>(Arrays.asList(countries)));
    }





    private void login(Context context) {
//        ProgressDialog dialog = Common.createProgressDialog(context, context.getResources().getString(R.string.wait));
//        dialog.setCancelable(false);
//        dialog.show();
//        Api.getService(Tags.base_url).login( phone_code, phone).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io()).subscribe(new SingleObserver<Response<UserModel>>() {
//            @Override
//            public void onSubscribe(@NonNull Disposable d) {
//                disposable.add(d);
//            }
//
//            @Override
//            public void onSuccess(@NonNull Response<UserModel> userModelResponse) {
//                dialog.dismiss();
//
//                if (userModelResponse.isSuccessful()) {
//                    Log.e("status", userModelResponse.body().getStatus() + "");
//                    if (userModelResponse.body().getStatus() == 200) {
//
//                        userModelMutableLiveData.postValue(userModelResponse.body());
//                    } else if (userModelResponse.body().getStatus() == 422) {
//                        found.postValue("not_found");
//                    }
//
//                } else {
//
//                }
//
//            }
//
//            @Override
//            public void onError(@NonNull Throwable e) {
//                dialog.dismiss();
//                Log.e("ddkkdkd",e.toString());
//
//            }
//        });
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
