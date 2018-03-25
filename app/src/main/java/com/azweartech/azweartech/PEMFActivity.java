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
    static final int MAGNITUDE_LOW=0;
    static final int MAGNITUDE_HIGH=100;
    static final int PULSE_LOW=0;
    static final int PULSE_HIGH=100;

    int currentMagProgress;
    int currentPulseProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemf);

        /** Initialize current mag and pulse values */
        currentMagProgress=0;
        currentPulseProgress=0;


        /** Get TextViews, SeekBars, and the Button from the xml file */

        TextView magLow = (TextView) findViewById(R.id.magLowNumber);
        TextView magHigh = (TextView) findViewById(R.id.magHighNumber);
        SeekBar magSeekBar = (SeekBar) findViewById(R.id.magSeekBar);
        TextView pulseLow = (TextView) findViewById(R.id.pulseLowNumber);
        TextView pulseHigh = (TextView) findViewById(R.id.pulseHighNumber);
        SeekBar pulseSeekBar = (SeekBar) findViewById(R.id.pulseSeekBar);
        Button aboutButton = (Button) findViewById(R.id.PEMFHelpButton);

        /** Set Low and High values in the text boxes by the sliders */
        magLow.setText(""+MAGNITUDE_LOW);
        magHigh.setText(""+MAGNITUDE_HIGH);
        pulseLow.setText(""+PULSE_LOW);
        pulseHigh.setText(""+PULSE_HIGH);

        /** Set Maxes for the SeekBars based on the high values for each */
        magSeekBar.setMax(MAGNITUDE_HIGH);
        pulseSeekBar.setMax(PULSE_HIGH);

        /** Create onClickListener for the Magnitude SeekBar */
        magSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /** Registers changes to the Magnitude SeekBar */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                currentMagProgress=progressValue;
                changeMagnitude();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        /** Create onClickListener for the Pulse SeekBar */
        pulseSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /** Registers changes to the Pulse SeekBar */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                currentPulseProgress=progressValue;
                changePulse();
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

    /** Sends updated magnitude value to the arduino */
    public void changeMagnitude() {
        //Put code for changing the magnitude setting in the arduinio
    }

    /** Sends updated pulse value to the arduino */
    public void changePulse(){
        //Put code for changing the pulse setting in the arduino
    }
}
