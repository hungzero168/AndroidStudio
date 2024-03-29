package com.example.foodapp.Model;

public class OrderInfo {
    private int orderId;
    private String orderStatus;
    private String orderDate;
    private String orderTime;
    private float totalAmount;
    private String userName;
    private String userAddress;
    private String userPhoneNumber;
    private String foodName;
    private float foodPrice;
    private float foodDiscountPrice;
    private int foodQuantity;
    private float itemTotal;

    public OrderInfo(int orderId, String orderStatus, String orderDate, String orderTime,
                     float totalAmount, String userName, String userAddress, String userPhoneNumber,
                     String foodName, float foodPrice, float foodDiscountPrice, int foodQuantity, float itemTotal) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.totalAmount = totalAmount;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userPhoneNumber = userPhoneNumber;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodDiscountPrice = foodDiscountPrice;
        this.foodQuantity = foodQuantity;
        this.itemTotal = itemTotal;
    }

    public OrderInfo(int orderId, String orderStatus, String orderDate, String orderTime, float totalAmount, String userName, String userAddress, String userPhoneNumber, String foodDetails, float orderTotal) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.totalAmount = totalAmount;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userPhoneNumber = userPhoneNumber;
        this.foodName = foodDetails;
        this.itemTotal = orderTotal;

    }

    public int getOrderId() {
        return orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public String getFoodName() {
        return foodName;
    }

    public float getFoodPrice() {
        return foodPrice;
    }

    public float getFoodDiscountPrice() {
        return foodDiscountPrice;
    }

    public int getFoodQuantity() {
        return foodQuantity;
    }

    public float getItemTotal() {
        return itemTotal;
    }
}

