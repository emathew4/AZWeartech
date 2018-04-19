package com.azweartech.azweartech;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** Set OnClickListener for Logout Button */
        TextView logout = (TextView) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LogoutIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(LogoutIntent);
            }
        });

        /** Set OnClickListener for PEMF */
        TextView pemf = (TextView) findViewById(R.id.pemf);
        pemf.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent PEMFIntent = new Intent(MainActivity.this, PEMFActivity.class);
                startActivity(PEMFIntent);
            }
        });

        /** Set OnClickListener for TENS */
        TextView tens = (TextView) findViewById(R.id.tens);
        tens.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent TENSIntent = new Intent(MainActivity.this, TENSActivity.class);
                startActivity(TENSIntent);
            }
        });

        /** Set OnClickListener for About button */
        TextView about = (TextView) findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent AboutIntent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(AboutIntent);
            }
        });
    }
}