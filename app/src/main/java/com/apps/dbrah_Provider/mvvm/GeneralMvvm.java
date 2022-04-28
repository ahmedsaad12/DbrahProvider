package com.apps.dbrah_Provider.mvvm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;



import java.util.List;

public class GeneralMvvm extends AndroidViewModel {
    private MutableLiveData<Integer> orderpage;
    private MutableLiveData<Boolean> onCurrentOrderRefreshed;


    public GeneralMvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Integer> getOrderpage() {
        if (orderpage == null) {
            orderpage = new MutableLiveData<>();
        }
        return orderpage;
    }
    public MutableLiveData<Boolean> getOnCurrentOrderRefreshed() {
        if (onCurrentOrderRefreshed == null) {
            onCurrentOrderRefreshed = new MutableLiveData<>();
        }
        return onCurrentOrderRefreshed;
    }

}
