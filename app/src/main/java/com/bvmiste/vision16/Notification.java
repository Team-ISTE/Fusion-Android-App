package com.bvmiste.vision16;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bvmiste.vision16.App.AppConfig;
import com.bvmiste.vision16.Helper.SQLiteHandler;
import com.parse.ParseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Notification extends AppCompatActivity {
    // LogCat tag
    private static final String TAG = Notification.class.getSimpleName();
    private AppCompatButton btnRegister;
    public EditText inputNo ;
    public EditText inputEmail;
    private android.support.v7.widget.AppCompatButton btnforgot;
    private SQLiteHandler db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());


        inputEmail = (EditText) findViewById(R.id.email1);
        inputNo = (EditText) findViewById(R.id.no1);
        btnforgot=(AppCompatButton) findViewById(R.id.btnRegister);

        btnforgot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                forgot();
            }
        });
    }

    /**
     * function to verify email details
     * */
    public void forgot()
    {
        if (!validate()) {
            onforgotFailed("Invalid Input");
            return;
        }
        btnforgot.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(Notification.this, R.style.AppTheme_Base_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Checking with backend be patient...");
        progressDialog.show();

        final String email = inputEmail.getText().toString().trim();
        final String no = inputNo.getText().toString().trim();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onforgotSuccess(no,email);
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 4000);
    }
    /**
     * function to verify login details entered
     * */
    public boolean validate() {
        boolean valid = true;

        String email = inputEmail.getText().toString().trim();
        String no = inputNo.getText().toString().trim();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputEmail.setError("Enter a valid email address");
            valid = false;
        } else {
            inputEmail.setError(null);
        }

        if (no.isEmpty()) {
            inputNo.setError("Enter a valid receipt no");
            valid = false;
        } else {
            inputNo.setError(null);
        }
        return valid;
    }
    //forgot sucesss
    public void onforgotSuccess(final String no, final String email) {
        // Tag used to cancel the request
        String tag_string_req = "req_notification";
        final ProgressDialog progressDialog = new ProgressDialog(Notification.this, R.style.AppTheme_Base_Dialog);
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d(TAG, "Notification Response: " + response.toString());
                progressDialog.dismiss();

                try {

                    JSONObject jObj =new JSONObject(response);
                 //   Log.d(TAG,"error");
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        // User successfully stored in MySQL
                        // Launch login activity
                        String name = jObj.getString("status");
                        String email = jObj.getString("email");
                        //adding status to sqlite
                        db.addStatus(name,email);
                        btnforgot.setEnabled(true);
                        Toast.makeText(getApplicationContext(), "Registration Done Thank you Now you can see all events notification in notification feed ", Toast.LENGTH_LONG).show();
                        Intent i=new Intent(Notification.this,NotificationFeed.class);
                        startActivity(i);
                        finish();

                    } else {
                        // Error occurred in forgot method. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        onforgotFailed(errorMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Forgot Error: " + error.getMessage());
                onforgotFailed(error.getMessage());
                progressDialog.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to forgot url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "login");
                params.put("no", no);
                params.put("email", email);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(strReq);


    }
    public void onforgotFailed(String msg) {
        Toast.makeText(getBaseContext(),msg, Toast.LENGTH_LONG).show();
        btnforgot.setEnabled(true);
    }


}
