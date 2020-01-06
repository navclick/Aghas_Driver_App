package com.example.naveed.aghas_rider_app.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderListUp {



    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("iserror")
    @Expose
    private Boolean iserror;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("value")
    @Expose
    private List<Value> value = null;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getIserror() {
        return iserror;
    }

    public void setIserror(Boolean iserror) {
        this.iserror = iserror;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Value> getValue() {
        return value;
    }

    public void setValue(List<Value> value) {
        this.value = value;
    }


    public class Value {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("customerId")
        @Expose
        private String customerId;
        @SerializedName("customerName")
        @Expose
        private String customerName;
        @SerializedName("customerPhone")
        @Expose
        private Object customerPhone;
        @SerializedName("total")
        @Expose
        private Integer total;
        @SerializedName("tax")
        @Expose
        private Integer tax;
        @SerializedName("orderTime")
        @Expose
        private String orderTime;
        @SerializedName("deliveryDate")
        @Expose
        private Object deliveryDate;
        @SerializedName("orderStatusId")
        @Expose
        private Integer orderStatusId;
        @SerializedName("orderStatus")
        @Expose
        private String orderStatus;
        @SerializedName("orderTypeId")
        @Expose
        private Integer orderTypeId;
        @SerializedName("orderType")
        @Expose
        private String orderType;
        @SerializedName("paymentMethodId")
        @Expose
        private Integer paymentMethodId;
        @SerializedName("paymentMethod")
        @Expose
        private String paymentMethod;
        @SerializedName("paymentStatusId")
        @Expose
        private Integer paymentStatusId;
        @SerializedName("paymentStatus")
        @Expose
        private String paymentStatus;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("contactNo")
        @Expose
        private String contactNo;
        @SerializedName("assignedTo")
        @Expose
        private String assignedTo;
        @SerializedName("assignedToID")
        @Expose
        private String assignedToID;
        @SerializedName("isPrint")
        @Expose
        private Integer isPrint;
        @SerializedName("orderItem")
        @Expose
        private List<OrderItem> orderItem = null;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public Object getCustomerPhone() {
            return customerPhone;
        }

        public void setCustomerPhone(Object customerPhone) {
            this.customerPhone = customerPhone;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public Integer getTax() {
            return tax;
        }

        public void setTax(Integer tax) {
            this.tax = tax;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public Object getDeliveryDate() {
            return deliveryDate;
        }

        public void setDeliveryDate(Object deliveryDate) {
            this.deliveryDate = deliveryDate;
        }

        public Integer getOrderStatusId() {
            return orderStatusId;
        }

        public void setOrderStatusId(Integer orderStatusId) {
            this.orderStatusId = orderStatusId;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public Integer getOrderTypeId() {
            return orderTypeId;
        }

        public void setOrderTypeId(Integer orderTypeId) {
            this.orderTypeId = orderTypeId;
        }

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public Integer getPaymentMethodId() {
            return paymentMethodId;
        }

        public void setPaymentMethodId(Integer paymentMethodId) {
            this.paymentMethodId = paymentMethodId;
        }

        public String getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public Integer getPaymentStatusId() {
            return paymentStatusId;
        }

        public void setPaymentStatusId(Integer paymentStatusId) {
            this.paymentStatusId = paymentStatusId;
        }

        public String getPaymentStatus() {
            return paymentStatus;
        }

        public void setPaymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getContactNo() {
            return contactNo;
        }

        public void setContactNo(String contactNo) {
            this.contactNo = contactNo;
        }

        public String getAssignedTo() {
            return assignedTo;
        }

        public void setAssignedTo(String assignedTo) {
            this.assignedTo = assignedTo;
        }

        public String getAssignedToID() {
            return assignedToID;
        }

        public void setAssignedToID(String assignedToID) {
            this.assignedToID = assignedToID;
        }

        public Integer getIsPrint() {
            return isPrint;
        }

        public void setIsPrint(Integer isPrint) {
            this.isPrint = isPrint;
        }

        public List<OrderItem> getOrderItem() {
            return orderItem;
        }

        public void setOrderItem(List<OrderItem> orderItem) {
            this.orderItem = orderItem;
        }

    }


    public class OrderItem {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("orderId")
        @Expose
        private Integer orderId;
        @SerializedName("itemId")
        @Expose
        private Integer itemId;
        @SerializedName("itemName")
        @Expose
        private String itemName;
        @SerializedName("price")
        @Expose
        private Object price;
        @SerializedName("quantity")
        @Expose
        private Integer quantity;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

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

        public Object getPrice() {
            return price;
        }

        public void setPrice(Object price) {
            this.price = price;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

    }
}
