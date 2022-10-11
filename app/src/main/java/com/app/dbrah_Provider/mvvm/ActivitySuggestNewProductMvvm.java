package com.app.dbrah_Provider.mvvm;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.app.dbrah_Provider.R;
import com.app.dbrah_Provider.model.CategoryDataModel;
import com.app.dbrah_Provider.model.CategoryModel;
import com.app.dbrah_Provider.model.StatusResponse;
import com.app.dbrah_Provider.model.SuggestNewProductModel;
import com.app.dbrah_Provider.model.UserModel;
import com.app.dbrah_Provider.remote.Api;
import com.app.dbrah_Provider.share.Common;
import com.app.dbrah_Provider.tags.Tags;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class ActivitySuggestNewProductMvvm extends AndroidViewModel {
    private MutableLiveData<Boolean> isLoadingCategory;
    private MutableLiveData<List<CategoryModel>> onCategoryDataSuccess;
    public MutableLiveData<Boolean> send = new MutableLiveData<>();

    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivitySuggestNewProductMvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getIsLoadingCategory() {
        if (isLoadingCategory==null){
            isLoadingCategory=new MutableLiveData<>();
        }
        return isLoadingCategory;
    }

    public MutableLiveData<List<CategoryModel>> getOnCategoryDataSuccess() {
        if (onCategoryDataSuccess==null){
            onCategoryDataSuccess=new MutableLiveData<>();
        }
        return onCategoryDataSuccess;
    }


    public void getCategory() {
        getIsLoadingCategory().setValue(true);
        Api.getService(Tags.base_url).getCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<CategoryDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<CategoryDataModel> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getData() != null && response.body().getStatus() == 200) {
                                getIsLoadingCategory().setValue(false);

                                getOnCategoryDataSuccess().setValue(response.body().getData());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("error", e.toString());
                    }
                });
    }

    public void suggestProduct(Context context, SuggestNewProductModel model, UserModel userModel){
        ProgressDialog dialog = Common.createProgressDialog(context, context.getResources().getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();

        Log.e("ddd",model.getImage()+" "+model.getProduct_title()+" "+model.getSpecifications());
        MultipartBody.Part image = null;
        if (model.getImage() != null && !model.getImage().isEmpty()) {
            image = Common.getMultiPartImage(context, Uri.parse(model.getImage()), "image");
        }

        RequestBody provider_id=Common.getRequestBodyText(userModel.getData().getId()+"");
        RequestBody title = Common.getRequestBodyText(model.getProduct_title());
        RequestBody main_category_id = Common.getRequestBodyText(model.getMain_category_id()+"");
        RequestBody specifications = Common.getRequestBodyText(model.getSpecifications());
        Api.getService(Tags.base_url).suggestProduct(provider_id,image,title,main_category_id,specifications)
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
                        Log.e("eee",response.code()+" "+response.body().getStatus());
                        if (response.isSuccessful()){
                            if (response.body().getStatus()==200){
                                send.postValue(true);
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
