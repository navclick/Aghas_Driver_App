package com.example.naveed.aghas_rider_app.Activities;

import android.Manifest;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.naveed.aghas_rider_app.BackGroundServices.LocationService;
import com.example.naveed.aghas_rider_app.Base.BaseActivity;
import com.example.naveed.aghas_rider_app.Helpers.DirectionPointListener;
import com.example.naveed.aghas_rider_app.Helpers.GetPathFromLocation;
import com.example.naveed.aghas_rider_app.Models.Order;
import com.example.naveed.aghas_rider_app.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Text;

public class NavigationActivity extends BaseActivity implements OnMapReadyCallback , View.OnClickListener{
    private GoogleMap mMap;
    static final LatLng Aghas = new LatLng(24.8283061, 67.0355426);
    public  LatLng custLocation;
    public Marker MeMarker = null;
    public Marker RiderMarker = null;
    float zoomLevel = (float) 13.0;
    public static Order order;
    private Button btn_back;
    WifiLevelReceiver receiver;

    private TextView txt_contact,txt_cus_name,txt_address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        txt_contact =(TextView) findViewById(R.id.txt_contact);
        txt_cus_name =(TextView) findViewById(R.id.txt_cus_name);
        txt_address =(TextView) findViewById(R.id.txt_address);
        btn_back=(Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        if(order.getValue().getLatitude() != null && order.getValue().getLatitude() != ""){
            custLocation = new LatLng(Double.valueOf(order.getValue().getLatitude().toString()),Double.valueOf(order.getValue().getLongitude().toString()));


        }

        receiver = new WifiLevelReceiver();
        registerReceiver(receiver, new IntentFilter("BroadcastLocation"));
        setupMAP();

        txt_contact.setText(order.getValue().getContactNo());
        txt_cus_name.setText(order.getValue().getCustomerName());
        txt_address.setText(order.getValue().getAddress());

    }


    public void setupMAP(){

        if(!isMyServiceRunning(LocationService.class)) {
            // startService(new Intent(MainActivity.this, LocationService.class));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //  this.startForegroundService(new Intent(this, LocationService.class));
                this.startForegroundService(new Intent(this, LocationService.class));

            } else {
                startService(new Intent(this, LocationService.class));
            }






        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        //custLocation = getLocationFromAddress(this, OrderDetails.CustomerAddress);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mMap.setMyLocationEnabled(true);




        if(custLocation != null) {

            //  mMap.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );

/*
        MarkerOptions markerOptions = new MarkerOptions();

        // Setting the position for the marker
        markerOptions.position(custLocation);

        // Setting the title for the marker.
        // This will be displayed on taping the marker
        markerOptions.title("you");

        // Clears the previously touched position


        mMap.addMarker(markerOptions).showInfoWindow();

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(custLocation, zoomLevel));
        }
        else{
        custLocation=HAMBURG;
        MarkerOptions markerOptions = new MarkerOptions();

        // Setting the position for the marker
        markerOptions.position(HAMBURG);

        // Setting the title for the marker.
        // This will be displayed on taping the marker
        markerOptions.title("title");

        mMap.addMarker(markerOptions).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, zoomLevel));
*/


            this.MeMarker = mMap.addMarker(new MarkerOptions()
                    .position(custLocation)

                    .title(order.getValue().getCustomerName())

                    .snippet(order.getValue().getCustomerName())

            );
            this.MeMarker.showInfoWindow();
            //mMap.addMarker(MeMarker.).showInfoWindow();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(custLocation,zoomLevel));

        }




    }

    /*
    private BroadcastReceiver br = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "get broadcast", Toast.LENGTH_SHORT).show();
            Bundle bundle = intent.getExtras();
            String lat =bundle.getString("lat");
            String lng =bundle.getString("long");

            LatLng mylocation = new LatLng(Double.valueOf(lat), Double.valueOf(lng));

            // animateMarker(MeMarker,Destination,true);

            if(RiderMarker ==null){


                RiderMarker = mMap.addMarker(new MarkerOptions()
                        .position(mylocation)

                        .title("You")

                        .snippet("You")

                );
                RiderMarker.showInfoWindow();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mylocation,zoomLevel));
            }
            else {

                RiderMarker.setPosition(new LatLng(mylocation.latitude, mylocation.longitude));

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mylocation, zoomLevel));
                Toast.makeText(getApplicationContext(), "From Service " + lat + "    " + lng,
                        Toast.LENGTH_LONG).show();
            }
        }
    };


    */

    @Override
    public void onStop()
    {
        super.onStop();
        unregisterReceiver(receiver);           //<-- Unregister to avoid memoryleak
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                OpenActivity(CurrentOrderDetailActivity.class, order.getValue().getId().toString());
                break;



        }



    }


    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }


    public void animateMarker(final Marker marker, final LatLng toPosition,
                              final boolean hideMarker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = mMap.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 500;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
    }
    class WifiLevelReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals("BroadcastLocation"))
            {
                Toast.makeText(context, "get broadcast", Toast.LENGTH_SHORT).show();
                String lat = intent.getStringExtra("lat");
                String lng  =intent.getStringExtra("long");

               // Toast.makeText(context, lat+" + "+lng, Toast.LENGTH_SHORT).show();
                // Show it in GraphView

                LatLng mylocation = new LatLng(Double.valueOf(lat), Double.valueOf(lng));

                // animateMarker(MeMarker,Destination,true);

                if(RiderMarker ==null){



                    LatLng source = new LatLng(mylocation.latitude, mylocation.longitude);
                    LatLng destination = new LatLng(custLocation.latitude, custLocation.longitude);

                    new GetPathFromLocation(source, destination, new DirectionPointListener() {
                        @Override
                        public void onPath(PolylineOptions polyLine) {
                            mMap.addPolyline(polyLine);
                        }
                    }).execute();


                    RiderMarker = mMap.addMarker(new MarkerOptions()
                            .position(mylocation)

                            .title("You")

                            .snippet("You")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))

                    );
                    RiderMarker.showInfoWindow();
                  //  mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mylocation,zoomLevel));
                }
                else {

                    RiderMarker.setPosition(new LatLng(mylocation.latitude, mylocation.longitude));

                   // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mylocation, zoomLevel));
                 //   Toast.makeText(getApplicationContext(), "From Service " + lat + "    " + lng,
                   //         Toast.LENGTH_LONG).show();
                }

            }
        }

    }

}
