package com.example.naveed.aghas_rider_app.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.naveed.aghas_rider_app.Base.BaseActivity;
import com.example.naveed.aghas_rider_app.R;

public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnSignIn = (Button) findViewById(R.id.btn_signin);

        btnSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_signin:
                OpenActivity(LoginActivity.class);
                break;
        }
    }
}
