package com.apps.dbrah_Provider.mvvm;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apps.dbrah_Provider.model.StatusResponse;
import com.apps.dbrah_Provider.model.UserModel;
import com.apps.dbrah_Provider.remote.Api;
import com.apps.dbrah_Provider.tags.Tags;
import com.google.firebase.iid.FirebaseInstanceId;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ActivityHomeMvvm extends AndroidViewModel {

    public MutableLiveData<String> firebase = new MutableLiveData<>();

    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivityHomeMvvm(@NonNull Application application) {
        super(application);
    }

    public void updateFirebase(Context context, UserModel userModel) {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener((Activity) context, task -> {
            if (task.isSuccessful()) {
                String token = task.getResult().getToken();

                Log.e("token",token+"_");
                Api.getService(Tags.base_url).updateFirebasetoken( userModel.getData().getId(),token , "android")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<Response<StatusResponse>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                disposable.add(d);
                            }

                            @Override
                            public void onSuccess(@NonNull Response<StatusResponse> statusResponseResponse) {
                                if (statusResponseResponse.isSuccessful()) {
                                    if (statusResponseResponse.body().getStatus() == 200) {
                                        firebase.postValue(token);
                                        Log.e("token", "updated successfully");
                                    }
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable throwable) {
                                Log.e("error",throwable.toString());
                            }
                        });
            }
        });


    }

}
