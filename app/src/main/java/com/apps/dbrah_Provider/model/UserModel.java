package com.apps.dbrah_Provider.model;

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
        private List<CommercialRecords> commercial_records;
        private List<Categories> categories;

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

        public class CommercialRecords implements Serializable{
            private String provider_id;
            private String image;

            public String getProvider_id() {
                return provider_id;
            }

            public String getImage() {
                return image;
            }
        }
        public  class Categories implements Serializable{
            private String id;
            private String provider_id;
            private String category_id;
            private String created_at;
            private String updated_at;
            private Category category;

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

            public Category getCategory() {
                return category;
            }
            public class Category implements Serializable{
                private String id;
                private String title_ar;
                private String title_en;
                private String image;
                private String level;
                private String created_at;
                private String updated_at;

                public String getId() {
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

                public String getCreated_at() {
                    return created_at;
                }

                public String getUpdated_at() {
                    return updated_at;
                }
            }
        }
    }

}
