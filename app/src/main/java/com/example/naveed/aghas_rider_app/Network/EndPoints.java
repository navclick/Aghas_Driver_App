package com.example.naveed.aghas_rider_app.Network;

public class EndPoints {
    public static final String API_PREFIX = "api/";
    public static final String LOGIN = "oauth/token";

    public static final String CURRENTORDER = API_PREFIX + "order/getcurrentorder";
    public static final String TODAYSORDER = API_PREFIX + "order/gettodaysorders";
    public static final String SCHEDULEDORDER = API_PREFIX + "order/getscheduledsorders";
    public static final String ORDERDETAIL = API_PREFIX + "order/orderdetail";
    public static final String MAKEACTIVE = API_PREFIX + "order/MakeOrderActive";

    public static final String UPDATESTATUS = API_PREFIX + "order/UpdateOrderStatusFromRider";
    static final String UPDATEDEVICEINFO=API_PREFIX+"notification/addUpdatefcmtoken";


}
