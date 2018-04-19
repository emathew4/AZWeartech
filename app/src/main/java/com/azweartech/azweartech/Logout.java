package com.azweartech.azweartech;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.azweartech.azweartech.R;
import com.picard.home.picardapi.Picard;
import com.picard.home.picardapi.PicardRequestQue;

import org.json.JSONObject;

public class Logout extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Log the user out
        PicardRequestQue helper = PicardRequestQue.getInstance();
        Picard request = new Picard
                (Request.Method.GET,
                        "https://weartech.picard.io/auth/logout",
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Intent Login = new Intent(Logout.this, LoginActivity.class);
                                    startActivity(Login);
                                } catch (Exception e) {
                                    txtError(e);
                                }
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // if this happens the user account isn't available.
                        // We should log them out.
                        txtError(error);
                    }
                });

        request.setPriority(Request.Priority.HIGH);
        helper.add(request);
    }


    private void txtError(Exception e) {
        e.printStackTrace();
    }

}
