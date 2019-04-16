package com.knymbus.transmo.SmarterCard;

import android.content.Context;
import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.knymbus.transmo.FirestoreConnection.FirestoreConnection;
import com.knymbus.transmo.Helper.SystemInterface;


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
    public void startWallet(String uid, double smarterCardBalance){
        //        create adapter
        walletAdapter = new WalletAdapter(FirestoreConnection.WalletManager.CardQuery(uid), $context, smarterCardBalance);

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

    public void addToWallet(PaymentMethod paymentMethod){
        DocumentReference docRef = FirebaseFirestore.getInstance().collection(SystemInterface.CardTransaction.paymentMethod).document();
//        String pushKey = docRef.;
//
//        Log.d("Ovel", "push key information: "+pushKey);
//        paymentMethod.setDocId(pushKey);

        FirestoreConnection.WalletManager.create(paymentMethod);
    }

    public void restartWallet() {
        walletAdapter.notifyDataSetChanged();
    }
}
