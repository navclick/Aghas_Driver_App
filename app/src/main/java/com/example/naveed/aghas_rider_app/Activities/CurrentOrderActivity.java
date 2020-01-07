package com.example.naveed.aghas_rider_app.Activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.naveed.aghas_rider_app.BackGroundServices.LocationService;
import com.example.naveed.aghas_rider_app.Base.BaseActivity;
import com.example.naveed.aghas_rider_app.Helpers.Constants;
import com.example.naveed.aghas_rider_app.Helpers.GeneralCallBack;
import com.example.naveed.aghas_rider_app.Models.Order;
import com.example.naveed.aghas_rider_app.Network.ApiClient;
import com.example.naveed.aghas_rider_app.Network.IApiCaller;
import com.example.naveed.aghas_rider_app.Network.RestClient;
import com.example.naveed.aghas_rider_app.R;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentOrderActivity extends BaseActivity implements View.OnClickListener{

    //Declarations
    public String TokenString;
    TextView txtOrderId, txtCustomerName, txtAddress, txtTotal, txtSettings, txtLogOut;
    ImageView txtTodaysOrders, txtScheduledOrders;
    Button btnViewOrder;
    LinearLayout LL_orders,LL_no_orders;
    final private int  REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 200;

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
        LL_orders=(LinearLayout) findViewById(R.id.LL_orders);

        LL_no_orders= (LinearLayout) findViewById(R.id.LL_no_orders);


        // Listeners
        txtSettings.setOnClickListener(this);
        txtTodaysOrders.setOnClickListener(this);
        txtScheduledOrders.setOnClickListener(this);
        txtLogOut.setOnClickListener(this);
        btnViewOrder.setOnClickListener(this);
        GetPermissions();
        TokenString = tokenHelper.GetToken();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //  this.startForegroundService(new Intent(this, LocationService.class));
            startForegroundService(new Intent(this, LocationService.class));

        } else {
            startService(new Intent(this, LocationService.class));
        }
        if(TokenString == null)
            OpenActivity(LoginActivity.class);

        GetCurrentOrderFromServer();
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






    private void GetCurrentOrderFromServer(){


        showProgress();
        Log.d("test","intest");
        RestClient.getAuthAdapterToekn(tokenHelper.GetToken()).GetCurrentOrder().enqueue(new GeneralCallBack<Order>(this) {
            @Override
            public void onSuccess(Order response) {
                Gson gson = new Gson();
                String Reslog= gson.toJson(response);
                Log.d(Constants.TAG, Reslog);
                hideProgress();

                if (!response.getIserror()) {

                if(response.getCode() != 404){

                    LL_orders.setVisibility(View.VISIBLE);
                            LL_no_orders.setVisibility(View.GONE);
                    Order.Value list = response.getValue();
                    String orderid = list.getId().toString();
                    String customername = list.getCustomerName();
                    String address = list.getAddress();
                    String total = list.getTotal().toString();

                    txtOrderId.setText(orderid);
                    txtCustomerName.setText(customername);
                    txtAddress.setText(address);
                    txtTotal.setText(total);


                }
                else{

                    LL_orders.setVisibility(View.GONE);
                    LL_no_orders.setVisibility(View.VISIBLE);

                }

                    //getFproducts();

                }
                else{



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
                            txtTotal.setText(total);

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




    @TargetApi(Build.VERSION_CODES.M)
    private void GetPermissions(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED  || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)  {

            if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.LOLLIPOP){
                List<String> permissionsNeeded = new ArrayList<String>();

                final List<String> permissionsList = new ArrayList<String>();
                if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION))
                    permissionsNeeded.add("GPS");
                if (!addPermission(permissionsList, Manifest.permission.ACCESS_COARSE_LOCATION))
                    permissionsNeeded.add("Location");

                if (!addPermission(permissionsList, Manifest.permission.CAMERA))
                    permissionsNeeded.add("Camera");

                if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                    permissionsNeeded.add("Write_External_Storage");
                if (!addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE))
                    permissionsNeeded.add("Read_External_Storage");

                if (!addPermission(permissionsList, Manifest.permission.CALL_PHONE))
                    permissionsNeeded.add("Call_phone");


                if (permissionsList.size() > 0) {
                    if (permissionsNeeded.size() > 0) {


                    }
                    requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                            REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                    return;
                }

            }




            return;
        }

        else{


        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
            {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_COARSE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CALL_PHONE, PackageManager.PERMISSION_GRANTED);

                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED


                        ) {
                    // All Permissions Granted

                    // setMapForV6();

                } else {
                    // Permission Denied
                    // Toast.makeText(this, "Some Permission is Denied", Toast.LENGTH_SHORT)
                    //       .show();

                    showMessageDailog(getString(R.string.app_name), Constants.MESSAGE_REQUESTED_PERMISSION_DENIED);

                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}