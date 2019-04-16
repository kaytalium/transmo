package com.knymbus.transmo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.knymbus.transmo.Helper.Helper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String activeUser = "John";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        setActivityTitle(R.string.window_route);

        TextView txtWelcome = findViewById(R.id.welcome);
//        creating the three buttons from design
        RelativeLayout btnRoute, btnBalance, btnTopup;

//        set object var
        btnRoute = findViewById(R.id.layout_track_bus);
        btnBalance = findViewById(R.id.layout_check_balance);
        btnTopup = findViewById(R.id.layout_topup);

//        create on click listener for route
        btnRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RouteActivity.class);
                startActivity(i);
            }
        });

//        create on click listener for Card balances
        btnBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ManageCardBalanceActivity.class);
                startActivity(i);
            }
        });

//        create on click listener for Card balances
        btnTopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TopupActivity.class);
                startActivity(i);
            }
        });

        txtWelcome.setText(Helper.stringBuilder("Welcome %s what are you doing today?",activeUser));



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //        Call the list of active bus and display info in recycler view in main window
//
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
        getMenuInflater().inflate(R.menu.main, menu);
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

        if (id == R.id.nav_route) {
           Intent i = new Intent(getApplicationContext(), RouteActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_ticket) {
            Intent i = new Intent(getApplicationContext(), DigitalBusPassActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_smarter_card) {
            Intent i = new Intent(getApplicationContext(), TopupActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_about) {
            Intent i = new Intent(getApplicationContext(), AboutActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setActivityTitle(int title){
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(title);

//            this remove the arrow used with activity and fragment
//             getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }
}
