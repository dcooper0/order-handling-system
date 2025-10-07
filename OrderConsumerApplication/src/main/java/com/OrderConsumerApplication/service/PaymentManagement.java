package com.OrderConsumerApplication.service;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class PaymentManagement {

    public final Random random = new Random();

    public boolean paymentDetailsAreValid(String paymentDetails){
        //TODO - add method to check payment detail validity/sufficient funds via external API call
        // new method to go here <-
        return paymentDetails != null && paymentDetails.equals("dummy valid details");
    }

    public boolean takePaymentFromCard(String paymentDetails){
        // TODO - add method to process payment using previously validated details
        // new method to go here <-
        return paymentDetailsAreValid(paymentDetails);
    }
}
