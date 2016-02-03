package com.bvmiste.vision16;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;

import com.bvmiste.vision16.Branch.Civilhouse;
import com.bvmiste.vision16.Branch.Codehouse;
import com.bvmiste.vision16.Branch.ElectronHouse;
import com.bvmiste.vision16.Branch.FirstYear;
import com.bvmiste.vision16.Branch.MachineHouse;
import com.bvmiste.vision16.Branch.electrical;
import com.bvmiste.vision16.Helper.ParseLogin;
import com.bvmiste.vision16.Helper.ParseNotification;
import com.bvmiste.vision16.Helper.ParseRegistration;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.ParseObject;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParseObject.registerSubclass(ParseNotification.class);
        ParseObject.registerSubclass(ParseRegistration.class);
        ParseObject.registerSubclass(ParseLogin.class);



        GridView gridView = (GridView) findViewById(R.id.grid_view);
        // Instance of ImageAdapter Class
        gridView.setAdapter(new Eventimageadapter(this));
        /**
         * On Click event for Single Gridview Item
         * */
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if(position==0)
                {
                    Intent i = new Intent(getApplicationContext(), Civilhouse.class);
                    startActivity(i);
                }
                else if(position==1)
                {
                    Intent i = new Intent(getApplicationContext(), Codehouse.class);
                    startActivity(i);
                }
                else if(position==2)
                {
                    Intent i = new Intent(getApplicationContext(), ElectronHouse.class);
                    startActivity(i);
                }
                else if(position==3)
                {
                    Intent i = new Intent(getApplicationContext(), MachineHouse.class);
                    startActivity(i);
                }
                else if(position==4)
                {
                    Intent i = new Intent(getApplicationContext(), electrical.class);
                    startActivity(i);
                }
                else if(position==5)
                {
                    Intent i = new Intent(getApplicationContext(), FirstYear.class);
                    startActivity(i);

                }
                // Sending image id to FullScreenActivity

            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);


        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_civil) {
            Intent i = new Intent(getApplicationContext(), Civilhouse.class);
            startActivity(i);

        } else if (id == R.id.nav_code) {
            Intent i = new Intent(getApplicationContext(), Codehouse.class);
            startActivity(i);

        } else if (id == R.id.nav_electron) {
            Intent i = new Intent(getApplicationContext(), ElectronHouse.class);
            startActivity(i);

        } else if (id == R.id.nav_machine) {
            Intent i = new Intent(getApplicationContext(), MachineHouse.class);
            startActivity(i);

        }else if (id == R.id.nav_electrical) {
            Intent i = new Intent(getApplicationContext(), electrical.class);
            startActivity(i);

        }else if (id == R.id.nav_first) {
            Intent i = new Intent(getApplicationContext(), FirstYear.class);
            startActivity(i);

        }
        else if (id == R.id.nav_register) {
            Intent i = new Intent(getApplicationContext(), Feedback.class);
            startActivity(i);

        }
        else if (id == R.id.nav_notify1) {
            Intent i =new Intent(getApplicationContext(),TeamFusion.class);
            startActivity(i);
        }
        else if (id == R.id.nav_notify) {
            Intent i =new Intent(getApplicationContext(),Notification.class);
            startActivity(i);
        }
        else if (id == R.id.nav_notifyfeed){
            Intent i =new Intent(getApplicationContext(),NotificationFeed.class);
            startActivity(i);
        }
        else
        {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
