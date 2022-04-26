package com.apps.dbrah_Provider.model;

import android.content.Context;

import androidx.databinding.BaseObservable;

import com.apps.dbrah_Provider.BR;

import java.util.ArrayList;
import java.util.List;

public class EditAccountModel extends BaseObservable {
    private String image;
    private String store_name;
    private String phone_code;
    private String phone;
    private String email;
    private List<CategoryModel> categoryList;
    private String password;
    private String repeat_password;
    private List<String> commercial_images;


    public boolean isDAtaValid(Context context) {
        if (password == repeat_password) {
            return true;
        } else {
            return false;
        }
    }

    public EditAccountModel() {
        this.image = "";
        notifyPropertyChanged(BR.image);
        store_name = "";
        notifyPropertyChanged(BR.store_name);
        email = "";
        notifyPropertyChanged(BR.email);
        password = "";
        notifyPropertyChanged(BR.password);
        repeat_password = "";
        notifyPropertyChanged(BR.repeat_password);
        phone_code = "+20";
        notifyPropertyChanged(BR.phone_code);
        phone = "";
        notifyPropertyChanged(BR.phone);
        categoryList = new ArrayList<>();
        commercial_images = new ArrayList<>();
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getPhone_code() {
        return phone_code;
    }

    public void setPhone_code(String phone_code) {
        this.phone_code = phone_code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<CategoryModel> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryModel> categoryList) {
        this.categoryList = categoryList;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeat_password() {
        return repeat_password;
    }

    public void setRepeat_password(String repeat_password) {
        this.repeat_password = repeat_password;
    }

    public List<String> getCommercial_images() {
        return commercial_images;
    }

    public void setCommercial_images(List<String> commercial_images) {
        this.commercial_images = commercial_images;
    }
}
