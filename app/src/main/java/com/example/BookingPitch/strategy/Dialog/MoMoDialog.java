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

public class MoMoDialog extends DialogFragment {

    private EditText edtPhone ;
    private EditText edtDesc ;
    private EditText edtAmount ;
    private Button btnPay;
    private TextView tvCheckPhone;
    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view= inflater.inflate(R.layout.dialog_payment_momo,null);
        builder.setView(view).setTitle("MOMO")
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
        edtPhone = view.findViewById(R.id.edt_phone_dialog_payment_momo);
        edtDesc = view.findViewById(R.id.edt_desc_dialog_payment_momo);
        edtAmount = view.findViewById(R.id.edt_amount_dialog_payment_momo);
        btnPay = view.findViewById(R.id.btn_pay_momo);
        tvCheckPhone = view.findViewById(R.id.tv_check_phone_dialog_payment_momo);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = edtPhone.getText().toString();
            if(!phone.matches(MyApplication.PHONE_REGEX)){
                tvCheckPhone.setText("* Invalid phone number");
                tvCheckPhone.setVisibility(View.VISIBLE);
            }else{
                //CHECKOUT = true;
                UserDatSanChiTietActivity.setCHECK_OUTTrue();
                Toast.makeText(getContext(), "Successfully", Toast.LENGTH_SHORT).show();
                //dialog.dismiss();
            }
            }
        });

        return builder.create();
    }

}
