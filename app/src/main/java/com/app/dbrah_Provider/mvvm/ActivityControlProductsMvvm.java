package com.app.dbrah_Provider.mvvm;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.app.dbrah_Provider.R;
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

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ActivityControlProductsMvvm extends AndroidViewModel {

    private MutableLiveData<Boolean> isLoading;

    public MutableLiveData<Boolean> save = new MutableLiveData<>();

    private MutableLiveData<List<CategoryModel>> onCategoryDataSuccess;

    private MutableLiveData<List<CategoryModel>> onSubCategoryDataSuccess;
    private MutableLiveData<List<CategoryModel>> onSubSubCategoryDataSuccess;

    private MutableLiveData<List<ProductModel>> onProductsDataSuccess;

    private MutableLiveData<String> categoryId;

    private MutableLiveData<String> subCategoryId;
    private MutableLiveData<String> subsubCategoryId;

    private MutableLiveData<String> query;

    private MutableLiveData<Integer> categoryPos;

    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivityControlProductsMvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getIsLoading() {
        if (isLoading == null) {
            isLoading = new MutableLiveData<>();
        }
        return isLoading;
    }

    public MutableLiveData<List<CategoryModel>> getOnCategoryDataSuccess() {
        if (onCategoryDataSuccess == null) {
            onCategoryDataSuccess = new MutableLiveData<>();
        }
        return onCategoryDataSuccess;
    }


    public MutableLiveData<List<CategoryModel>> getOnSubCategoryDataSuccess() {
        if (onSubCategoryDataSuccess == null) {
            onSubCategoryDataSuccess = new MutableLiveData<>();
        }
        return onSubCategoryDataSuccess;
    }

    public MutableLiveData<List<CategoryModel>> getOnSubSubCategoryDataSuccess() {
        if (onSubSubCategoryDataSuccess == null) {
            onSubSubCategoryDataSuccess = new MutableLiveData<>();
        }
        return onSubSubCategoryDataSuccess;
    }

    public MutableLiveData<List<ProductModel>> getOnProductsDataSuccess() {
        if (onProductsDataSuccess == null) {
            onProductsDataSuccess = new MutableLiveData<>();
        }
        return onProductsDataSuccess;
    }

    public MutableLiveData<Integer> getCategoryPos() {
        if (categoryPos == null) {
            categoryPos = new MutableLiveData<>();
            categoryPos.setValue(-1);
        }
        return categoryPos;
    }

    public void setCategoryPos(int pos) {
        getCategoryPos().setValue(pos);
    }

    public MutableLiveData<String> getCategoryId() {
        if (categoryId == null) {
            categoryId = new MutableLiveData<>();
        }
        return categoryId;
    }

    public MutableLiveData<String> getQuery() {
        if (query == null) {
            query = new MutableLiveData<>();
        }
        return query;
    }

    public MutableLiveData<String> getSubCategoryId() {
        if (subCategoryId == null) {
            subCategoryId = new MutableLiveData<>();
        }
        return subCategoryId;
    }

    public MutableLiveData<String> getSubsubCategoryId() {
        if (subsubCategoryId == null) {
            subsubCategoryId = new MutableLiveData<>();
        }
        return subsubCategoryId;
    }

    public void setCategoryId(String categoryId, UserModel userModel) {
        getCategoryId().setValue(categoryId);
        getSubCategory(categoryId, userModel);
    }

    public void getCategory() {
        getIsLoading().setValue(true);
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
                                if (response.body().getData().size() == 0) {
                                    getIsLoading().setValue(false);

                                }
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


    public void getSubCategory(String cat_id, UserModel userModel) {
        getIsLoading().setValue(true);

        getOnSubCategoryDataSuccess().setValue(new ArrayList<>());
        getOnSubSubCategoryDataSuccess().setValue(new ArrayList<>());
        getOnProductsDataSuccess().setValue(new ArrayList<>());
        Api.getService(Tags.base_url).getSubCategory(cat_id)
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
                                List<CategoryModel> list = response.body().getData();

                                if (list.size() > 0) {
                                    CategoryModel model = new CategoryModel(null, "الكل", "All", null, true);
                                    list.add(0, model);
                                }
                                getCategoryId().setValue(cat_id);
                                if (!list.isEmpty())
                                    getSubCategoryId().setValue(list.get(0).getId());

                                getOnSubCategoryDataSuccess().setValue(list);
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        Log.e("error", e.toString());
                    }
                });
    }

    public void getSubSubCategory(String sub_cat_id, UserModel userModel) {

        getOnSubSubCategoryDataSuccess().setValue(new ArrayList<>());
        Api.getService(Tags.base_url).getSubSubCategory(sub_cat_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<CategoryDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<CategoryDataModel> response) {
                        Log.e("d;dlldl", response.code() + "" + response.body().getStatus());
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getData() != null && response.body().getStatus() == 200) {
                                getIsLoading().setValue(false);
                                List<CategoryModel> list = response.body().getData();

                                if (list.size() > 0) {
                                    CategoryModel model = new CategoryModel(null, "الكل", "All", null, true);
                                    list.add(0, model);
                                }
                                Log.e("d;dlldl", response.code() + "" + response.body().getStatus() + "" + list.size());

                                getSubCategoryId().setValue(sub_cat_id);
                                if (list.size() > 0) {
                                    getSubsubCategoryId().setValue(list.get(0).getId());
                                }
                                controlProducts(userModel);
                                getOnSubSubCategoryDataSuccess().setValue(list);
                            } else {
                                controlProducts(userModel);

                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        Log.e("error", e.toString());
                    }
                });
    }

    public void controlProducts(UserModel userModel) {
        getIsLoading().postValue(true);
        Log.e("data", userModel.getData().getId() + " " + getCategoryId().getValue() + " " + getSubCategoryId().getValue() + "" + getSubsubCategoryId().getValue());
        Api.getService(Tags.base_url).controlProducts(userModel.getData().getId(), getCategoryId().getValue(), getSubCategoryId().getValue(), getSubsubCategoryId().getValue())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<RecentProductDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<RecentProductDataModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getData() != null && response.body().getStatus() == 200) {

                                prepareProducts(response.body().getData());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getIsLoading().setValue(false);
                        Log.e("error", e.toString());
                    }
                });
    }

    private void prepareProducts(List<ProductModel> data) {
        for (int index = 0; index < data.size(); index++) {
            ProductModel productModel = data.get(index);
            data.set(index, productModel);
        }
        getIsLoading().setValue(false);
        getOnProductsDataSuccess().setValue(data);
    }

    public void editProducts(Context context, EditProductModel editProductModel) {
        ProgressDialog dialog = Common.createProgressDialog(context, context.getResources().getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();

        Log.e("list", editProductModel.getProducts_id() + " " + editProductModel.getProvider_id());
        Api.getService(Tags.base_url).editProducts(editProductModel)
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
                        Log.e("status", response.code() + " " + response.body().getStatus());
                        if (response.isSuccessful()) {
                            if (response.body().getStatus() == 200) {
                                save.postValue(true);
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dialog.dismiss();
                        Log.e("error", e.toString());
                    }
                });
    }

}
