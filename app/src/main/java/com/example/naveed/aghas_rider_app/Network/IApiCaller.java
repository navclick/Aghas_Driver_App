package com.example.naveed.aghas_rider_app.Network;

import com.example.naveed.aghas_rider_app.Models.DeviceInfoRequest;
import com.example.naveed.aghas_rider_app.Models.DeviceInfoResponse;
import com.example.naveed.aghas_rider_app.Models.GResponse;
import com.example.naveed.aghas_rider_app.Models.Order;
import com.example.naveed.aghas_rider_app.Models.OrderList;
import com.example.naveed.aghas_rider_app.Models.OrderListUp;
import com.example.naveed.aghas_rider_app.Models.Token;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
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

    @GET(EndPoints.TODAYSORDER)
    Call<OrderListUp> GetTodaysOrders();

    @GET(EndPoints.SCHEDULEDORDER)
    Call<OrderList> GetScheduledOrders();

    @GET(EndPoints.ORDERDETAIL)
    Call<Order> GetOrderDetail(@Query("id") int id);


    @GET(EndPoints.MAKEACTIVE)
    Call<GResponse> MakeOrderActive(@Query("id") int id);


    @GET(EndPoints.UPDATESTATUS)
    Call<GResponse> UpdateStatus(@Query("id") int id,@Query("status") int status,@Query("reason") String reason);

    @POST(EndPoints.UPDATEDEVICEINFO)
    Call<DeviceInfoResponse> updateDeviceInfo(@Body DeviceInfoRequest deviceInfo);
}
