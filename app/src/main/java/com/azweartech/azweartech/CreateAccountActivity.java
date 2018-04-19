package com.azweartech.azweartech;
import com.azweartech.azweartech.R;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.picard.home.picardapi.Picard;
import com.picard.home.picardapi.PicardRequestQue;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountActivity extends AppCompatActivity {

    /**
     * Keep track of the create account task to ensure we can cancel it if requested.
     */
    private CreateAccountActivity.UserAccountCreationTask mAuthTask = null;

    // UI references.
    private EditText mPasswordView, mUsernameView, mEmailView;
    private View mProgressView, mCreateAccountFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set up the create account view
        setContentView(R.layout.activity_create_account);

        /** set up the create account form*/
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mUsernameView = (EditText) findViewById(R.id.username);
        mCreateAccountFormView = findViewById(R.id.account_creation_progress);
        mProgressView = findViewById(R.id.login_progress);
    }

    public void createAccount(View view){
        if (mAuthTask != null) {
            return;
        }

        // Store values at the time of the create account attempt.
        String username = mUsernameView.getText().toString();
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        mCreateAccountFormView = findViewById(R.id.create_account_form);
        mProgressView = findViewById(R.id.account_creation_progress);

        // Reset errors.
        mUsernameView.setError(null);
        mEmailView.setError(null);
        mPasswordView.setError(null);

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        } else if (!isUsernameValid(username)) {
            mUsernameView.setError(getString(R.string.error_invalid_username));
            focusView = mUsernameView;
            cancel = true;
        }

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
            mAuthTask = new CreateAccountActivity.UserAccountCreationTask(username, email, password);
        }


    }


    /**
     * Represents an asynchronous create user account task used to authenticate
     * the user.
     */
    public class UserAccountCreationTask {

        JSONObject parameters;
        View focusView = null;

        UserAccountCreationTask(String username, String email, String password) {
            Map<String, String> params = new HashMap();
            params.put("name", username);
            params.put("email", email);
            params.put("password", password);
            parameters = new JSONObject(params);

            // Attempt authentication against Picard service
            PicardRequestQue helper = PicardRequestQue.getInstance();
            Picard request = new Picard
                    (Request.Method.POST,
                            "https://weartech.picard.io/auth/user",
                            parameters,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        // go to home screen which should make a call to access
                                        // their auth user model.
                                        Intent LandingIntent = new Intent(CreateAccountActivity.this, MainActivity.class);
                                        startActivity(LandingIntent);

                                    } catch (Exception e) {
                                        txtError(e);
                                    }
                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error.networkResponse.statusCode == 400){
                                // update the create account view to indicate error logging in.
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


    private boolean isUsernameValid(String username) {
        //TODO: Replace this with our own logic
        return username.equals(username.toLowerCase());
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with our own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with our own logic
        return password.length() > 4;
    }


    /**
     * Shows the progress UI and hides the create account form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mCreateAccountFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mCreateAccountFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mCreateAccountFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mCreateAccountFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
