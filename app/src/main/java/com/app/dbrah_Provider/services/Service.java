package com.app.dbrah_Provider.services;


import com.app.dbrah_Provider.model.AddOFFerDataModel;
import com.app.dbrah_Provider.model.BankDataModel;
import com.app.dbrah_Provider.model.CategoryDataModel;
import com.app.dbrah_Provider.model.EditProductModel;
import com.app.dbrah_Provider.model.MessagesDataModel;
import com.app.dbrah_Provider.model.NationalitiesModel;
import com.app.dbrah_Provider.model.NotificationDataModel;
import com.app.dbrah_Provider.model.OrderDataModel;
import com.app.dbrah_Provider.model.PlaceGeocodeData;
import com.app.dbrah_Provider.model.PlaceMapDetailsData;
import com.app.dbrah_Provider.model.PreviousOfferDataModel;
import com.app.dbrah_Provider.model.ProductDataModel;
import com.app.dbrah_Provider.model.RecentProductDataModel;
import com.app.dbrah_Provider.model.ReviewDataModel;
import com.app.dbrah_Provider.model.SettingDataModel;
import com.app.dbrah_Provider.model.SingleMessageModel;
import com.app.dbrah_Provider.model.SingleOrderDataModel;
import com.app.dbrah_Provider.model.StatisticsDataModel;
import com.app.dbrah_Provider.model.StatusResponse;
import com.app.dbrah_Provider.model.TimeDataModel;
import com.app.dbrah_Provider.model.UserModel;

import java.util.List;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
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
    Call<PlaceGeocodeData> getGeoData(@Query(value = "latlng") String latlng,
                                      @Query(value = "language") String language,
                                      @Query(value = "key") String key);

    @FormUrlEncoded
    @POST("api/provider/login")
    Single<Response<UserModel>> login(@Field("phone_code") String phone_code,
                                      @Field("phone") String phone,
                                      @Field("email") String email,
                                      @Field("password") String password,
                                      @Field("type") String type);

    @FormUrlEncoded
    @POST("api/provider/confirm-password")
    Single<Response<StatusResponse>> newPassword(@Field("phone_code") String phone_code,
                                                 @Field("phone") String phone,
                                                 @Field("email") String email,
                                                 @Field("type") String type,
                                                 @Field("password") String password

                                                 );

    @FormUrlEncoded
    @POST("api/provider/check-provider-exists")
    Single<Response<StatusResponse>> checkUser(@Field("phone_code") String phone_code,
                                               @Field("phone") String phone,
                                               @Field("email") String email,
                                               @Field("type") String type);

    @FormUrlEncoded
    @POST("api/auth/check/code")
    Single<Response<StatusResponse>> checkCode(@Field("email") String email,
                                               @Field("code") String code
    );


    @Multipart
    @POST("api/provider/register")
    Single<Response<UserModel>> signUp(@Part("phone") RequestBody phone,
                                       @Part("phone_code") RequestBody phone_code,
                                       @Part("email") RequestBody email,
                                       @Part("name") RequestBody name,
                                       @Part("vat_number") RequestBody vat_number,
                                       @Part("password") RequestBody password,
                                       @Part("category_ids[]") List<RequestBody> category_ids,
                                       @Part("nationality_id") RequestBody nationality_id,
                                       @Part("town_id") RequestBody town_id,
                                       @Part("latitude") RequestBody latitude,
                                       @Part("longitude") RequestBody longitude,
                                       @Part List<MultipartBody.Part> commercial_records_images,
                                       @Part MultipartBody.Part image);

    @Multipart
    @POST("api/provider/update_profile")
    Single<Response<UserModel>> update(@Part("provider_id") RequestBody provider_id,
                                       @Part("phone") RequestBody phone,
                                       @Part("phone_code") RequestBody phone_code,
                                       @Part("email") RequestBody email,
                                       @Part("password") RequestBody password,
                                       @Part MultipartBody.Part image,
                                       @Part("category_ids[]") List<RequestBody> category_ids
    );


    @GET("api/main_categories")
    Single<Response<CategoryDataModel>> getCategory();

    @GET("api/sub_categories")
    Single<Response<CategoryDataModel>> getSubCategory(@Query("category_id") String category_id);

    @GET("api/sub_sub_categories")
    Single<Response<CategoryDataModel>> getSubSubCategory(@Query("sub_category_id") String sub_category_id);

    @GET("api/provider/control_products")
    Single<Response<RecentProductDataModel>> controlProducts(@Query("provider_id") String provider_id,
                                                             @Query("category_id") String category_id,
                                                             @Query("sub_category_id") String sub_category_id,
                                                             @Query("sub_sub_category_id") String sub_sub_category_id);

    @POST("api/provider/edit_my_products_not_have")
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
    Single<Response<SettingDataModel>> getSetting();

    @FormUrlEncoded
    @POST("api/contact_us")
    Single<Response<StatusResponse>> contactUs(@Field("name") String name,
                                               @Field("email") String email,
                                               @Field("subject") String phone,
                                               @Field("message") String message


    );


    @GET("api/getNotifications")
    Single<Response<NotificationDataModel>> getNotifications(@Query(value = "provider_id") String provider_id
    );
    @GET("api/provider/rejectOffers")
    Single<Response<PreviousOfferDataModel>> getPreviousOffers(@Query(value = "provider_id") String provider_id,
                                                               @Query(value = "order_id") int order_id

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

    @FormUrlEncoded
    @POST("api/provider/hide_order")
    Single<Response<StatusResponse>> hideOrder(@Field("order_id") String order_id,
                                               @Field("provider_id") String provider_id);

    @GET("api/provider/order_details")
    Single<Response<SingleOrderDataModel>> getOrderDetails(@Query("order_id") String order_id,
                                                           @Query("provider_id") String provider_id);

    @GET("api/provider/control_products")
    Single<Response<ProductDataModel>> getProduct(@Query("provider_id") String provider_id);

    @POST("api/provider/add_offer")
    Single<Response<StatusResponse>> addOffer(@Body AddOFFerDataModel addOFFerDataModel);


    @FormUrlEncoded
    @POST("api/provider/logout")
    Single<Response<StatusResponse>> logout(@Field("provider_id") String provider_id,
                                            @Field("token") String token
    );

    @FormUrlEncoded
    @POST("api/storeToken")
    Single<Response<StatusResponse>> updateFirebasetoken(@Field("provider_id") String provider_id,
                                                         @Field("token") String token,
                                                         @Field("type") String type
    );

    @GET("api/getChat")
    Single<Response<MessagesDataModel>> getChatMessages(@Query("order_id") String order_id,
                                                        @Query("representative_id") String representative_id,
                                                        @Query("user_id") String user_id,
                                                        @Query("provider_id") String provider_id);

    @Multipart
    @POST("api/storeMessage")
    Single<Response<SingleMessageModel>> sendMessages(@Part("order_id") RequestBody order_id,
                                                      @Part("type") RequestBody type,
                                                      @Part("from_type") RequestBody from,
                                                      @Part("message") RequestBody message,
                                                      @Part("representative_id") RequestBody representative_id,
                                                      @Part("user_id") RequestBody user_id,
                                                      @Part("provider_id") RequestBody provider_id,
                                                      @Part MultipartBody.Part image
    );

    @FormUrlEncoded
    @POST("api/updateOrderStatus")
    Single<Response<StatusResponse>> changeOrderStatus(@Field("order_id") String order_id,
                                                       @Field("status") String status);

    @GET("api/setting")
    Single<Response<SettingDataModel>> getSettings();

    @GET("api/representative/nationalities")
    Single<Response<NationalitiesModel>> getNationalities();

    @GET("place/findplacefromtext/json")
    Call<PlaceMapDetailsData> searchOnMap(@Query(value = "inputtype") String inputtype,
                                          @Query(value = "input") String input,
                                          @Query(value = "fields") String fields,
                                          @Query(value = "language") String language,
                                          @Query(value = "key") String key
    );

    @GET("api/delivery_times")
    Single<Response<TimeDataModel>> getTime();

    @FormUrlEncoded
    @POST("api/provider/store")
    Single<Response<StatusResponse>> addBankAccount(@Field("provider_id") String provider_id,
                                                    @Field("account_name") String account_name,
                                                    @Field("account_holder_name") String account_holder_name,
                                                    @Field("iban") String iban


    );

    @FormUrlEncoded
    @POST("api/provider/update")
    Single<Response<StatusResponse>> editBankAccount(@Field("bank_account_id") String bank_account_id,
                                                     @Field("provider_id") String provider_id,
                                                     @Field("account_name") String account_name,
                                                     @Field("account_holder_name") String account_holder_name,
                                                     @Field("iban") String iban


    );

    @FormUrlEncoded
    @POST("api/provider/delete")
    Single<Response<StatusResponse>> deleteBankAccount(@Field("bank_account_id") String bank_account_id,
                                                       @Field("provider_id") String provider_id


    );

    @GET("api/provider/list")
    Single<Response<BankDataModel>> getBanks(@Query(value = "provider_id") String provider_id
    );

}