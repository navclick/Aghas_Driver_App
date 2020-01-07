package com.example.naveed.aghas_rider_app.Activities;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.naveed.aghas_rider_app.BackGroundServices.LocationService;
import com.example.naveed.aghas_rider_app.Base.BaseActivity;
import com.example.naveed.aghas_rider_app.Helpers.Constants;
import com.example.naveed.aghas_rider_app.Helpers.GeneralCallBack;
import com.example.naveed.aghas_rider_app.Helpers.GeneralHelper;
import com.example.naveed.aghas_rider_app.Models.DeviceInfoRequest;
import com.example.naveed.aghas_rider_app.Models.DeviceInfoResponse;
import com.example.naveed.aghas_rider_app.Models.Token;
import com.example.naveed.aghas_rider_app.Network.ApiClient;
import com.example.naveed.aghas_rider_app.Network.IApiCaller;
import com.example.naveed.aghas_rider_app.Network.RestClient;
import com.example.naveed.aghas_rider_app.R;
import com.example.naveed.aghas_rider_app.Utilities.ValidationUtility;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity  implements View.OnClickListener {

    // Declarations
    Button btnSignIn, btnSignUp;
    EditText txtEmail, txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        // Implementations
        btnSignIn = (Button) findViewById(R.id.btn_signin);
        btnSignUp = (Button) findViewById(R.id.btn_signup);
        txtEmail = (EditText) findViewById(R.id.txt_email);
        txtPassword = (EditText) findViewById(R.id.txt_password);

        // Listeners
        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_signup:
                OpenActivity(RegisterActivity.class);
                break;

            case R.id.btn_signin:
                if(isValidate()){
                    SignIn();
                }else{
                    break;
                }
                break;
        }
    }

    private void SignIn(){
        try {
            showProgress();
            IApiCaller token = ApiClient.createService(IApiCaller.class);
            String username = txtEmail.getText().toString();
            String password = txtPassword.getText().toString();
            Call<Token> response = token.GetToken(username,password,"password");

            response.enqueue(new Callback<Token>() {
                @Override
                public void onResponse(Call<Token> call, Response<Token> response) {

                    Gson gson = new Gson();
                    String Reslog= gson.toJson(response);
                    Log.d("testme", Reslog);
                    Token objToken = response.body();
                    if(objToken == null){
                        try {
                            hideProgress();
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            String err = jObjError.getString("error_description").toString();
                            Log.d("Error", err);
                            Toast.makeText(LoginActivity.this, err, Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());
                            Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            hideProgress();
                        }
                    }else{
                        String access_token = objToken.getAccessToken();
                        boolean isTokenSet = tokenHelper.SetToken(objToken.getAccessToken());
                        if(isTokenSet == true){
                            // TODO: Open main screen if token is set successfully
                            OpenActivity(CurrentOrderActivity.class);

                            addUpdateDeviceInfoToServer(FirebaseInstanceId.getInstance().getToken());
                        }
                    }
                }
                @Override
                public void onFailure(Call<Token> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    Log.d("ApiError",t.getMessage());
                    hideProgress();
                }
            });

        }catch (Exception e){
            Log.d("error",e.getMessage());
            Toast.makeText(LoginActivity.this, "Email or password is not correct", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidate(){
        if(!ValidationUtility.EditTextValidator(txtEmail,txtPassword)){
            GeneralHelper.ShowToast(this, "Phone number or password can not be empty!");
            return false;
        }else{
            return true;
        }
    }


    public void addUpdateDeviceInfoToServer(String FCMtoken){
        //showProgress();

        if(FCMtoken.isEmpty() || FCMtoken ==""){
            return;


        }


        DeviceInfoRequest obj = new DeviceInfoRequest();
        obj.setAppType(Constants.APP_TYPE);
        obj.setFCMToken(FCMtoken);


        Gson gson = new Gson();
        String Reslog= gson.toJson(obj);
        Log.d("testme", Reslog);

        RestClient.getAuthAdapterToekn(tokenHelper.GetToken()).updateDeviceInfo(obj).enqueue(new GeneralCallBack<DeviceInfoResponse>(this) {
            @Override
            public void onSuccess(DeviceInfoResponse response) {
                Gson gson = new Gson();
                String Reslog= gson.toJson(response);
                Log.d("testme", Reslog);

                if(!response.getIserror()){
                    String msg=getApplicationContext().getString(R.string.msg_device_info_successfull);
                    Toast.makeText(getApplicationContext(),msg ,
                            Toast.LENGTH_LONG).show();


                }
                else{

                    String msg=getApplicationContext().getString(R.string.msg_device_info_failed);
                    Toast.makeText(getApplicationContext(),msg ,
                            Toast.LENGTH_LONG).show();

                }

                hideProgress();



            }

            @Override
            public void onFailure(Throwable throwable) {
                //onFailure implementation would be in GeneralCallBack class
                hideProgress();
                String msg=getApplicationContext().getString(R.string.msg_device_info_failed);
                Toast.makeText(getApplicationContext(),msg ,
                        Toast.LENGTH_LONG).show();

            }



        });



    }
}
