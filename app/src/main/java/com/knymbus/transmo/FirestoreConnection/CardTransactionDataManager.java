package com.knymbus.transmo.FirestoreConnection;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;
import com.knymbus.transmo.Helper.SystemInterface;
import com.knymbus.transmo.SmarterCard.CardTransaction;

public class CardTransactionDataManager extends FirestoreManager {

    public CardTransactionDataManager() {
        super(SystemInterface.CardTransaction.parentNode);
    }

    public CardTransactionDataManager(String collectionRefName) {
        super(collectionRefName);
    }

    /**
     * Get the full list from the database node
     *
     */
    public FirestoreRecyclerOptions<CardTransaction> CardTransactionQuery(){

        Query query = this.getCollectionRef()
                .orderBy("date", Query.Direction.ASCENDING);



        /* Get Query result from Firebase Firestore database */
        return new FirestoreRecyclerOptions.Builder<CardTransaction>()
                .setQuery(query, CardTransaction.class)
                .build();
    }

}
