package com.bvmiste.vision16;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bvmiste.vision16.Helper.ParseLogin;
import com.bvmiste.vision16.Helper.SQLiteHandler;
import com.bvmiste.vision16.Helper.SessionManager;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.SaveCallback;

public class Login extends AppCompatActivity {
    private AppCompatButton btnLogin;
    private EditText enrollment;
    private ProgressDialog pDialog;
    private String s;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(Login.this, Dashboard.class);
            startActivity(intent);
            finish();
        }
        enrollment= (EditText) findViewById(R.id.enrollmentno);

        btnLogin = (AppCompatButton) findViewById(R.id.btnlogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                signup();
            }
        });
    }

    void signup()
    {
        if (!validate()) {
            onSignupFailed("Invalid Inputs" );
            return;
        }

        btnLogin.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(Login.this, R.style.AppTheme_Base_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Logging you up....");
        progressDialog.show();

        final String No = enrollment.getText().toString();


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess(No);
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 1500);
    }
    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email,users, password) to register url
     * */


    public void onSignupSuccess(final String no) {

        final String name=no;
        final ParseLogin login =new ParseLogin();
        login.getno();
        login.setno(no);

        ParseACL acl = new ParseACL();
        acl.setPublicReadAccess(true);
        login.setACL(acl);

        login.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    onSignupFailed(e.getMessage());
                } else {
                    login.pinInBackground();
                    db.addUser(name);
                    session.setLogin(true);
                    Toast.makeText(getBaseContext(), "Succesfully Stored", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(),Dashboard.class);
                    startActivity(i);
                }
            }
        });

    }
    public void onSignupFailed(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
        btnLogin.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String no = enrollment.getText().toString();
        int number = 0;

        try {

            number = Integer.parseInt(no.toString());

        } catch(NumberFormatException e) {
            System.out.println("parse value is not valid : " + e);
        }

        if (no.isEmpty()  ){
            enrollment.setError("12 digit numbers and not characters");
            valid = false;
        } else {
            enrollment.setError(null);
        }
        return valid;
    }
    public static boolean isNumeric(String str)
    {
        for (char c : str.toCharArray())
        {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }




}


