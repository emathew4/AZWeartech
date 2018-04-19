package com.azweartech.azweartech;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.azweartech.azweartech.R;
import com.picard.home.picardapi.Picard;
import com.picard.home.picardapi.PicardRequestQue;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotPasswordActivity extends AppCompatActivity {

    /**
     * Keep track of the password reset task to ensure we can cancel it if requested.
     */
    private ForgotPasswordActivity.ForgotPasswordTask mAuthTask = null;

    // UI references.
    private EditText mEmailView;
    private View mProgressView, mPwdResetFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set up the create account view
        setContentView(R.layout.activity_forgot_password);

        /** set up the create account form*/
        mEmailView = (EditText) findViewById(R.id.email);
        mPwdResetFormView = findViewById(R.id.pwd_reset_form);
        mProgressView = findViewById(R.id.pwd_reset_progress);
    }

    public void resetPassword(View view){
        if (mAuthTask != null) {
            return;
        }

        // Store values at the time of the create account attempt.
        String email = mEmailView.getText().toString();
        mPwdResetFormView = findViewById(R.id.pwd_reset_form);
        mProgressView = findViewById(R.id.pwd_reset_progress);

        // Reset errors.
        mEmailView.setError(null);

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt account creation and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user account creation attempt.
            showProgress(true);
            mAuthTask = new ForgotPasswordActivity.ForgotPasswordTask(email);
        }


    }


    /**
     * Represents an asynchronous password reset task used to send email token w/ password reset
     */
    public class ForgotPasswordTask {

        JSONObject parameters;
        View focusView = null;

        ForgotPasswordTask(String email) {
            Map<String, String> params = new HashMap();
            params.put("email", email);
            parameters = new JSONObject(params);

            // Attempt password reset against Picard service
            PicardRequestQue helper = PicardRequestQue.getInstance();
            Picard request = new Picard
                    (Request.Method.POST,
                            "https://weartech.picard.io/auth/passwordReset",
                            parameters,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        // go to home screen which should make a call to access
                                        // their auth user model.
                                        Intent LoginIntent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                                        startActivity(LoginIntent);

                                    } catch (Exception e) {
                                        txtError(e);
                                    }
                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error.networkResponse.statusCode == 400){
                                // update the pwd reset view to indicate error
                                showProgress(false);
                                mEmailView.setError(getString(R.string.error_invalid_login));
                                focusView = mEmailView;
                                focusView.requestFocus();
                            }
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


    private boolean isEmailValid(String email) {
        //TODO: Replace this with our own logic
        return email.contains("@");
    }

    /**
     * Shows the progress UI and hides the password reset form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mPwdResetFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mPwdResetFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mPwdResetFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mPwdResetFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
