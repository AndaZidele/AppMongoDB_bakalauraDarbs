package com.example.appmongodb.API;

import com.example.appmongodb.Adapters.Cart;
import com.example.appmongodb.Adapters.Order;
import com.example.appmongodb.Adapters.Product;
import com.example.appmongodb.Adapters.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserAPI {


//    @POST("register")
//    @FormUrlEncoded
//    Observable<String> addToCart(@Field("email") String email,
//                                    @Field("name") String name,
//                                    @Field("password") String password);


    @POST("register")
    @FormUrlEncoded
    Observable<String> registerUser(@Field("email") String email,
                                    @Field("name") String name,
                                    @Field("password") String password,
                                    @Field("phone") String phone);

    @POST("registerStore")
    @FormUrlEncoded
    Observable<String> registerStore(@Field("store") String store);


    @POST("pievienoProduktusKaaFirebase")
    @FormUrlEncoded
    Observable<String> registerUserProducts(@Field("phone") String phone);


    @POST("login")
    @FormUrlEncoded
    Observable<String> loginUser(@Field("email") String email,
                                 @Field("password") String password);





    @GET("getProductsFromStore")
    Observable<List<Product>> getProductList();

    @GET("getUsersFromStore")
    Observable<List<User>> getUserList();

    @GET("getUserCart")
    Observable<List<Cart>> getUserCart();

    @GET("getUserOrder")
    Observable<List<Order>> getUserOrder();

    @POST("getThisUserCart")
    @FormUrlEncoded
    Observable<String> getThisUserCart(@Field("user") String email);

//    @GET("showUserCart")
//    Observable<List<Product>> showUsersCart();

    @POST("incProduct")//updateProductForCart")
    @FormUrlEncoded
    Observable<String> incProduct(@Field("product") String product,
                                  @Field("user") String user,
                                  @Field("amount") String amount);
    @POST("decProduct")//updateProductForCart")
    @FormUrlEncoded
    Observable<String> decProduct(@Field("product") String product,
                                  @Field("user") String user,
                                  @Field("amount") String amount);

    @POST("deleteProduct")
    @FormUrlEncoded
    Observable<String> deleteProduct(@Field("product") String product,
                                     @Field("user") String user);

    @POST("deleteUser")
    @FormUrlEncoded
    Observable<String> deleteUser(@Field("user") String user);

    @POST("addProductAgain")
    @FormUrlEncoded
    Observable<String> addToCart(@Field("id") String pId,
                                 @Field("user") String uId,
                                 @Field("amount") String am,
                                 @Field("name") String name,
                                 @Field("price") String pr);


    @POST("makeOrder")
    @FormUrlEncoded
    Observable<String> registerOrder(@Field("user_id") String user_id,
                                    @Field("user_name") String user_name,
                                    @Field("email") String email,
                                    @Field("phone") String phone,
                                    @Field("address") String address,
                                     @Field("prod_names") String prod_names,
                                     @Field("prod_ids") String prod_ids,
                                     @Field("price") String price);
}
