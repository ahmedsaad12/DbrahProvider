package com.apps.dbrah_Provider.services;


import com.apps.dbrah_Provider.model.CategoryDataModel;
import com.apps.dbrah_Provider.model.EditProductModel;
import com.apps.dbrah_Provider.model.NotificationDataModel;
import com.apps.dbrah_Provider.model.OrderDataModel;
import com.apps.dbrah_Provider.model.PlaceGeocodeData;
import com.apps.dbrah_Provider.model.ProductDataModel;
import com.apps.dbrah_Provider.model.RecentProductDataModel;
import com.apps.dbrah_Provider.model.ReviewDataModel;
import com.apps.dbrah_Provider.model.SingleOrderDataModel;
import com.apps.dbrah_Provider.model.StatisticsDataModel;
import com.apps.dbrah_Provider.model.SettingModel;
import com.apps.dbrah_Provider.model.StatusResponse;
import com.apps.dbrah_Provider.model.UserModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Service {

    @GET("geocode/json")
    Single<Response<PlaceGeocodeData>> getGeoData(@Query(value = "latlng") String latlng,
                                                  @Query(value = "language") String language,
                                                  @Query(value = "key") String key);


    @FormUrlEncoded
    @POST("api/provider/login")
    Single<Response<UserModel>> login(@Field("phone_code") String phone_code,
                                      @Field("phone") String phone,
                                      @Field("email") String email,
                                      @Field("password") String password,
                                      @Field("type") String type);


    @Multipart
    @POST("api/provider/register")
    Single<Response<UserModel>> signUp(@Part("phone") RequestBody phone,
                                       @Part("phone_code") RequestBody phone_code,
                                       @Part("email") RequestBody email,
                                       @Part("name") RequestBody name,
                                       @Part("vat_number") RequestBody vat_number,
                                       @Part("password") RequestBody password,
                                       @Part List<RequestBody> categories,
                                       @Part List<MultipartBody.Part> commercial_records,
                                       @Part MultipartBody.Part image);

    @Multipart
    @POST("api/provider/update_profile")
    Single<Response<UserModel>> update(@Part("provider_id") RequestBody provider_id,
                                       @Part("phone") RequestBody phone,
                                       @Part("phone_code") RequestBody phone_code,
                                       @Part("email") RequestBody email,
                                       @Part("name") RequestBody name,
                                       @Part("vat_number") RequestBody vat_number,
                                       @Part("password") RequestBody password,
                                       @Part List<RequestBody> categories,
                                       @Part MultipartBody.Part image);


    @GET("api/main_categories")
    Single<Response<CategoryDataModel>> getCategory();

    @GET("api/sub_categories")
    Single<Response<CategoryDataModel>> getSubCategory(@Query("category_id") String category_id);

    @GET("api/provider/control_products")
    Single<Response<RecentProductDataModel>> controlProducts(@Query("provider_id") String provider_id,
                                                             @Query("category_id") String category_id,
                                                             @Query("sub_category_id") String sub_category_id);

    @POST("api/provider/edit_my_products")
    Single<Response<StatusResponse>> editProducts(@Body EditProductModel editProductModel);

    @Multipart
    @POST("api/provider/suggest_product")
    Single<Response<StatusResponse>> suggestProduct(@Part("provider_id") RequestBody provider_id,
                                                    @Part MultipartBody.Part image,
                                                    @Part("title") RequestBody title,
                                                    @Part("main_category_id") RequestBody main_category_id,
                                                    @Part("specifications") RequestBody specifications);


    @FormUrlEncoded
    @POST("api/logout")
    Single<Response<StatusResponse>> logout(@Header("AUTHORIZATION") String token,
                                            @Field("api_key") String api_key,
                                            @Field("phone_token") String phone_token


    );

    @FormUrlEncoded
    @POST("api/firebase-tokens")
    Single<Response<StatusResponse>> updateFirebasetoken(@Header("AUTHORIZATION") String token,
                                                         @Field("api_key") String api_key,
                                                         @Field("phone_token") String phone_token,
                                                         @Field("user_id") String user_id,
                                                         @Field("software_type") String software_type


    );


    @GET("api/setting")
    Single<Response<SettingModel>> getSetting();

    @FormUrlEncoded
    @POST("api/contact_us")
    Single<Response<StatusResponse>> contactUs(@Field("name") String name,
                                               @Field("email") String email,
                                               @Field("subject") String phone,
                                               @Field("message") String message


    );


    @GET("api/notifications")
    Single<Response<NotificationDataModel>> getNotifications(@Header("AUTHORIZATION") String token,
                                                             @Query(value = "api_key") String api_key,
                                                             @Query(value = "user_id") String user_id
    );

    @GET("api/provider/reviews")
    Single<Response<ReviewDataModel>> getReviews(@Query(value = "provider_id") String provider_id);

    @GET("api/provider/orders")
    Single<Response<OrderDataModel>> getOrders(@Query(value = "provider_id") String provider_id);

    @GET("api/provider/statistics")
    Single<Response<StatisticsDataModel>> getStatistics(@Query(value = "provider_id") String provider_id);

    @FormUrlEncoded
    @POST("api/provider/pin_order")
    Single<Response<StatusResponse>> PinOrder(@Field("order_id") String order_id,
                                              @Field("provider_id") String provider_id);

    @GET("api/provider/order_details")
    Single<Response<SingleOrderDataModel>> getOrderDetails(@Query("order_id") String order_id,
                                                           @Query("provider_id") String provider_id);

    @GET("api/provider/control_products")
    Single<Response<ProductDataModel>> getProduct(@Query("provider_id") String provider_id);
}