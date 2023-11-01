package com.example.BookingPitch.strategy.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.BookingPitch.MyApplication;
import com.example.BookingPitch.R;
import com.example.BookingPitch.activity.user.UserDatSanChiTietActivity;

public class PayPalDialog extends DialogFragment {

    private EditText edtEmail ;
    private EditText edtPass ;

    private Button btnPay;
    private TextView tvCheckPass;
    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view= inflater.inflate(R.layout.dialog_payment_paypal,null);
        builder.setView(view).setTitle("ZALOPAY")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        edtEmail = view.findViewById(R.id.edt_email_dialog_payment_paypal);
        edtPass = view.findViewById(R.id.edt_pass_dialog_payment_paypal);
        btnPay = view.findViewById(R.id.btn_pay_paypal);
        tvCheckPass = view.findViewById(R.id.tv_check_pass_dialog_payment_paypal);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = edtPass.getText().toString();
                if(!pass.matches(MyApplication.PASS_REGEX)){
                    //invisible(tvCheckPass);
                    tvCheckPass.setVisibility(View.VISIBLE);
                }else{
                    UserDatSanChiTietActivity.setCHECK_OUTTrue();
                    Toast.makeText(getContext(), "Successfully", Toast.LENGTH_SHORT).show();

                }
            }
        });

        return builder.create();
    }
}
