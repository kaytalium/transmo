package com.knymbus.transmo;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.knymbus.transmo.Helper.SystemInterface;
import com.knymbus.transmo.SmarterCard.SmarterCardEngine;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.WindowManager;

public class TopupActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Top Up");
        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorFunkyDarkBlue));

//        Get the information from the database and load same to screen
        RecyclerView rvCardsInWallet = findViewById(R.id.rv_cards_in_wallet);
        SmarterCardEngine smarterCardEngine = new SmarterCardEngine(rvCardsInWallet, this);

        smarterCardEngine.startWallet(SystemInterface.UserData.uid);

//        Floating btton to add a new card to wallet
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CardManagerActivity.class);
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
