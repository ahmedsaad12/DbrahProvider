package com.apps.dbrah_Provider.model;

import java.io.Serializable;

public class OfferDataModel implements Serializable {
    private String product_id;
    private String qty;
    private String type;
    private String price;
    private String total_price;
    private String available_qty;
    private String other_product_id;
    private ProductModel productModel;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getAvailable_qty() {
        return available_qty;
    }

    public void setAvailable_qty(String available_qty) {
        this.available_qty = available_qty;
    }

    public String getOther_product_id() {
        return other_product_id;
    }

    public void setOther_product_id(String other_product_id) {
        this.other_product_id = other_product_id;
    }

    public ProductModel getProductModel() {
        return productModel;
    }

    public void setProductModel(ProductModel productModel) {
        this.productModel = productModel;
    }
}
