package com.knymbus.transmo;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.knymbus.transmo.Helper.Helper;
import com.knymbus.transmo.Helper.SystemInterface;
import com.knymbus.transmo.SmarterCard.SmarterCardEngine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class TopupActivity extends AppCompatActivity {

    SmarterCardEngine smarterCardEngine;
    TextView smarterCardBal;
    RecyclerView rvCardsInWallet;

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

//        get the cureent balance of the smarter card
       smarterCardBal = findViewById(R.id.txt_topup_balance);

//        Get the information from the database and load same to screen
       rvCardsInWallet = findViewById(R.id.rv_cards_in_wallet);

        init();

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

    private void init(){
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("smarter_card_info").document("2LFVBak6IibH8JptRaH5oKNJzk62");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        double smarterCardBalance =  Double.valueOf(document.getData().get("balance").toString());
                        smarterCardBal.setText(Helper.stringBuilder("%s",Helper.formatToMoney(smarterCardBalance)));

//                        now that we have the smarter card information we can now show the cards in our wallet information
                        smarterCardEngine = new SmarterCardEngine(rvCardsInWallet, TopupActivity.this);

                        smarterCardEngine.startWallet(SystemInterface.UserData.uid, smarterCardBalance);

                    } else {
                        Log.d("Ovel", "No such document");
                    }
                } else {
                    Log.d("Ovel", "get failed with ", task.getException());
                }
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        smarterCardEngine.restartWallet();
        init();
    }
}
