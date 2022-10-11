package com.app.dbrah_Provider.model;

import java.io.Serializable;
import java.util.List;

public class ReviewDataModel extends StatusResponse implements Serializable {
    private List<ReviewModel> data;

    public List<ReviewModel> getData() {
        return data;
    }
}
