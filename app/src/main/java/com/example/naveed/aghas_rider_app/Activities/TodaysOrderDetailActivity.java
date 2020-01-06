package com.example.naveed.aghas_rider_app.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.naveed.aghas_rider_app.Adapter.MyOrderDetailAdapter;
import com.example.naveed.aghas_rider_app.Base.BaseActivity;
import com.example.naveed.aghas_rider_app.Helpers.Constants;
import com.example.naveed.aghas_rider_app.Helpers.GeneralCallBack;
import com.example.naveed.aghas_rider_app.Models.GResponse;
import com.example.naveed.aghas_rider_app.Models.Order;
import com.example.naveed.aghas_rider_app.Models.OrderItems;
import com.example.naveed.aghas_rider_app.Network.ApiClient;
import com.example.naveed.aghas_rider_app.Network.IApiCaller;
import com.example.naveed.aghas_rider_app.Network.RestClient;
import com.example.naveed.aghas_rider_app.R;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodaysOrderDetailActivity extends BaseActivity implements View.OnClickListener{

    TextView txtOrderId, txtCustomerName, txtAddress, txtTotal, txtTotalPrice, txtOrderDate, txtDeliveryDate,txt_status;
    Button btnHome,btn_active;
    public static int OrderId;

    private List<OrderItems> myOrderList = new ArrayList<>();
    private RecyclerView recyclerViewOrderItems;
    private MyOrderDetailAdapter myOrderDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_or_schedule_order_detail);

        txtOrderId = (TextView) findViewById(R.id.txt_orderid);
        txtCustomerName = (TextView) findViewById(R.id.txt_customername);
        txtAddress = (TextView) findViewById(R.id.txt_address);
        txtOrderDate = (TextView) findViewById(R.id.txt_orderdate);
        txtDeliveryDate = (TextView) findViewById(R.id.txt_deliverydate);
        txtTotal = (TextView) findViewById(R.id.txt_total);
        txtTotalPrice = (TextView) findViewById(R.id.txt_totalprice);
        txt_status= (TextView) findViewById(R.id.txt_status);
        btnHome = (Button) findViewById(R.id.btn_home);
        btn_active= (Button) findViewById(R.id.btn_active);

        recyclerViewOrderItems = (RecyclerView) findViewById(R.id.recyclerViewOrderItems);
        myOrderDetailAdapter = new MyOrderDetailAdapter(myOrderList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewOrderItems.setLayoutManager(mLayoutManager);
        recyclerViewOrderItems.setItemAnimator(new DefaultItemAnimator());
        recyclerViewOrderItems.setAdapter(myOrderDetailAdapter);

        btnHome.setOnClickListener(this);
        btn_active.setOnClickListener(this);
        GetOrder(OrderId);
    }

    private void GetOrder(int orderid){
        showProgress();
        try {
            IApiCaller callerResponse = ApiClient.createService(IApiCaller.class);
            Call<Order> response = callerResponse.GetOrderDetail(orderid);

            response.enqueue(new Callback<Order>() {
                @Override
                public void onResponse(Call<Order> call, Response<Order> response) {
                    Order obj = response.body();

                    Gson gson = new Gson();
                    String Reslog= gson.toJson(response);
                    Log.d(Constants.TAG, Reslog);
                    if(obj == null){
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            String err = jObjError.getString("error_description").toString();
                            Log.d("Error", err);
                            //Toast.makeText(MyOrderActivity.this, err, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());
                            //Toast.makeText(MyOrderActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            hideProgress();
                        }
                    }else{
                        Order.Value list = obj.getValue();

                        String orderid = list.getId().toString();
                        String customername = list.getCustomerName();
                        String address = list.getAddress();
                        String total = list.getTotal().toString();
                        String orderdate = list.getOrderTime();
                        String deliverydate = list.getDeliveryDate();

                        txtOrderId.setText(orderid);
                        txtCustomerName.setText(customername);
                        txtAddress.setText(address);
                        txtTotal.setText(total);
                        txtTotalPrice.setText(total);
                        txtOrderDate.setText(orderdate);
                        txtDeliveryDate.setText(deliverydate);
                        txt_status.setText(list.getOrderStatus());
                        List<Order.OrderItem> orderitems = list.getOrderItem();

                        for (Order.OrderItem itemsList: orderitems){

                            int ordid = itemsList.getOrderId();
                            int itmid = itemsList.getItemId();
                            int quantity = itemsList.getQuantity();
                            String itemname = itemsList.getItemName();

                            OrderItems o = new OrderItems(ordid, itmid, itemname, quantity);
                            myOrderList.add(o);
                        }
                    }
                    myOrderDetailAdapter.notifyDataSetChanged();
                    hideProgress();
                }
                @Override
                public void onFailure(Call<Order> call, Throwable t) {
                    //Toast.makeText(MyOrderActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                Log.d("ApiError",t.getMessage());
                    hideProgress();
                }
            });

        }catch (Exception e){
            Log.d("error",e.getMessage());
            //Toast.makeText(MyOrderActivity.this, "Email or password is not correct", Toast.LENGTH_SHORT).show();
            hideProgress();
        }
    }


    public  void makeOrderActive(){


        showProgress();
        Log.d("test","intest");
        RestClient.getAuthAdapterToekn(tokenHelper.GetToken()).MakeOrderActive(OrderId).enqueue(new GeneralCallBack<GResponse>(this) {
            @Override
            public void onSuccess(GResponse response) {
                Gson gson = new Gson();
                String Reslog= gson.toJson(response);
                Log.d(Constants.TAG, Reslog);
                hideProgress();

                if (!response.getIserror()) {

                    OpenActivity(CurrentOrderActivity.class);


                    //getFproducts();

                }
                else{


                    AlertDialog alertDialog = new AlertDialog.Builder(TodaysOrderDetailActivity.this).create();
                    alertDialog.setTitle("");
                    alertDialog.setMessage(response.getMessage());
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();



                }

            }

            @Override
            public void onFailure(Throwable throwable) {
                //onFailure implementation would be in GeneralCallBack class
                hideProgress();
                Log.d("test",throwable.getMessage());

            }



        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_home:
                OpenActivity(CurrentOrderActivity.class);
                break;

            case R.id.btn_active:
                makeOrderActive();
                break;
        }
    }
}
