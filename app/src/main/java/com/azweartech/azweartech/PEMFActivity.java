package com.azweartech.azweartech;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Set;

public class PEMFActivity extends AppCompatActivity {

    /** Low and High values to put in the text boxes by the sliders */
    static final int FREQUENCY_LOW=0;
    static final int FREQUENCY_HIGH=10000;
    static final int INTENSITY_LOW=0;
    static final int INTENSITY_HIGH=100;

    int currentFrequencyProgress;
    int currentIntensityProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemf);

        /** Initialize current duty cycle and pulse values */
        currentFrequencyProgress=0;
        currentIntensityProgress=0;


        /** Get TextViews, SeekBars, and the Button from the xml file */

        TextView frequencyVal = (TextView) findViewById(R.id.frequencyVal);
        SeekBar frequencySeekBar = (SeekBar) findViewById(R.id.freqSeekBar);
        TextView intensityVal = (TextView) findViewById(R.id.intensityVal);
        SeekBar intensitySeekBar = (SeekBar) findViewById(R.id.intensitySeekBar);
        Button aboutButton = (Button) findViewById(R.id.PEMFHelpButton);

        /** Set Low and High values in the text boxes by the sliders */
        frequencyVal.setText(""+FREQUENCY_LOW);
        intensityVal.setText(""+INTENSITY_LOW);

        /** Set Maxes for the SeekBars based on the high values for each */
        frequencySeekBar.setMax((FREQUENCY_HIGH-FREQUENCY_LOW)/100);
        intensitySeekBar.setMax(INTENSITY_HIGH-INTENSITY_LOW);

        /** Create onClickListener for the Frequency SeekBar */
        frequencySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /** Registers changes to the Frequency SeekBar */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                currentFrequencyProgress=progressValue;
                updateFrequencyText();
                changeFrequency();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        /** Create onClickListener for the Intensity SeekBar */
        intensitySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /** Registers changes to the Intensity SeekBar */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                currentIntensityProgress=progressValue;
                updateIntensityText();
                changeIntensity();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        /** Set onClickListener for the Help Button */
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AboutPEMFIntent = new Intent(PEMFActivity.this, AboutPEMFActivity.class);
                startActivity(AboutPEMFIntent);
            }
        });

    }

    /** Sends updated frequency value to the arduino */
    public void changeFrequency() {
        //Put code for changing the duty cycle setting in the arduino
    }

    /** Sends updated intensity value to the arduino */
    public void changeIntensity(){
        //Put code for changing the pulse setting in the arduino
    }

    /** Updates the TextView that displays the current duty cycle value */
    public void updateFrequencyText(){
        TextView frequencyVal = (TextView) findViewById(R.id.frequencyVal);
        int frequencyValue = currentFrequencyProgress*100+ FREQUENCY_LOW;
        frequencyVal.setText(""+frequencyValue);
    }

    /** Updates the TextView that displays the current pulse value */
    public void updateIntensityText(){
        TextView intensityVal = (TextView) findViewById(R.id.intensityVal);
        int intensityValue = currentIntensityProgress + INTENSITY_LOW;
        intensityVal.setText(""+intensityValue);
    }
}
