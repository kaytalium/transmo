package com.knymbus.transmo.SmarterCard;

import android.content.Context;
import android.util.Log;

import com.knymbus.transmo.FirestoreConnection.FirestoreConnection;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SmarterCardEngine {

    //    Global recycler
    private RecyclerView rv;
    private Context $context;
    private TransactionAdapter cardTransactionAdapter;
    private  WalletAdapter walletAdapter;

    public SmarterCardEngine(RecyclerView rv, Context $contect) {
        this.rv = rv;
        this.$context = $contect;
    }

    public SmarterCardEngine() {}

    public void start(){
        //        create adapter
        cardTransactionAdapter = new TransactionAdapter(FirestoreConnection.TransactionDataManager.CardTransactionQuery(), $context);

//         configure recyclerview
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager($context));
        rv.setAdapter(cardTransactionAdapter);

        cardTransactionAdapter.startListening();
    }

    public void stop(){
        cardTransactionAdapter.stopListening();
    }

    /**
     * Handle all the Payment Method QUERY HERE
     */
    public void startWallet(String uid){
        //        create adapter
        walletAdapter = new WalletAdapter(FirestoreConnection.WalletManager.CardQuery(uid), $context);

        Log.d("Ovel", "SmarterCardEngine line 50");
//         configure recyclerview
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager($context));
        rv.setAdapter(walletAdapter);

        walletAdapter.startListening();
    }

    public void stopWallet(){
        walletAdapter.stopListening();
    }
}
