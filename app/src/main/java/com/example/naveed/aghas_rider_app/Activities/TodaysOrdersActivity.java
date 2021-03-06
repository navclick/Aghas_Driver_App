package com.example.naveed.aghas_rider_app.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.naveed.aghas_rider_app.Adapter.TodaysOrderAdapter;
import com.example.naveed.aghas_rider_app.Base.BaseActivity;
import com.example.naveed.aghas_rider_app.Helpers.Constants;
import com.example.naveed.aghas_rider_app.Listeners.RecyclerTouchListener;
import com.example.naveed.aghas_rider_app.Models.Order;
import com.example.naveed.aghas_rider_app.Models.OrderList;
import com.example.naveed.aghas_rider_app.Models.OrderListUp;
import com.example.naveed.aghas_rider_app.Models.OrderVM;
import com.example.naveed.aghas_rider_app.Network.ApiClient;
import com.example.naveed.aghas_rider_app.Network.IApiCaller;
import com.example.naveed.aghas_rider_app.R;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodaysOrdersActivity extends BaseActivity implements View.OnClickListener{

    //Declarations

    private List<OrderVM> orderList = new ArrayList<>();
    public String TokenString;
    private RecyclerView recyclerViewTodaysOrders;
    private TodaysOrderAdapter todaysOrderAdapter;
    Button btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_order);

        TokenString = tokenHelper.GetToken();
        if(TokenString == null)
            OpenActivity(LoginActivity.class);


        btnHome = (Button) findViewById(R.id.btn_home);

        btnHome.setOnClickListener(this);

        recyclerViewTodaysOrders = (RecyclerView) findViewById(R.id.recyclerViewTodaysOrders);
        todaysOrderAdapter = new TodaysOrderAdapter(orderList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewTodaysOrders.setLayoutManager(mLayoutManager);
        recyclerViewTodaysOrders.setItemAnimator(new DefaultItemAnimator());
        recyclerViewTodaysOrders.setAdapter(todaysOrderAdapter);

        recyclerViewTodaysOrders.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerViewTodaysOrders, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                OrderVM order = orderList.get(position);
                Gson gson = new Gson();
                String Reslog= gson.toJson(order);
                Log.d(Constants.TAG, Reslog);

                if(order.getOrderId() > 0){
                    TodaysOrderDetailActivity.OrderId = order.getOrderId();
                    Intent intent = new Intent(TodaysOrdersActivity.this, TodaysOrderDetailActivity.class);
                    startActivity(intent);
                }else{
                    OpenActivity(CurrentOrderActivity.class);
                }

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        GetTodaysOrders();
    }

    private void GetTodaysOrders(){
        showProgress();
        try {
            String token = "Bearer " + TokenString;
            IApiCaller callerResponse = ApiClient.createService(IApiCaller.class, token);
            Call<OrderListUp> response = callerResponse.GetTodaysOrders();

            response.enqueue(new Callback<OrderListUp>() {
                @Override
                public void onResponse(Call<OrderListUp> call, Response<OrderListUp> response) {
                    OrderListUp obj = response.body();

                    Gson gson = new Gson();
                    String Reslog= gson.toJson(response);
                    Log.d(Constants.TAG, Reslog);
                    if(obj == null){
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            String err = jObjError.getString("error_description").toString();
                            Log.d("Error", err);
                            //Toast.makeText(MyOrderActivity.this, err, Toast.LENGTH_SHORT).show();
                            hideProgress();
                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());
                            //Toast.makeText(MyOrderActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            hideProgress();
                        }
                    }else{
                        List<OrderListUp.Value> orderlist = obj.getValue();

                        for (OrderListUp.Value itemsList: orderlist){

                            int orderid = itemsList.getId();
                            int total = itemsList.getTotal();
                            String customername = itemsList.getCustomerName();
                            String address = itemsList.getAddress();

                            OrderVM ord = new OrderVM(orderid, total, customername, address,itemsList.getOrderTime());
                            orderList.add(ord);
                        }
                    }
                    todaysOrderAdapter.notifyDataSetChanged();
                    hideProgress();
                }
                @Override
                public void onFailure(Call<OrderListUp> call, Throwable t) {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_home:
                OpenActivity(CurrentOrderActivity.class);
                break;


        }
    }
}
