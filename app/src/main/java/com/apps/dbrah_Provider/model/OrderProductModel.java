package com.apps.dbrah_Provider.model;

import java.io.Serializable;

public class OrderProductModel implements Serializable {
    private String id;
    private String order_id;
    private String product_id;
    private String qty;
    private String category_id;
    private String sub_category_id;
    private String user_id;
    private String created_at;
    private String updated_at;
    private ProductModel product;

    public String getId() {
        return id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getQty() {
        return qty;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getSub_category_id() {
        return sub_category_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public ProductModel getProduct() {
        return product;
    }
}
