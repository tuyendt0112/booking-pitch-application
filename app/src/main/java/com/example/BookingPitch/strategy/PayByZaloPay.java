package com.example.BookingPitch.strategy;

import androidx.fragment.app.DialogFragment;

import com.example.BookingPitch.strategy.Dialog.ZaloPayDialog;

public class PayByZaloPay  implements PaymentStrategy{



    @Override
    public DialogFragment collectPaymentDetail( ) {
            ZaloPayDialog zaloPayDialog = new ZaloPayDialog();
            return  zaloPayDialog;
    }
}
