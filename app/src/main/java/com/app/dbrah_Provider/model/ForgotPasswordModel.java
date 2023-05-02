package com.app.dbrah_Provider.model;

import android.content.Context;
import android.util.Log;
import android.util.Patterns;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.app.dbrah_Provider.BR;
import com.app.dbrah_Provider.R;
import com.google.gson.annotations.SerializedName;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.io.Serializable;

import kotlin.jvm.internal.SerializedIr;


public class ForgotPasswordModel extends BaseObservable implements Serializable {
    private String phone_code;
    private String phone;
    private String code;

    private String email;
    private String type;
    public ObservableField<String> error_email = new ObservableField<>();
    public ObservableField<String> error_phone = new ObservableField<>();

    PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

    public ForgotPasswordModel() {
        phone_code = "+20";
        code = "EG";
        phone = "";
        email = "";

    }

    public boolean isDataValid(Context context) {
        boolean isValid = false;
        if (type.equals("phone")) {
            Phonenumber.PhoneNumber swissNumberProto;


            try {
                swissNumberProto = phoneUtil.parse("0" + phone, code);
                isValid = phoneUtil.isValidNumber(swissNumberProto);
            } catch (NumberParseException e) {
                Log.e("NumberParseException", e.toString());
            }
        }
        if ((type.equals("phone") && !phone.isEmpty() &&
                isValid)
                || (type.equals("email") && !email.isEmpty() &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
            error_phone.set(null);
            error_email.set(null);

            return true;
        } else {
            if (type.equals("phone")) {
                if (phone.isEmpty()) {
                    error_phone.set(context.getString(R.string.field_required));

                } else if (!isValid) {
                    error_phone.set(context.getString(R.string.invaild_phone));
                } else {
                    error_phone.set(null);

                }

            } else if (type.equals("email")) {
                if (email.isEmpty()) {
                    error_email.set(context.getString(R.string.field_required));
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    error_email.set(context.getString(R.string.inv_email));
                } else {
                    error_email.set(null);

                }

            }


            return false;
        }
    }

    @Bindable
    public String getPhone_code() {
        return phone_code;
    }

    public void setPhone_code(String phone_code) {
        this.phone_code = phone_code;
        notifyPropertyChanged(BR.phone_code);
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }


}