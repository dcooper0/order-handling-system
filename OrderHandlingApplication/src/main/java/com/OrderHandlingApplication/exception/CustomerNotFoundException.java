package com.OrderHandlingApplication.exception;

public class CustomerNotFoundException extends RuntimeException{

    public CustomerNotFoundException(String name){
        super("No customer found with the name: " + name);
    }
}
