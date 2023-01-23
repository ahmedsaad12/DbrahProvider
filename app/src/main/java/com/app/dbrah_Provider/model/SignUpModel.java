package com.app.dbrah_Provider.model;

import android.content.Context;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.app.dbrah_Provider.BR;
import com.app.dbrah_Provider.R;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class SignUpModel extends BaseObservable {
    private String image;
    final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
    private String store_name;
    private String phone_code;
    private String phone;
    private String email;
    private List<CategoryModel> categoryList;
    private String vat_num;
    private String password;
    private String repeat_password;
    private List<String> commercial_images;
    private String selected_category;
    private int nationality_id;
    private int town_id;
    private String latitude;
    private String longitude;
    private String address;
    private String code;

    public ObservableField<String> error_store_name = new ObservableField<>();
    public ObservableField<String> error_email = new ObservableField<>();
    public ObservableField<String> error_vat_num = new ObservableField<>();
    public ObservableField<String> error_password = new ObservableField<>();
    public ObservableField<String> error_repeat_password = new ObservableField<>();
    public ObservableField<String> error_phone = new ObservableField<>();
    public ObservableField<String> error_address = new ObservableField<>();
    private Pattern pattern;
    PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();


    public boolean isDataValid(Context context) {
        Phonenumber.PhoneNumber swissNumberProto;
        boolean isValid=false;

        try {
            swissNumberProto = phoneUtil.parse("0"+phone, code);
            isValid = phoneUtil.isValidNumber(swissNumberProto);
        } catch (NumberParseException e) {
            Log.e("NumberParseException" , e.toString());
        }
        pattern = Pattern.compile(PASSWORD_PATTERN);

        if (!store_name.trim().isEmpty()
                && !email.isEmpty()
                && !phone.isEmpty() &&
                isValid&&
                nationality_id != 0 &&
                town_id != 0
                && (vat_num.isEmpty() || vat_num.length() == 15)
                && password.length() >= 6
                && pattern.matcher(password).matches()
                && repeat_password.length() >= 6
                && password.equals(repeat_password)
                && !address.isEmpty()
        ) {
            error_store_name.set(null);
            error_email.set(null);
            error_phone.set(null);
            error_vat_num.set(null);
            error_password.set(null);
            error_repeat_password.set(null);
            error_address.set(null);

            return true;
        } else {

            if (store_name.trim().isEmpty()) {
                error_store_name.set(context.getString(R.string.field_required));

            } else {
                error_store_name.set(null);

            }
            if (email.trim().isEmpty()) {
                error_email.set(context.getString(R.string.field_required));

            } else {
                error_email.set(null);

            }

            if (phone.isEmpty()) {
                error_phone.set(context.getString(R.string.field_required));

            }
            else if(!isValid){
                error_phone.set(context.getString(R.string.invaild_phone));
            }
            else {
                error_phone.set(null);

            }

            if (nationality_id == 0) {
                Toast.makeText(context, R.string.choose_nationality, Toast.LENGTH_SHORT).show();
            }
            if (town_id == 0) {
                Toast.makeText(context, R.string.choose_town, Toast.LENGTH_SHORT).show();
            }
//            if (!vat_num.isEmpty()) {
//                error_vat_num.set(context.getString(R.string.field_required));
//            }
//            else
            if (!vat_num.isEmpty() && vat_num.length() != 15) {
                error_vat_num.set(context.getString(R.string.must_equal));
            } else {
                error_vat_num.set(null);
            }

            if (password.isEmpty()) {
                error_password.set(context.getString(R.string.field_required));
            } else {
                if (!pattern.matcher(password).matches()) {
                    error_password.set(context.getResources().getString(R.string.password_weak));
                    error_password.notifyChange();
                } else {
                    error_password.set(null);
                }
            }
            if (repeat_password.isEmpty()) {
                error_repeat_password.set(context.getString(R.string.field_required));
            } else if (!repeat_password.equals(password)) {
                error_repeat_password.set(context.getString(R.string.repeat_password_must));
            } else {
                error_repeat_password.set(null);
            }
            if (categoryList.size() > 0) {

            } else {
                Toast.makeText(context, R.string.choose_category, Toast.LENGTH_SHORT).show();
            }
            if (commercial_images.size() > 0) {

            } else {
                Toast.makeText(context, R.string.add_commercial_record, Toast.LENGTH_SHORT).show();
            }
            if (address.isEmpty()) {
                error_address.set(context.getString(R.string.field_required));

            } else {
                error_address.set(null);

            }

            return false;
        }
    }

    public SignUpModel() {

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
        vat_num = "";
        notifyPropertyChanged(BR.vat_num);
        phone_code = "+20";
        notifyPropertyChanged(BR.phone_code);
        phone = "";
        notifyPropertyChanged(BR.phone);
        categoryList = new ArrayList<>();
        commercial_images = new ArrayList<>();
        this.selected_category = "";
        notifyPropertyChanged(BR.selected_category);
        this.address = "";
        notifyPropertyChanged(BR.address);
        setNationality_id(0);
        setTown_id(0);
    }


    @Bindable
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
        notifyPropertyChanged(BR.image);
    }


    @Bindable
    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
        notifyPropertyChanged(BR.store_name);
    }

    @Bindable
    public String getPhone_code() {
        return phone_code;
    }

    public void setPhone_code(String phone_code) {
        this.phone_code = phone_code;
        notifyPropertyChanged(BR.phone_code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public int getNationality_id() {
        return nationality_id;
    }

    public void setNationality_id(int nationality_id) {
        this.nationality_id = nationality_id;
        notifyPropertyChanged(BR.nationality_id);
    }

    @Bindable
    public int getTown_id() {
        return town_id;
    }

    public void setTown_id(int town_id) {
        this.town_id = town_id;
        notifyPropertyChanged(BR.town_id);
    }

    public List<CategoryModel> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryModel> categoryList) {
        this.categoryList = categoryList;
    }

    public List<String> getCommercial_images() {
        return commercial_images;
    }

    public void setCommercial_images(List<String> commercial_images) {
        this.commercial_images = commercial_images;
    }

    @Bindable
    public String getVat_num() {
        return vat_num;
    }

    public void setVat_num(String vat_num) {
        this.vat_num = vat_num;
        notifyPropertyChanged(BR.vat_num);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public String getRepeat_password() {
        return repeat_password;
    }

    public void setRepeat_password(String repeat_password) {
        this.repeat_password = repeat_password;
        notifyPropertyChanged(BR.repeat_password);
    }

    @Bindable
    public String getSelected_category() {
        return selected_category;
    }

    public void setSelected_category(String selected_category) {
        this.selected_category = selected_category;
        notifyPropertyChanged(BR.selected_category);
    }

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(BR.address);
    }

    @Bindable
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
        notifyPropertyChanged(BR.latitude);
    }

    @Bindable
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
        notifyPropertyChanged(BR.longitude);
    }
}