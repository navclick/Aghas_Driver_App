package com.example.naveed.aghas_rider_app.firebaseservice;

import android.util.Log;
import android.widget.Toast;

import com.example.naveed.aghas_rider_app.Base.GeneralCallBackService;
import com.example.naveed.aghas_rider_app.Helpers.Constants;
import com.example.naveed.aghas_rider_app.Helpers.TokenHelper;
import com.example.naveed.aghas_rider_app.Models.DeviceInfoRequest;
import com.example.naveed.aghas_rider_app.Models.DeviceInfoResponse;
import com.example.naveed.aghas_rider_app.Network.RestClient;
import com.example.naveed.aghas_rider_app.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

public class FirebaseService extends FirebaseInstanceIdService {
    private static final String TAG = "testme";
    private static final String TOPIC_CUSTOMER = "customer";
    public TokenHelper tokenHelper;
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // now subscribe to `global` topic to receive app wide notifications
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC_CUSTOMER);

        tokenHelper = new TokenHelper(this); // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.


        tokenHelper.saveFCMToken(refreshedToken);
        if(tokenHelper.GetToken()==null || tokenHelper.GetToken()=="") {

        }
        else {
            sendRegistrationToServer(refreshedToken);
        }
    }

    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.

        //  showProgress();
        DeviceInfoRequest obj = new DeviceInfoRequest();
        obj.setAppType(Constants.APP_TYPE);
        obj.setFCMToken(token);


        Gson gson = new Gson();
        String Reslog= gson.toJson(obj);
        Log.d("testme", Reslog);

        RestClient.getAuthAdapterToekn(tokenHelper.GetToken()).updateDeviceInfo(obj).enqueue(new GeneralCallBackService<DeviceInfoResponse>(this) {

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

                //hideProgress();



            }

            @Override
            public void onFailure(Throwable throwable) {
                //onFailure implementation would be in GeneralCallBack class
                // hideProgress();
                String msg=getApplicationContext().getString(R.string.msg_device_info_failed);
                Toast.makeText(getApplicationContext(),msg ,
                        Toast.LENGTH_LONG).show();

            }



        });

    }
}
