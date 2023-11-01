package com.example.BookingPitch.strategy;

import androidx.fragment.app.DialogFragment;

import com.example.BookingPitch.strategy.Dialog.PayPalDialog;

public class PayByPayPal  implements PaymentStrategy{


    @Override
    public DialogFragment collectPaymentDetail( ) {
        PayPalDialog payPalDialog = new PayPalDialog();
        return  payPalDialog;
    }
}
