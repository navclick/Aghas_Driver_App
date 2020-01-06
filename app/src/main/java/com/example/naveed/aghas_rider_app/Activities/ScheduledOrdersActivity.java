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

import com.example.naveed.aghas_rider_app.Adapter.ScheduledOrderAdapter;
import com.example.naveed.aghas_rider_app.Adapter.TodaysOrderAdapter;
import com.example.naveed.aghas_rider_app.Base.BaseActivity;
import com.example.naveed.aghas_rider_app.Helpers.Constants;
import com.example.naveed.aghas_rider_app.Listeners.RecyclerTouchListener;
import com.example.naveed.aghas_rider_app.Models.OrderList;
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

public class ScheduledOrdersActivity extends BaseActivity implements View.OnClickListener{

    //Declarations

    private List<OrderVM> orderList = new ArrayList<>();
    public String TokenString;
    private RecyclerView recyclerViewScheduledOrders;
    private ScheduledOrderAdapter scheduledOrderAdapter;
    Button btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_order);

        TokenString = tokenHelper.GetToken();
        if(TokenString == null)
            OpenActivity(LoginActivity.class);


        btnHome = (Button) findViewById(R.id.btn_home);
        btnHome.setOnClickListener(this);

        recyclerViewScheduledOrders = (RecyclerView) findViewById(R.id.recyclerViewScheduledOrders);
        scheduledOrderAdapter = new ScheduledOrderAdapter(orderList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewScheduledOrders.setLayoutManager(mLayoutManager);
        recyclerViewScheduledOrders.setItemAnimator(new DefaultItemAnimator());
        recyclerViewScheduledOrders.setAdapter(scheduledOrderAdapter);

        recyclerViewScheduledOrders.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerViewScheduledOrders, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                OrderVM order = orderList.get(position);
                Gson gson = new Gson();
                String Reslog= gson.toJson(order);
                Log.d(Constants.TAG, Reslog);

                if(order.getOrderId() > 0){
                    ScheduledOrderDetailActivity.OrderId = order.getOrderId();
                    Intent intent = new Intent(ScheduledOrdersActivity.this, ScheduledOrderDetailActivity.class);
                    startActivity(intent);
                }else{
                    OpenActivity(CurrentOrderActivity.class);
                }

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        GetScheduledOrders();
    }

    private void GetScheduledOrders(){
        showProgress();
        try {
            String token = "Bearer " + TokenString;
            IApiCaller callerResponse = ApiClient.createService(IApiCaller.class, token);
            Call<OrderList> response = callerResponse.GetScheduledOrders();

            response.enqueue(new Callback<OrderList>() {
                @Override
                public void onResponse(Call<OrderList> call, Response<OrderList> response) {
                    OrderList obj = response.body();

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
                        List<OrderList.Value> orderlist = obj.getValue();

                        for (OrderList.Value itemsList: orderlist){

                            int orderid = itemsList.getId();
                            int total = itemsList.getTotal();
                            String customername = itemsList.getCustomerName();
                            String address = itemsList.getAddress();

                            OrderVM ord = new OrderVM(orderid, total, customername, address,itemsList.getOrderTime());
                            orderList.add(ord);
                        }
                    }
                    scheduledOrderAdapter.notifyDataSetChanged();
                    hideProgress();
                }
                @Override
                public void onFailure(Call<OrderList> call, Throwable t) {
                    //Toast.makeText(MyOrderActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    Log.d("ApiError",t.getMessage());
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
