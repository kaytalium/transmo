package com.knymbus.transmo.FirestoreConnection;

import android.util.Log;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;
import com.knymbus.transmo.Helper.SystemInterface;
import com.knymbus.transmo.Routes.ActiveBus;

/**
 * This class is use to handle all the data that is needed from the database for
 * all action on the node and related nodes
 * the class is initialized with the parent node "active_bus_group"
 * user of this class can however init class with their own nodes
 */
public class ActiveBusDataManager extends FirestoreManager {

    /**
     * init with parent node
     */
    public ActiveBusDataManager() {super(SystemInterface.ActiveBusGroup.parentNode);}


    /**
     * Allow users init with own node label
     * @param collectionRefName
     */
    public ActiveBusDataManager(String collectionRefName) {super(collectionRefName);}



    /**
     * Get the full list from the database node
     *
     */
    public FirestoreRecyclerOptions<ActiveBus> ActiveBusQuery(){

        Query query = this.getCollectionRef()
                .whereEqualTo("runningFlag",true)
                .orderBy("origin.actualDepartureTime", Query.Direction.ASCENDING);



        /* Get Query result from Firebase Firestore database */
        return new FirestoreRecyclerOptions.Builder<ActiveBus>()
                .setQuery(query, ActiveBus.class)
                .build();
    }

    public FirestoreRecyclerOptions<ActiveBus> SearchResultOfActiveBusQuery(String searchText){

        Query query = null;

        if(searchText.length() < 6){
            query = this.getCollectionRef()
                    .whereEqualTo("runningFlag",true)
                    .orderBy("busNumber").startAt(searchText).endAt(searchText+"\uf8ff");

        }

        if(searchText.length() > 5){
            query = this.getCollectionRef()
                    .whereEqualTo("runningFlag",true)
                    .orderBy("destination.code").startAt(searchText).endAt(searchText+"\uf8ff");

        }



        /* Get Query result from Firebase Firestore database */
        return new FirestoreRecyclerOptions.Builder<ActiveBus>()
                .setQuery(query, ActiveBus.class)
                .build();
    }


}
