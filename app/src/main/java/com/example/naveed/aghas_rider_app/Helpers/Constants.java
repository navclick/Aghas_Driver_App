package com.example.naveed.aghas_rider_app.Helpers;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    // Network

    //public static String BASE_URL="http://aghasapi.insidedemo.com/";
    //public static String BASE_URL = "http://192.168.100.2:9091/";
    public static String BASE_URL="http://apis.aghas.com.pk/";
  //  public static String BASE_URL = "http://192.168.100.2:9091/";
    // public static String BASE_URL = "http://ocflexapi.insidedemo.com/";
    public final static  long CONNECTION_TIMEOUT = 25;
    public final static int DATA_SUCCESS = 1;
    public final static int DATA_ERROR = 0;

    // Database
    public final static String DATABASE_NAME = "TempDB";
    public static final int DATABASE_VERSION = 1;

    // Splash screen
    public static final int SPLASH_TIME_OUT = 3000;

    // On billing screen
    public static boolean IS_BILLING = false;

    // Network
    public final static String TAG = "test";

    // Splash screen
    public static final int ORDER_PENDING = 1;
    public static final int ORDER_PLACED = 2;
    public static final int ORDER_ASSIGNED = 3;
    public static final int ORDER_ACTIVE = 5;
    public static final int ORDER_DELIVERED= 6;
    public static final int ORDER_RETURNED = 8;
    public static final int ORDER_CANCELLED_BY_CUSTOMER = 11;
    public static final int ORDER_CANCELLED= 7;

    public static final int APP_TYPE =1; //0-web, 1-android, 2-Ios
    public static final Map<Integer, String> OrderStatus = new HashMap<Integer, String>();

    public static void init(){

        OrderStatus.put(1,"Pending");
        OrderStatus.put(2,"Placed");
        OrderStatus.put(3,"Assigned");
        OrderStatus.put(4,"Dispatched");
        OrderStatus.put(5,"Active");
        OrderStatus.put(6,"Dilivered");

        OrderStatus.put(7,"Canceled");
        OrderStatus.put(8,"Returned");
        OrderStatus.put(9,"Ready For Pickup");
        OrderStatus.put(10,"Picked");
        OrderStatus.put(11,"Canceled By Customer");
    }

    public static final String MSG_SERVICE_STATUS_UPDATED= "Service status updated.";

    public static final String MSG_SERVICE_STATUS_UPDATE_FAILED= "Failed to update service status. Please try again.";
    public static final String MESSAGE_REQUESTED_PERMISSION_DENIED = "Requested permissions are required to continue app use.";

    public static final String MESSAGE_ACCEPT_TERMS = "You must agree to the terms and conditions. ";


}