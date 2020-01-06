package com.example.naveed.aghas_rider_app.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
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
import com.example.naveed.aghas_rider_app.Models.Order;
import com.example.naveed.aghas_rider_app.Models.OrderItems;
import com.example.naveed.aghas_rider_app.Network.ApiClient;
import com.example.naveed.aghas_rider_app.Network.IApiCaller;
import com.example.naveed.aghas_rider_app.R;
import com.google.gson.Gson;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentOrderDetailActivity extends BaseActivity implements View.OnClickListener {

    //Declarations
    public String TokenString;
    TextView txtOrderId, txtCustomerName, txtAddress, txtTotal, txtOrderDate, txtDeliveryDate;
    Button btnChangeOrderStatus, btnHome, btn_call;

    private List<OrderItems> myOrderList = new ArrayList<>();
    private RecyclerView recyclerViewOrderItems;
    private MyOrderDetailAdapter myOrderDetailAdapter;
    private String CustomerCell = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_order_detail);

        // Implementations
        txtOrderId = (TextView) findViewById(R.id.txt_orderid);
        txtCustomerName = (TextView) findViewById(R.id.txt_customername);
        txtAddress = (TextView) findViewById(R.id.txt_address);
        txtOrderDate = (TextView) findViewById(R.id.txt_orderdate);
        txtDeliveryDate = (TextView) findViewById(R.id.txt_deliverydate);
        txtTotal = (TextView) findViewById(R.id.txt_total);
        btnChangeOrderStatus = (Button) findViewById(R.id.btn_changeorderstatus);
        btnHome = (Button) findViewById(R.id.btn_home);
        btn_call = (Button) findViewById(R.id.btn_call);
        // Events
        btnChangeOrderStatus.setOnClickListener(this);
        btnHome.setOnClickListener(this);
        btn_call.setOnClickListener(this);
        recyclerViewOrderItems = (RecyclerView) findViewById(R.id.recyclerViewOrderItems);
        myOrderDetailAdapter = new MyOrderDetailAdapter(myOrderList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewOrderItems.setLayoutManager(mLayoutManager);
        recyclerViewOrderItems.setItemAnimator(new DefaultItemAnimator());
        recyclerViewOrderItems.setAdapter(myOrderDetailAdapter);

        String orderid = getIntent().getStringExtra("id");
        if (orderid == null)
            OpenActivity(LoginActivity.class);
        else {
            if (Integer.valueOf(orderid) > 0) {
                txtOrderId.setText(orderid);
                GetCurrentOrder(Integer.parseInt(orderid));
            } else {
                OpenActivity(LoginActivity.class);
            }
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_settings:
                OpenActivity(LoginActivity.class);
                break;
            case R.id.btn_home:
                OpenActivity(CurrentOrderActivity.class);
                break;

            case R.id.btn_call:
                callCustomer();
                break;
            case R.id.btn_changeorderstatus:
                openActivity(StatusChangeActivity.class);
                break;


        }



    }

    private void callCustomer() {


        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + CustomerCell));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {


            new AlertDialog.Builder(this)
                    .setTitle("App")
                    .setMessage("Call permission not granted !")
                    .setPositiveButton(android.R.string.ok, null)
                    .show();



            return;
        }
        startActivity(intent);
    }


    private void GetCurrentOrder(int orderid){
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

                        txtOrderId.setText(orderid);
                        txtCustomerName.setText(customername);
                        txtAddress.setText(address);
                        txtTotal.setText(total);
                        txtOrderDate.setText(orderdate);

                        if(obj.getValue().getContactNo().toString().isEmpty() || obj.getValue().getContactNo().toString()==""){

                            btn_call.setVisibility(View.GONE);


                        }
                        else{
                            btn_call.setVisibility(View.VISIBLE);
                            CustomerCell=obj.getValue().getContactNo().toString();
                        }

                        List<Order.OrderItem> orderitems = list.getOrderItem();
                        //List<OrderItems> itms = new ArrayList<OrderItems>();

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
}
