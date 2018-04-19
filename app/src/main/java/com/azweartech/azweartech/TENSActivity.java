package com.azweartech.azweartech;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class TENSActivity extends AppCompatActivity {
    static final int FREQUENCY_LOW=0;
    static final int FREQUENCY_HIGH=120;

    int currentFreqProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tens);

        /** Initialize current freq progress */
        currentFreqProgress=0;

        /** Get TextView, SeekBar, and Button */
        TextView freqLow = (TextView) findViewById(R.id.freqVal);
        SeekBar freqSeekBar = (SeekBar) findViewById(R.id.freqSeekBar);
        Button aboutButton = (Button) findViewById(R.id.TENSHelpButton);

        /** Set Low and High values in the text boxes by the sliders */
        freqLow.setText(""+FREQUENCY_LOW);

        /** Set Maxes for the SeekBars based on the high values for each */
        freqSeekBar.setMax(FREQUENCY_HIGH-FREQUENCY_LOW);

        /** Create onClickListener for the Frequency SeekBar */
        freqSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /** Registers changes to the Magnitude SeekBar */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                currentFreqProgress=progressValue;
                updateFreqText();
                changeFrequency();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        /** Set onClickListener for the Help Button */
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AboutTENSIntent = new Intent(TENSActivity.this, AboutTENSActivity.class);
                startActivity(AboutTENSIntent);
            }
        });
    }

    /** Sends updated magnitude value to the arduino */
    public void changeFrequency() {
        //Put code for changing the frequency setting in the arduinio
    }

    /** Updates the TextView that displays the current frequency value */
    public void updateFreqText(){
        TextView freqVal = (TextView) findViewById(R.id.freqVal);
        int freqValue = currentFreqProgress + FREQUENCY_LOW;
        freqVal.setText(""+freqValue);
    }
}
