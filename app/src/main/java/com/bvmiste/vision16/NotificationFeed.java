package com.bvmiste.vision16;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;


import com.bvmiste.vision16.App.AppConfig;
import com.bvmiste.vision16.Helper.SQLiteHandler;
import com.parse.ParseAnalytics;
import com.parse.ParsePush;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;


public class NotificationFeed extends AppCompatActivity {

    //Creating Views
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private SQLiteHandler db = new  SQLiteHandler(this);


    AppConfig config;
    String status="No";
    String email="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
        setContentView(R.layout.activity_notification_feed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getStatusDetails();

         status = user.get("name");
         email = user.get("email");
        // Log.d("Status",status);



        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        //Calling method to get data

        if("Yes".equals(status))
        {
            Log.d("Status","In if block");
            ParsePush.subscribeInBackground("Registered");
            getData();
        }
        else
        {

            Log.d("Status","In else block");
            getData1();
        }

    }

    //This method will get data from the web api
    private void getData1(){
        class GetData extends AsyncTask<Void,Void,String> {
            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(NotificationFeed.this, "Fetching Data", "Please wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                parseJSON(s);
                showData();
            }

            @Override
            protected String doInBackground(Void... params) {
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(AppConfig.GET_URL1);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }
            }
        }
        GetData gd = new GetData();
        gd.execute();
    }

    private void getData(){
        class GetData extends AsyncTask<Void,Void,String> {
            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(NotificationFeed.this, "Fetching Data", "Please wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                parseJSON(s);
                showData();
            }

            @Override
            protected String doInBackground(Void... params) {
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(AppConfig.GET_URL);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }
            }
        }
        GetData gd = new GetData();
        gd.execute();
    }

    public void showData(){
        adapter = new CardAdapter(AppConfig.titles,AppConfig.msgs);
        recyclerView.setAdapter(adapter);
    }

    private void parseJSON(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = jsonObject.getJSONArray(AppConfig.TAG_JSON_ARRAY);

            config = new AppConfig(array.length());

            for(int i=0; i<array.length(); i++){
                JSONObject j = array.getJSONObject(i);
                AppConfig.titles[i] = getName(j);
                AppConfig.msgs[i] = getURL(j);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private String getName(JSONObject j){

            String name = null;
            try {
                name = j.getString(AppConfig.TAG_IMAGE_NAME);

            } catch (JSONException e) {
                e.printStackTrace();
            }
           return name;

    }

    private String getURL(JSONObject j){
        String url = null;
        try {
            url = j.getString(AppConfig.TAG_IMAGE_URL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return url;
    }

}
