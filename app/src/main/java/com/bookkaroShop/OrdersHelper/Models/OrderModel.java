package com.bookkaroShop.OrdersHelper.Models;

import java.util.ArrayList;

public class OrderModel {
    ArrayList<OrderItemModel> orderItems;
    int orderStatus;
    int orderTotal;
    int orderType;
    AddressModel userAddress;
    String userPhoneNumber;
    String vendorName;
    String vendorPhoneNumber;

    String orderKey;

    public OrderModel() {
    }

    public ArrayList<OrderItemModel> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(ArrayList<OrderItemModel> orderItems) {
        this.orderItems = orderItems;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(int orderTotal) {
        this.orderTotal = orderTotal;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public AddressModel getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(AddressModel userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorPhoneNumber() {
        return vendorPhoneNumber;
    }

    public void setVendorPhoneNumber(String vendorPhoneNumber) {
        this.vendorPhoneNumber = vendorPhoneNumber;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }
}
