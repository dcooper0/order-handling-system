package com.order.shared.order;

import com.order.shared.user.Address;

import java.util.Objects;



public class Order {

    private String customerName;
    private String productName;
    private Address deliveryAddress;
    private int orderNumber;
    public OrderStatus orderstatus;

    public Order(){}

    public Order(String customerName, String productName, Address deliveryAddress, int orderNumber, OrderStatus orderstatus) {
        this.customerName = customerName;
        this.productName = productName;
        this.deliveryAddress = deliveryAddress;
        this.orderNumber = orderNumber;
        this.orderstatus = orderstatus;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public OrderStatus getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(OrderStatus orderstatus) {
        this.orderstatus = orderstatus;
    }

    @Override
    public String toString() {
        return "Order{" +
                "customerName='" + customerName + '\'' +
                ", productName='" + productName + '\'' +
                ", deliveryAddress=" + deliveryAddress +
                ", orderNumber=" + orderNumber +
                ", orderstatus=" + orderstatus +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return getOrderNumber() == order.getOrderNumber() && Objects.equals(getCustomerName(), order.getCustomerName()) && Objects.equals(getProductName(), order.getProductName()) && Objects.equals(getDeliveryAddress(), order.getDeliveryAddress()) && getOrderstatus() == order.getOrderstatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCustomerName(), getProductName(), getDeliveryAddress(), getOrderNumber(), getOrderstatus());
    }
}
