package com.knymbus.transmo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.knymbus.transmo.Helper.Helper;
import com.knymbus.transmo.Helper.SystemInterface;
import com.knymbus.transmo.SmarterCard.SmarterCardEngine;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

public class ManageCardBalanceActivity extends AppCompatActivity {

    TextView cardBalance;
    TextView cardNumber;
    RecyclerView rv;
    SmarterCardEngine routeEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_card_balance);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Card Balance");
        }

//        set the onclick for the topup button
        RelativeLayout topUpBtn = findViewById(R.id.layout_middle_top_up_button);
        topUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TopupActivity.class);
                startActivity(i);
            }
        });

//        set the current date
        TextView currentDate = findViewById(R.id.tv_current_date);
        currentDate.setText(Helper.DateFormatter(SystemInterface.DateTimeFormat.dateMonthYear, new Date()));

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorDarkBlue));


        cardBalance = findViewById(R.id.tv_bal);
        cardNumber = findViewById(R.id.card_number);

//        init main Recyclerview and start the route engine
        rv = findViewById(R.id.rv_transactions);

//        Start the app
        init();
    }

    private void init(){
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("smarter_card_info").document("2LFVBak6IibH8JptRaH5oKNJzk62");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        double num = Double.valueOf(document.getData().get("balance").toString());

                        cardBalance.setText(Helper.stringBuilder("%s",Helper.formatToMoney(num)));
                        cardNumber.setText(Helper.stringBuilder("%s",document.getData().get("cardNumber")));


//                          get route engine
                        routeEngine = new SmarterCardEngine(rv,getApplicationContext());

//                          start route engine
                        routeEngine.start();

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
        init();
    }

}
