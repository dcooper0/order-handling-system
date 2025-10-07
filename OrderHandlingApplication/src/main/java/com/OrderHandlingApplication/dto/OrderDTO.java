package com.OrderHandlingApplication.dto;

public class OrderDTO {

    private String customerName;
    private String productName;

    public OrderDTO() {}

    public OrderDTO(String customerName, String productName) {
        this.customerName = customerName;
        this.productName = productName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getProductName() {
        return productName;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "customerName='" + customerName + '\'' +
                ", productName='" + productName + '\'' +
                '}';
    }
}
