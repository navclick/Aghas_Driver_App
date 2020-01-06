package com.example.naveed.aghas_rider_app.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.naveed.aghas_rider_app.Base.BaseActivity;
import com.example.naveed.aghas_rider_app.Helpers.Constants;
import com.example.naveed.aghas_rider_app.Helpers.GeneralCallBack;
import com.example.naveed.aghas_rider_app.Helpers.GeneralHelper;
import com.example.naveed.aghas_rider_app.Models.GResponse;
import com.example.naveed.aghas_rider_app.Network.RestClient;
import com.example.naveed.aghas_rider_app.R;
import com.example.naveed.aghas_rider_app.Utilities.ValidationUtility;
import com.google.gson.Gson;

public class StatusChangeActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener{

public static int statusCode= Constants.ORDER_DELIVERED;
    public RadioButton radio_Delivered,radio_Return,radio_Cancel;
public EditText txt_reason;
public Button btn_update,btn_close;
public static int OrderId=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_change_status);
        radio_Delivered =(RadioButton) findViewById(R.id.radio_Delivered);
                radio_Return=(RadioButton) findViewById(R.id.radio_Return);
        radio_Cancel=(RadioButton) findViewById(R.id.radio_Cancel);
        txt_reason=(EditText) findViewById(R.id.txt_reason);
        radio_Delivered.setChecked(true);

        radio_Delivered.setOnCheckedChangeListener(this);

        radio_Return.setOnCheckedChangeListener(this);
        radio_Cancel.setOnCheckedChangeListener(this);


        btn_update =(Button) findViewById(R.id.btn_update);
        btn_close=(Button) findViewById(R.id.btn_close);
        btn_update.setOnClickListener(this);
        btn_close.setOnClickListener(this);
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            if (buttonView.getId() == R.id.radio_Delivered) {
                radio_Delivered.setChecked(true);
                radio_Return.setChecked(false);
                statusCode= Constants.ORDER_DELIVERED;
                radio_Cancel.setChecked(false);
                txt_reason.setVisibility(View.GONE);


            }
            if (buttonView.getId() == R.id.radio_Return) {
                radio_Return.setChecked(true);
                radio_Delivered.setChecked(false);
                statusCode= Constants.ORDER_RETURNED;

                radio_Cancel.setChecked(false);
                txt_reason.setVisibility(View.VISIBLE);
            }

            if (buttonView.getId() == R.id.radio_Cancel) {
                radio_Cancel.setChecked(true);
                radio_Return.setChecked(true);
                radio_Delivered.setChecked(false);
                statusCode= Constants.ORDER_CANCELLED;


                ;
                txt_reason.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_update:
                Log.d("test","Next click");
                Update();

                break;
            case R.id.btn_close:
                finish();
                break;



        }

    }




    public void Update(){


        if(OrderId==0){

          return;
        }
        if(statusCode==0){
            return;


        }

        if(statusCode==6){

            txt_reason.setText("N/A");
        }
        if(statusCode==8 || statusCode==7){



            if(!ValidationUtility.EditTextValidator(txt_reason)){
                GeneralHelper.ShowToast(this, "Reason can not be empty!");
                return ;
            }




        }



        showProgress();
        Log.d("test","intest");
        RestClient.getAuthAdapterToekn(tokenHelper.GetToken()).UpdateStatus(OrderId,statusCode,txt_reason.getText().toString()).enqueue(new GeneralCallBack<GResponse>(this) {
            @Override
            public void onSuccess(GResponse response) {
                Gson gson = new Gson();
                String Reslog= gson.toJson(response);
                Log.d(Constants.TAG, Reslog);
                hideProgress();

                if (!response.getIserror()) {
                    OrderId=0;
                    OpenActivity(CurrentOrderActivity.class);


                    //getFproducts();

                }
                else{


                    AlertDialog alertDialog = new AlertDialog.Builder(StatusChangeActivity.this).create();
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


}
