package com.knymbus.transmo.FirestoreConnection;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;
import com.knymbus.transmo.Helper.SystemInterface;
import com.knymbus.transmo.SmarterCard.PaymentMethod;

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
                .orderBy("creationDate", Query.Direction.ASCENDING);



        /* Get Query result from Firebase Firestore database */
        return new FirestoreRecyclerOptions.Builder<PaymentMethod>()
                .setQuery(query, PaymentMethod.class)
                .build();
    }
}
