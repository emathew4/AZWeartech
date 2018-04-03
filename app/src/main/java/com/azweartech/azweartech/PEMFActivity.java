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
    static final int DUTY_CYCLE_LOW=0;
    static final int DUTY_CYCLE_HIGH=100;
    static final int PULSE_LOW=0;
    static final int PULSE_HIGH=100;

    int currentDutyCycleProgress;
    int currentPulseProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemf);

        /** Initialize current duty cycle and pulse values */
        currentDutyCycleProgress=0;
        currentPulseProgress=0;


        /** Get TextViews, SeekBars, and the Button from the xml file */

        TextView dutyCycleVal = (TextView) findViewById(R.id.dutyCycleVal);
        SeekBar dutyCycleSeekBar = (SeekBar) findViewById(R.id.dutyCycleSeekBar);
        TextView pulseVal = (TextView) findViewById(R.id.pulseVal);
        SeekBar pulseSeekBar = (SeekBar) findViewById(R.id.pulseSeekBar);
        Button aboutButton = (Button) findViewById(R.id.PEMFHelpButton);

        /** Set Low and High values in the text boxes by the sliders */
        dutyCycleVal.setText(""+DUTY_CYCLE_LOW);
        pulseVal.setText(""+PULSE_LOW);

        /** Set Maxes for the SeekBars based on the high values for each */
        dutyCycleSeekBar.setMax(DUTY_CYCLE_HIGH-DUTY_CYCLE_LOW);
        pulseSeekBar.setMax(PULSE_HIGH-PULSE_LOW);

        /** Create onClickListener for the Duty Cycle SeekBar */
        dutyCycleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /** Registers changes to the Duty Cycle SeekBar */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                currentDutyCycleProgress=progressValue;
                updateDutyCycleText();
                changeDutyCycle();
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
                updatePulseText();
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

    /** Sends updated duty cycle value to the arduino */
    public void changeDutyCycle() {
        //Put code for changing the duty cycle setting in the arduinio
    }

    /** Sends updated pulse value to the arduino */
    public void changePulse(){
        //Put code for changing the pulse setting in the arduino
    }

    /** Updates the TextView that displays the current duty cycle value */
    public void updateDutyCycleText(){
        TextView dutyCycleVal = (TextView) findViewById(R.id.dutyCycleVal);
        int dutyCycleValue = currentDutyCycleProgress+ DUTY_CYCLE_LOW;
        dutyCycleVal.setText(""+dutyCycleValue);
    }

    /** Updates the TextView that displays the current pulse value */
    public void updatePulseText(){
        TextView pulseVal = (TextView) findViewById(R.id.pulseVal);
        int pulseValue = currentPulseProgress + PULSE_LOW;
        pulseVal.setText(""+pulseValue);
    }
}
