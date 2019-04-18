package com.example.naveed.aghas_rider_app.Network;

import com.example.naveed.aghas_rider_app.Models.Order;
import com.example.naveed.aghas_rider_app.Models.Token;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IApiCaller {
    @FormUrlEncoded
    @POST(EndPoints.LOGIN)
    Call<Token> GetToken(@Field("username") String username,
                         @Field("password") String password,
                         @Field("grant_type") String grant_type);


    @GET(EndPoints.CURRENTORDER)
    Call<Order> GetCurrentOrder();

    @GET(EndPoints.ORDERDETAIL)
    Call<Order> GetOrderDetail(@Query("id") int id);
}
