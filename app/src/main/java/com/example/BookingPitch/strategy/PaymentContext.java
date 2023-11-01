package com.example.BookingPitch.strategy;

import androidx.fragment.app.DialogFragment;

public class PaymentContext {


    public DialogFragment processPayMent(PaymentStrategy strategy) {
        // Here we could collect and store payment data from the strategy.
        return strategy.collectPaymentDetail( );
    }
}
