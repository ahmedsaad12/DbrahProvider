package com.apps.dbrah_Provider.mvvm;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apps.dbrah_Provider.R;
import com.apps.dbrah_Provider.model.StatusResponse;
import com.apps.dbrah_Provider.model.UserModel;
import com.apps.dbrah_Provider.remote.Api;
import com.apps.dbrah_Provider.share.Common;
import com.apps.dbrah_Provider.tags.Tags;
import com.google.firebase.iid.FirebaseInstanceId;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class FragmentProfileMvvm extends AndroidViewModel {

    public MutableLiveData<Boolean> logout = new MutableLiveData<>();


    private CompositeDisposable disposable = new CompositeDisposable();

    public FragmentProfileMvvm(@NonNull Application application) {
        super(application);
    }


    public void logout(Context context, UserModel userModel){
        ProgressDialog dialog = Common.createProgressDialog(context, context.getResources().getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Log.e("dattta",userModel.getData().getId()+" "+userModel.getData().getFirebase_token());
        Api.getService(Tags.base_url).logout(userModel.getData().getId(),userModel.getData().getFirebase_token())
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
                        Log.e("statusLogout",response.code()+" "+response.body().getStatus());
                        if (response.isSuccessful()){
                            if (response.body().getStatus()==200){
                                logout.postValue(true);
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
