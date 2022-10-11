package com.app.dbrah_Provider.model;

import java.io.Serializable;
import java.util.List;

public class EditProductModel implements Serializable {
    private String provider_id;
    private List<String> products_id;

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public List<String> getProducts_id() {
        return products_id;
    }

    public void setProducts_id(List<String> products_id) {
        this.products_id = products_id;
    }
}
