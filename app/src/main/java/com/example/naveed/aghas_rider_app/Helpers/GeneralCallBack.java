package com.example.naveed.aghas_rider_app.Helpers;

import android.widget.Toast;

import com.example.naveed.aghas_rider_app.Base.BaseActivity;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class GeneralCallBack<T> implements Callback<T> {

    //private final Context context;
    private BaseActivity activity;


    /**
     * @purpose Parameter constructor
     * @param context
     */
    public GeneralCallBack(BaseActivity context)
    {
        this.activity = context;
    }

    /*this method will invoked when server gives an reponse and this have multiple implementation according to scenario*/
    @Override
    public void onResponse(Call<T> call, Response<T> response) {


        if (response.isSuccessful() && response.body() != null) {

            onSuccess(response.body());
        } else {
            onServerFailure("Server error");
        }



    }


    @Override
    public void onFailure(Call<T> call, Throwable throwable) {
        // activity.hideProgress();
        if (throwable instanceof IOException) {
            //Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
            // activity.showMessage(activity.getString(R.string.no_interconection_error));

        }
        else {
            //Toast.makeText(activity, "Something goes wrong!", Toast.LENGTH_SHORT).show();
            // Toast.makeText(activity, "Something goes wrong!", Toast.LENGTH_SHORT).show();
            //activity.showMessage(activity.getString(R.string.some_thing_goes_wrong));


        }
        onFailure(throwable);
    }

    /**
     * @purpose When server fails due to some validations
     * @param message
     */
    private void onServerFailure( String message)
    {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * @purpose this method will be implemented to all concrete class
     * @param response
     */
    abstract public void onSuccess(T response);
    abstract public void onFailure(Throwable throwable);


}