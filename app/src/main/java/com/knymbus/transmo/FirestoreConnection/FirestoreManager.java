package com.knymbus.transmo.FirestoreConnection;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * This class handles all the firebaseFirestore call and complex mutation of the data
 * then pass it on to the calling object.
 * the reason for this call is to have a centralize class to improve maintainability
 */
public class FirestoreManager {

    private FirebaseFirestore firestoreDB;
    private CollectionReference collectionRef;


    public FirestoreManager(){firestoreDB = FirebaseFirestore.getInstance();}

    public FirestoreManager(String collectionRefName){
        firestoreDB = FirebaseFirestore.getInstance();
        setCollectionRef(collectionRefName);
    }

    public void setCollectionRef(String collectionRefName) {
        this.collectionRef = firestoreDB.collection(collectionRefName);
    }

    public CollectionReference getCollectionRef(){
        return collectionRef;
    }
}
