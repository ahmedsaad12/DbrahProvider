package com.app.dbrah_Provider.mvvm;

import static android.content.ContentValues.TAG;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.app.dbrah_Provider.model.NotificationDataModel;
import com.app.dbrah_Provider.model.NotificationModel;
import com.app.dbrah_Provider.model.PreviousOfferDataModel;
import com.app.dbrah_Provider.model.UserModel;
import com.app.dbrah_Provider.remote.Api;
import com.app.dbrah_Provider.tags.Tags;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ActivityPreviousOffersMvvm extends AndroidViewModel {


    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivityPreviousOffersMvvm(@NonNull Application application) {
        super(application);
    }

    private MutableLiveData<List<PreviousOfferDataModel.Datum>> listMutableLiveData;
    private MutableLiveData<Boolean> isLoadingLivData;
    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
    public MutableLiveData<List<PreviousOfferDataModel.Datum>> getListMutableLiveData() {
        if (listMutableLiveData == null) {
            listMutableLiveData = new MutableLiveData<>();
        }
        return listMutableLiveData;
    }


    public MutableLiveData<Boolean> getIsLoading() {
        if (isLoadingLivData == null) {
            isLoadingLivData = new MutableLiveData<>();
        }
        return isLoadingLivData;
    }
    public void getpreviousoffers(UserModel userModel,int orderId, String lang) {
        isLoadingLivData.setValue(true);
Log.e("dlldldl",orderId+"");
Log.e("dlldldl",userModel.getData().getId()+"");

        Api.getService(Tags.base_url)
                .getPreviousOffers(userModel.getData().getId(),orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<PreviousOfferDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<PreviousOfferDataModel> response) {
                        isLoadingLivData.setValue(false);
                        Log.e("status",response.code()+"");
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatus() == 200) {
                                    listMutableLiveData.setValue(response.body().getData());
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: ", e);
                    }
                });

    }

}
