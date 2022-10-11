package com.app.dbrah_Provider.model;

import java.io.Serializable;
import java.util.List;
import java.util.stream.DoubleStream;

public class UserModel extends StatusResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public static class Data implements Serializable {
        private String id;
        private String name;
        private String fake_name;
        private String email;
        private String phone_code;
        private String phone;
        private String password;
        private String vat_number;
        private String image;
        private String commercial_record;
        private String created_at;
        private String updated_at;
        private String rate;
        private String provider_code;
        private List<CommercialRecords> commercial_records;
        private List<Categories> categories;
        private static String firebase_token;

        public String getFirebase_token() {
            return firebase_token;
        }

        public void setFirebase_token(String firebase_token) {
            this.firebase_token = firebase_token;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getFake_name() {
            return fake_name;
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

        public String getPassword() {
            return password;
        }

        public String getVat_number() {
            return vat_number;
        }

        public String getImage() {
            return image;
        }

        public String getCommercial_record() {
            return commercial_record;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getRate() {
            return rate;
        }

        public List<CommercialRecords> getCommercial_records() {
            return commercial_records;
        }

        public List<Categories> getCategories() {
            return categories;
        }

        public String getProvider_code() {
            return provider_code;
        }

        public class Categories implements Serializable {
            private String id;
            private String provider_id;
            private String category_id;
            private String created_at;
            private String updated_at;
            private CategoryModel category;

            public String getId() {
                return id;
            }

            public String getProvider_id() {
                return provider_id;
            }

            public String getCategory_id() {
                return category_id;
            }

            public String getCreated_at() {
                return created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public CategoryModel getCategory() {
                return category;
            }
        }

        public class CommercialRecords implements Serializable {
            private String provider_id;
            private String image;

            public String getProvider_id() {
                return provider_id;
            }

            public String getImage() {
                return image;
            }
        }
    }

}
