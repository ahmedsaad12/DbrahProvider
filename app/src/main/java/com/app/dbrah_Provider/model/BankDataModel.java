package com.app.dbrah_Provider.model;

import java.io.Serializable;
import java.util.List;

public class BankDataModel extends StatusResponse implements Serializable {
    private List<BankModel> data;

    public List<BankModel> getData() {
        return data;
    }
}
