package com.order.shared.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

public enum OrderStatus {
    PLACED,
    COMPLETED,
    OUT_OF_STOCK,
    INVALID_PAYMENT_DETAILS,
    PAYMENT_FAILED;

      @JsonCreator
    public static OrderStatus fromValue(String value){
        if (value == null) {
            return null;
        } else {
            return OrderStatus.valueOf(value.trim().toUpperCase());
        }
    }

    @JsonFormat
    public String toValue(){
        return name().toLowerCase();
    }
}
