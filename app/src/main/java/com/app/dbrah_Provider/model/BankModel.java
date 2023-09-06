
package com.app.dbrah_Provider.model;

import java.io.Serializable;

public class BankModel implements Serializable {
    private String id;
    private String account_holder_name;
    private String account_bank_name;
    private String iban;
    private String provider_id;


    public String getId() {
        return id;
    }

    public String getAccount_holder_name() {
        return account_holder_name;
    }

    public String getIban() {
        return iban;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public String getAccount_bank_name() {
        return account_bank_name;
    }
}
