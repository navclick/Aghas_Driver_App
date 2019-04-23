package com.example.naveed.aghas_rider_app.Models;

public class OrderVM {
    //
    public OrderVM(){

    }

    public OrderVM(int orderid, int total, String customername, String address){
        this.OrderId = orderid;
        this.Total = total;
        this.CustomerName = customername;
        this.Address = address;
    }


    private Integer OrderId;
    private Integer Total;
    private String CustomerName;
    private String Address;

    public Integer getOrderId() {
        return OrderId;
    }

    public void setOrderId(Integer orderid) {
        this.OrderId = orderid;
    }

    public Integer getTotal() {
        return Total;
    }

    public void setTotal(Integer total) {
        this.Total = total;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customername) {
        this.CustomerName = customername;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }
}
