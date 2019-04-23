package com.example.naveed.aghas_rider_app.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.naveed.aghas_rider_app.Base.BaseActivity;
import com.example.naveed.aghas_rider_app.Helpers.Constants;
import com.example.naveed.aghas_rider_app.Models.Order;
import com.example.naveed.aghas_rider_app.Network.ApiClient;
import com.example.naveed.aghas_rider_app.Network.IApiCaller;
import com.example.naveed.aghas_rider_app.R;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentOrderActivity extends BaseActivity implements View.OnClickListener{

    //Declarations
    public String TokenString;
    TextView txtOrderId, txtCustomerName, txtAddress, txtTotal, txtSettings, txtLogOut;
    ImageView txtTodaysOrders, txtScheduledOrders;
    Button btnViewOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Implementations
        txtOrderId = (TextView) findViewById(R.id.txt_orderid);
        txtCustomerName = (TextView) findViewById(R.id.txt_customername);
        txtAddress = (TextView) findViewById(R.id.txt_address);
        txtSettings = (TextView) findViewById(R.id.txt_settings);
        txtTotal = (TextView) findViewById(R.id.txt_total);
        txtTodaysOrders = (ImageView) findViewById(R.id.txt_todaysorders);
        txtScheduledOrders = (ImageView) findViewById(R.id.txt_scheduledorders);
        txtLogOut = (TextView) findViewById(R.id.txt_logout);
        btnViewOrder = (Button) findViewById(R.id.btn_vieworder);

        // Listeners
        txtSettings.setOnClickListener(this);
        txtTodaysOrders.setOnClickListener(this);
        txtScheduledOrders.setOnClickListener(this);
        txtLogOut.setOnClickListener(this);
        btnViewOrder.setOnClickListener(this);

        TokenString = tokenHelper.GetToken();
        if(TokenString == null)
            OpenActivity(LoginActivity.class);

        GetCurrentOrder();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_settings:
                OpenActivity(SettingActivity.class);

            case R.id.btn_vieworder:
                OpenActivity(CurrentOrderDetailActivity.class, txtOrderId.getText().toString());
                break;

            case R.id.txt_todaysorders:
                OpenActivity(TodaysOrdersActivity.class);
                break;

            case R.id.txt_scheduledorders:
                OpenActivity(ScheduledOrdersActivity.class);
                break;

            case R.id.txt_logout:
                Logout();
                break;
        }
    }

    private void GetCurrentOrder(){
        showProgress();
        try {
            String token = "Bearer " + TokenString;
            IApiCaller callerResponse = ApiClient.createService(IApiCaller.class, token);
            Call<Order> response = callerResponse.GetCurrentOrder();

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
                            hideProgress();
                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());
                            //Toast.makeText(MyOrderActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            hideProgress();
                        }
                    }else{
                            Order.Value list = obj.getValue();

                        if(list != null){
                            String orderid = list.getId().toString();
                            String customername = list.getCustomerName();
                            String address = list.getAddress();
                            String total = list.getTotal().toString();

                            txtOrderId.setText(orderid);
                            txtCustomerName.setText(customername);
                            txtAddress.setText(address);
                            txtTotal.setText("PKR" + total);

                            hideProgress();
                        }
                    }
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

    private void Logout(){
        tokenHelper.removeALL();
        // this.deleteDatabase(Constants.DATABASE_NAME);
        startActivity(this,LoginActivity.class);
    }
}