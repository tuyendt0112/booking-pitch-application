package com.example.BookingPitch.custom_barchart.custom_barchart;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;

public class MyAxisValueFormatter extends ValueFormatter {

    private DecimalFormat decimalFormat;

    public MyAxisValueFormatter(){
        decimalFormat = new DecimalFormat("###.#");
    }

    @Override
    public String getFormattedValue(float value) {
        if (value % 1 == 0)
            return (int) value + "tr";
        else return decimalFormat.format(value) + "tr";
    }
}