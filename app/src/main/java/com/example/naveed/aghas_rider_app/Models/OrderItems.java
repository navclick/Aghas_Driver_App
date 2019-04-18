package com.example.naveed.aghas_rider_app.Models;

public class OrderItems {

    public OrderItems(){

    }

    public OrderItems(int orderid, int itemid, String itemname,  int quantity){
        this.orderId = orderid;
        this.itemId = itemid;
        this.itemName = itemname;
        this.quantity = quantity;
    }

    private Integer orderId;
    private Integer itemId;
    private String itemName;
    private Integer quantity;


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}