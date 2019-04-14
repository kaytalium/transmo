package com.knymbus.transmo.SmarterCard;

import android.content.Context;

import com.knymbus.transmo.FirestoreConnection.FirestoreConnection;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SmarterCardEngine {

    //    Global recycler
    private RecyclerView rv;
    private Context $contect;
    private TransactionAdapter cardTransactionAdapter;


    public SmarterCardEngine(RecyclerView rv, Context $contect) {
        this.rv = rv;
        this.$contect = $contect;
    }

    public SmarterCardEngine() {}

    public void start(){
        //        create adapter
        cardTransactionAdapter = new TransactionAdapter(FirestoreConnection.TransactionDataManager.CardTransactionQuery(), $contect);

//         configure recyclerview
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager($contect));
        rv.setAdapter(cardTransactionAdapter);

        cardTransactionAdapter.startListening();
    }

    public void stop(){
        cardTransactionAdapter.stopListening();
    }
}
