package com.bvmiste.vision16;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bvmiste.vision16.Helper.ParseRegistration;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.SaveCallback;

public class Feedback extends AppCompatActivity {
    private AppCompatButton btnRegister;
    private EditText name;
    private EditText eventname;
    private EditText clg;
    private ProgressDialog pDialog;
    private String s;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        name = (EditText) findViewById(R.id.name);
        eventname = (EditText) findViewById(R.id.eventname);
        clg = (EditText) findViewById(R.id.clg);
        btnRegister = (AppCompatButton) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                signup();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    void signup()
    {
        if (!validate()) {
            onSignupFailed("Invalid Inputs" );
            return;
        }

        btnRegister.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(Feedback.this, R.style.AppTheme_Base_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(" Submitting your valuable comments....");
        progressDialog.show();

        final String Name = name.getText().toString();
        final String Clg = clg.getText().toString();
        final String event = eventname.getText().toString();


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess(Name,Clg,event);
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 1500);
    }
    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email,users, password) to register url
     * */


    public void onSignupSuccess(final String name, final String Clg,final String event ) {

        ParseRegistration registration =new ParseRegistration();
        registration.getname();
        registration.setname(name);
        registration.getclg();
        registration.setclg(Clg);
        registration.getevent();
        registration.setevent(event);

        ParseACL acl = new ParseACL();
        acl.setPublicReadAccess(true);
        registration.setACL(acl);

        registration.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    onSignupFailed(e.getMessage());
                } else {
                    Toast.makeText(getBaseContext(), "Feedback Stored.!! Thanks for it", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), Dashboard.class);
                    startActivity(i);
                }
            }
        });

    }
    public void onSignupFailed(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
        btnRegister.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String names = name.getText().toString();
        String event = eventname.getText().toString();
        String clgs = clg.getText().toString();


        if (names.isEmpty() || names.length() < 3 || isNumericString(names)){
            name.setError("At least 3 characters and not numbers");
            valid = false;
        } else {
            name.setError(null);
        }

        if (event.isEmpty()) {
            eventname.setError("Enter an email id");
            valid = false;
        } else {
            eventname.setError(null);
        }

        if (clgs.isEmpty()) {
            clg.setError("Enter a message please");
            valid = false;
        } else {
            clg.setError(null);
        }

        return valid;
    }
    public static boolean isNumericString(String input) {
        boolean result = false;

        if(input != null && input.length() > 0) {
            char[] charArray = input.toCharArray();

            for(char c : charArray) {
                if(c >= '0' && c <= '9') {
                    // it is a digit
                    result = true;
                } else {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }


}
