package com.knymbus.transmo.FirestoreConnection;

import android.util.Log;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;
import com.knymbus.transmo.Helper.SystemInterface;
import com.knymbus.transmo.SmarterCard.PaymentMethod;

import java.util.HashMap;
import java.util.Map;

public class PaymentMethodDataManager extends FirestoreManager {

    public PaymentMethodDataManager() {
        super(SystemInterface.CardTransaction.paymentMethod);
    }

    public PaymentMethodDataManager(String collectionRefName) {
        super(collectionRefName);
    }

    /**
     * Get the full list from the database node
     *
     */
    public FirestoreRecyclerOptions<PaymentMethod> CardQuery(String uid){

        Query query = this.getCollectionRef()
                .whereEqualTo("uid",uid)
                .orderBy("dateCreated", Query.Direction.ASCENDING);



        /* Get Query result from Firebase Firestore database */
        return new FirestoreRecyclerOptions.Builder<PaymentMethod>()
                .setQuery(query, PaymentMethod.class)
                .build();
    }

    public void create(PaymentMethod paymentMethod){
        this.getCollectionRef().add(paymentMethod).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                String pushKey = documentReference.getId();
//
        Log.d("Ovel", "push key information: "+pushKey);
                Map<String, Object> docIdObj = new HashMap<>();
                docIdObj.put("docId",pushKey);
                documentReference.update(docIdObj);
            }
        });
    }
}
