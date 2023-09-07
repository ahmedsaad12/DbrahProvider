package com.app.dbrah_Provider.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class PreviousOfferDataModel implements Serializable {
    public ArrayList<Datum> data;
    public String msg;
    public int status;

    public ArrayList<Datum> getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public int getStatus() {
        return status;
    }
    public class SubCategory implements Serializable{
        public int id;
        public String title_ar;
        public String title_en;
        public String image;
        public String level;
        public int status;
        public Object admin_id;
        public Object date;
        public Date created_at;
        public Date updated_at;

        public int getId() {
            return id;
        }

        public String getTitle_ar() {
            return title_ar;
        }

        public String getTitle_en() {
            return title_en;
        }

        public String getImage() {
            return image;
        }

        public String getLevel() {
            return level;
        }

        public int getStatus() {
            return status;
        }

        public Object getAdmin_id() {
            return admin_id;
        }

        public Object getDate() {
            return date;
        }

        public Date getCreated_at() {
            return created_at;
        }

        public Date getUpdated_at() {
            return updated_at;
        }
    }

    public class Datum implements Serializable{
        public int id;
        public int order_id;
        public int provider_id;
        public double total_price;
        public int total_before_tax;
        public int total_tax;
        public String status;
        public int delivery_date_time_id;
        public long delivery_date_time;
        public Object note;
        public Date created_at;
        public Date updated_at;
        public ArrayList<OfferDetail> offer_details;
        public Order order;

        public int getId() {
            return id;
        }

        public int getOrder_id() {
            return order_id;
        }

        public int getProvider_id() {
            return provider_id;
        }

        public double getTotal_price() {
            return total_price;
        }

        public int getTotal_before_tax() {
            return total_before_tax;
        }

        public int getTotal_tax() {
            return total_tax;
        }

        public String getStatus() {
            return status;
        }

        public int getDelivery_date_time_id() {
            return delivery_date_time_id;
        }

        public long getDelivery_date_time() {
            return delivery_date_time;
        }

        public Object getNote() {
            return note;
        }

        public Date getCreated_at() {
            return created_at;
        }

        public Date getUpdated_at() {
            return updated_at;
        }

        public ArrayList<OfferDetail> getOffer_details() {
            return offer_details;
        }

        public Order getOrder() {
            return order;
        }
    }

    public class MainCategory implements Serializable{
        public int id;
        public String title_ar;
        public String title_en;
        public String image;
        public String level;
        public int status;
        public Object admin_id;
        public Object date;
        public Date created_at;
        public Date updated_at;

        public int getId() {
            return id;
        }

        public String getTitle_ar() {
            return title_ar;
        }

        public String getTitle_en() {
            return title_en;
        }

        public String getImage() {
            return image;
        }

        public String getLevel() {
            return level;
        }

        public int getStatus() {
            return status;
        }

        public Object getAdmin_id() {
            return admin_id;
        }

        public Object getDate() {
            return date;
        }

        public Date getCreated_at() {
            return created_at;
        }

        public Date getUpdated_at() {
            return updated_at;
        }
    }

    public class OfferDetail implements Serializable{
        public int id;
        public int order_id;
        public int order_offer_id;
        public int product_id;
        public int qty;
        public String type;
        public int price;
        public int total_price;
        public int available_qty;
        public Object other_product_id;
        public Date created_at;
        public Date updated_at;
        public Product product;
        public int new_qty;
        public Object other_product;

        public int getId() {
            return id;
        }

        public int getOrder_id() {
            return order_id;
        }

        public int getOrder_offer_id() {
            return order_offer_id;
        }

        public int getProduct_id() {
            return product_id;
        }

        public int getQty() {
            return qty;
        }

        public String getType() {
            return type;
        }

        public int getPrice() {
            return price;
        }

        public int getTotal_price() {
            return total_price;
        }

        public int getAvailable_qty() {
            return available_qty;
        }

        public Object getOther_product_id() {
            return other_product_id;
        }

        public Date getCreated_at() {
            return created_at;
        }

        public Date getUpdated_at() {
            return updated_at;
        }

        public Product getProduct() {
            return product;
        }

        public int getNew_qty() {
            return new_qty;
        }

        public Object getOther_product() {
            return other_product;
        }
    }

    public class Order implements Serializable{
        public int id;
        public int user_id;
        public int address_id;
        public int category_id;
        public Object provider_id;
        public Object accepted_offer_id;
        public Object note;
        public Object pin;
        public String status;
        public int total_price;
        public int total_before_tax;
        public int total_tax;
        public int delivered_time;
        public Date created_at;
        public Date updated_at;
        public boolean provider_rated;
        public User user;
        public int getId() {
            return id;
        }

        public int getUser_id() {
            return user_id;
        }

        public int getAddress_id() {
            return address_id;
        }

        public int getCategory_id() {
            return category_id;
        }

        public Object getProvider_id() {
            return provider_id;
        }

        public Object getAccepted_offer_id() {
            return accepted_offer_id;
        }

        public Object getNote() {
            return note;
        }

        public Object getPin() {
            return pin;
        }

        public String getStatus() {
            return status;
        }

        public int getTotal_price() {
            return total_price;
        }

        public int getTotal_before_tax() {
            return total_before_tax;
        }

        public int getTotal_tax() {
            return total_tax;
        }

        public int getDelivered_time() {
            return delivered_time;
        }

        public Date getCreated_at() {
            return created_at;
        }

        public Date getUpdated_at() {
            return updated_at;
        }

        public boolean isProvider_rated() {
            return provider_rated;
        }

        public User getUser() {
            return user;
        }
    }

    public class Product implements Serializable{
        public int id;
        public String main_image;
        public String title_ar;
        public String title_en;
        public int category_id;
        public int sub_category_id;
        public Object sub_sub_category_id;
        public String details_at;
        public String details_en;
        public int status;
        public Object date;
        public Date created_at;
        public Date updated_at;
        public Object admin_id;
        public boolean is_list;
        public MainCategory main_category;
        public SubCategory sub_category;

        public int getId() {
            return id;
        }

        public String getMain_image() {
            return main_image;
        }

        public String getTitle_ar() {
            return title_ar;
        }

        public String getTitle_en() {
            return title_en;
        }

        public int getCategory_id() {
            return category_id;
        }

        public int getSub_category_id() {
            return sub_category_id;
        }

        public Object getSub_sub_category_id() {
            return sub_sub_category_id;
        }

        public String getDetails_at() {
            return details_at;
        }

        public String getDetails_en() {
            return details_en;
        }

        public int getStatus() {
            return status;
        }

        public Object getDate() {
            return date;
        }

        public Date getCreated_at() {
            return created_at;
        }

        public Date getUpdated_at() {
            return updated_at;
        }

        public Object getAdmin_id() {
            return admin_id;
        }

        public boolean isIs_list() {
            return is_list;
        }

        public MainCategory getMain_category() {
            return main_category;
        }

        public SubCategory getSub_category() {
            return sub_category;
        }
    }
    public class User{
        public int id;
        public String name;
        public String email;
        public String phone_code;
        public String phone;
        public String vat_number;
        public String image;
        public Date created_at;
        public Date updated_at;
        public int status;
        public Object date;
        public Object admin_id;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone_code() {
            return phone_code;
        }

        public String getPhone() {
            return phone;
        }

        public String getVat_number() {
            return vat_number;
        }

        public String getImage() {
            return image;
        }

        public Date getCreated_at() {
            return created_at;
        }

        public Date getUpdated_at() {
            return updated_at;
        }

        public int getStatus() {
            return status;
        }

        public Object getDate() {
            return date;
        }

        public Object getAdmin_id() {
            return admin_id;
        }
    }

}
