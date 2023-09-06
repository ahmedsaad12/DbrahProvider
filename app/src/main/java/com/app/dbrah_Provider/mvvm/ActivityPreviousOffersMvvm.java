package com.app.dbrah_Provider.mvvm;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.app.dbrah_Provider.R;
import com.app.dbrah_Provider.model.AddBankAccountModel;
import com.app.dbrah_Provider.model.StatusResponse;
import com.app.dbrah_Provider.model.UserModel;
import com.app.dbrah_Provider.remote.Api;
import com.app.dbrah_Provider.share.Common;
import com.app.dbrah_Provider.tags.Tags;

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


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
