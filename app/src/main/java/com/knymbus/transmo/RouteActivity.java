package com.knymbus.transmo;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.knymbus.transmo.Routes.RouteEngine;

public class RouteActivity extends AppCompatActivity {

    String[] searchHelpers = new String[]{"Half Way Tree", "Down Town Kingston", "Mountain View"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


//        init main Recyclerview and start the route engine
        RecyclerView rv = findViewById(R.id.rv_route_search_result);

//        get route engine
        final RouteEngine routeEngine = new RouteEngine(rv,getApplicationContext());

//        start route engine
        routeEngine.start();

        AutoCompleteTextView mainSearch = toolbar.findViewById(R.id.origin_location_search);
        mainSearch.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, searchHelpers));

        mainSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    routeEngine.stop();
                    routeEngine.search(v.getText().toString());
//
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

}
