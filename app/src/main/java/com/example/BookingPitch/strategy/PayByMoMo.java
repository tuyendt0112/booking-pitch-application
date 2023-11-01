package com.example.BookingPitch.strategy;


import androidx.fragment.app.DialogFragment;

import com.example.BookingPitch.strategy.Dialog.MoMoDialog;

public class PayByMoMo  implements PaymentStrategy  {

    @Override
    public DialogFragment collectPaymentDetail( ) {
        MoMoDialog moMoDialog = new MoMoDialog();
        return moMoDialog;
    }
}

