package com.knymbus.transmo.Routes;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.knymbus.transmo.FirestoreConnection.FirestoreConnection;


/**
 * Route Engine
 * @Creator Ovel Heslop
 * @Created date: April 11, 2019
 * @description: This engine will handle all the various calls made for search on the
 * bus route for this app.
 */
public class RouteEngine {

//    Global recycler
    private RecyclerView rv;
    private Context $contect;
    private  ActiveBusAdapter activeBusAdapter;


    public RouteEngine(RecyclerView rv, Context $contect) {
        this.rv = rv;
        this.$contect = $contect;
    }

    public RouteEngine() {}

    public void start(){
        //        create adapter
        activeBusAdapter = new ActiveBusAdapter(FirestoreConnection.BusDataManager.ActiveBusQuery(), $contect);

//         configure recyclerview
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager($contect));
        rv.setAdapter(activeBusAdapter);

        activeBusAdapter.startListening();
    }

    public void stop(){
        activeBusAdapter.stopListening();
    }

    public void search(String searchText){

        String[] searchHelpers = new String[]{"Half Way Tree", "Down Town kingston", "Mountain View"};


        activeBusAdapter = new ActiveBusAdapter(FirestoreConnection.BusDataManager.SearchResultOfActiveBusQuery(searchText), $contect);
        rv.setAdapter(activeBusAdapter);
        activeBusAdapter.notifyDataSetChanged();
        activeBusAdapter.startListening();

    }


}
