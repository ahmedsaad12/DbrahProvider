package com.app.dbrah_Provider.model;

import android.content.Context;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.app.dbrah_Provider.BR;
import com.app.dbrah_Provider.R;

import org.apache.commons.validator.routines.checkdigit.IBANCheckDigit;


public class AddBankAccountModel extends BaseObservable {
    private String name;
    private String bankname;
    private String iban;


    public ObservableField<String> error_name = new ObservableField<>();
    public ObservableField<String> error_bankname = new ObservableField<>();
    public ObservableField<String> error_iban = new ObservableField<>();
    IBANCheckDigit a = new IBANCheckDigit();

    public boolean isDataValid(Context context) {
        if (!name.isEmpty() &&!bankname.isEmpty() &&
                !iban.isEmpty() &&
                a.isValid(iban)

        ) {
            error_name.set(null);
            error_iban.set(null);
            error_bankname.set(null);

            return true;

        } else {
            if (name.isEmpty()){
                error_name.set(context.getString(R.string.field_required));
            }else {
                error_name.set(null);

            }
            if (bankname.isEmpty()){
                error_bankname.set(context.getString(R.string.field_required));
            }else {
                error_bankname.set(null);

            }


            if (iban.isEmpty()){
                error_iban.set(context.getString(R.string.field_required));
            }if (!a.isValid(iban)){
                error_iban.set(context.getString(R.string.inv_iban));
                notifyChange();
            }else {
                error_iban.set(null);

            }



            return false;
        }

    }

    public AddBankAccountModel() {
        name = "";
        iban = "";

    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
        notifyPropertyChanged(BR.email);

    }
    @Bindable
    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
        notifyPropertyChanged(BR.bankname);

    }
}
