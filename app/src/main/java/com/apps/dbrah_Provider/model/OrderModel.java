package com.apps.dbrah_Provider.model;

import java.io.Serializable;
import java.util.List;

public class OrderModel implements Serializable {
    private String id;
    private String user_id;
    private String address_id;
    private String category_id;
    private String provider_id;
    private String note;
    private String pin;
    private String status;
    private String total_price;
    private String delivered_time;
    private String created_at;
    private String updated_at;
    private String offer_status_code;
    private String offer_status;
    private String is_pin;
    private UserModel.Data user;
    private AddressModel address;
    private String day;
    private String time;
    private List<OrderProductModel> details;

    public String getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getAddress_id() {
        return address_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public String getNote() {
        return note;
    }

    public String getPin() {
        return pin;
    }

    public String getStatus() {
        return status;
    }

    public String getTotal_price() {
        return total_price;
    }

    public String getDelivered_time() {
        return delivered_time;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getOffer_status_code() {
        return offer_status_code;
    }

    public void setOffer_status_code(String offer_status_code) {
        this.offer_status_code = offer_status_code;
    }

    public String getOffer_status() {
        return offer_status;
    }

    public String getIs_pin() {
        return is_pin;
    }

    public UserModel.Data getUser() {
        return user;
    }

    public AddressModel getAddress() {
        return address;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }


    public List<OrderProductModel> getDetails() {
        return details;
    }
    public static class Offers implements Serializable {
        private String id;
        private String order_id;
        private String provider_id;
        private String total_price;
        private String status;
        private String delivery_date_time;
        private String note;
        private String created_at;
        private String updated_at;
        private List<OfferDetail> offer_details;
        private boolean isNotFound;
        private boolean isPrice;
        private boolean isLess;
        private boolean isOther;


        public String getId() {
            return id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public String getProvider_id() {
            return provider_id;
        }

        public String getTotal_price() {
            return total_price;
        }

        public String getStatus() {
            return status;
        }

        public String getDelivery_date_time() {
            return delivery_date_time;
        }

        public String getNote() {
            return note;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public List<OfferDetail> getOffer_details() {
            return offer_details;
        }


        public boolean isNotFound() {
            return isNotFound;
        }

        public void setNotFound(boolean notFound) {
            isNotFound = notFound;
        }

        public boolean isPrice() {
            return isPrice;
        }

        public void setPrice(boolean price) {
            isPrice = price;
        }

        public boolean isLess() {
            return isLess;
        }

        public void setLess(boolean less) {
            isLess = less;
        }

        public boolean isOther() {
            return isOther;
        }

        public void setOther(boolean other) {
            isOther = other;
        }
    }

    public static class OfferDetail implements Serializable {
        private String id;
        private String order_id;
        private String order_offer_id;
        private String product_id;
        private String qty;
        private String type;
        private String price;
        private String total_price;
        private String available_qty;
        private String other_product_id;
        private String created_at;
        private String updated_at;
        private ProductModel product;
        private ProductModel other_product;
        private int new_qty;

        public String getId() {
            return id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public String getOrder_offer_id() {
            return order_offer_id;
        }

        public String getProduct_id() {
            return product_id;
        }

        public String getQty() {
            return qty;
        }

        public String getType() {
            return type;
        }

        public String getPrice() {
            return price;
        }

        public String getTotal_price() {
            return total_price;
        }

        public String getAvailable_qty() {
            return available_qty;
        }

        public String getOther_product_id() {
            return other_product_id;
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

        public ProductModel getOther_product() {
            return other_product;
        }

        public int getNew_qty() {
            return new_qty;
        }
    }

}
