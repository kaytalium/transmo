package com.knymbus.transmo;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;
import com.knymbus.transmo.Helper.Helper;
import com.knymbus.transmo.Helper.SystemInterface;
import com.knymbus.transmo.SmarterCard.PaymentMethod;
import com.knymbus.transmo.SmarterCard.SmarterCardEngine;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Date;
import java.util.regex.Pattern;

public class CardManagerActivity extends AppCompatActivity {

    private final String space = "-"; // you can change this to whatever you want
    private final Pattern CODE_PATTERN = Pattern.compile("^(\\d{4}"+space+"{1}){0,3}\\d{1,4}$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_manager);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Adding A Card");
        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorDarkBlack));

        //         Getting the objects from view
        final EditText holderName = findViewById(R.id.edtv_name_on_card);

//        Credit Card input box logics
        final EditText creditCardeditText = (EditText) findViewById(R.id.edtv_card_number);
        creditCardeditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
               if (s.length() > 0 && !CODE_PATTERN.matcher(s).matches()) {
                    String input = s.toString();
                    String numbersOnly = keepNumbersOnly(input);
                    String code = formatNumbersAsCode(numbersOnly);

                    creditCardeditText.removeTextChangedListener(this);
                                        if(code.length() >19){
                        code = removeExtraDigit(code);
                    }
                    creditCardeditText.setText(code);
                    // You could also remember the previous position of the cursor
                    creditCardeditText.setSelection(code.length());
                    creditCardeditText.addTextChangedListener(this);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            private String keepNumbersOnly(CharSequence s) {
                return s.toString().replaceAll("[^0-9]", ""); // Should of course be more robust
            }

            private String formatNumbersAsCode(CharSequence s) {
                int groupDigits = 0;
                String tmp = "";
                for (int i = 0; i < s.length(); ++i) {
                    tmp += s.charAt(i);
                    ++groupDigits;
                    if (groupDigits == 4) {
                        tmp += "-";
                        groupDigits = 0;
                    }
                }
                return tmp;
            }

            private String removeExtraDigit(String s){
                   return s.substring(0, s.length() - 2);
            }
        });



//        card type selection
        final Spinner cardTypeSpinner = findViewById(R.id.spin_card_type);
        ArrayAdapter<String> mySpinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.card_type));

        mySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cardTypeSpinner.setAdapter(mySpinnerAdapter);
        cardTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(getResources().getColor(R.color.colorWhite)); //Change selected text color
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        Account type selection
        final Spinner accountTypeSpinner = findViewById(R.id.spin_account_type);
        ArrayAdapter<String> myAccountSpinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.sub_card_type));

        myAccountSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountTypeSpinner.setAdapter(myAccountSpinnerAdapter);
        accountTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(getResources().getColor(R.color.colorWhite)); //Change selected text color
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        Bank type selection
        final Spinner bankTypeSpinner = findViewById(R.id.spin_card_bank_type);
        ArrayAdapter<String> myBankSpinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.bank_type));

        myBankSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bankTypeSpinner.setAdapter(myBankSpinnerAdapter);
        bankTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(getResources().getColor(R.color.colorWhite)); //Change selected text color
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        Button to add new card
        Button btnAddCard = findViewById(R.id.add_card_btn);
        btnAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean hasError = false;
                if(holderName.getText().toString().length() < 6){
                    hasError = true;
                    holderName.setBackground(getDrawable(R.drawable.error_outline));
                    Snackbar.make(v, "Please provide a name", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                }else{
                    holderName.setBackground(getDrawable(R.drawable.new_card_outline));
                }

                if(creditCardeditText.getText().toString().length() < 19){
                    hasError = true;
                    creditCardeditText.setBackground(getDrawable(R.drawable.error_outline));
                    Snackbar.make(v, "Card number must be 16 digits", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                }else{
                    creditCardeditText.setBackground(getDrawable(R.drawable.new_card_outline));
                }

                if(cardTypeSpinner.getSelectedItem().toString().equals("--Select Card Type--")){
                    hasError = true;
                    cardTypeSpinner.setBackground(getDrawable(R.drawable.error_outline));
                    Snackbar.make(v, "Please Select a Card Type", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    cardTypeSpinner.setBackground(getDrawable(R.drawable.new_card_outline));
                }

                if(accountTypeSpinner.getSelectedItem().toString().equals("--Select Account Type--")){
                    hasError = true;
                    accountTypeSpinner.setBackground(getDrawable(R.drawable.error_outline));
                    Snackbar.make(v, "Please Select an Account Type", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    accountTypeSpinner.setBackground(getDrawable(R.drawable.new_card_outline));
                }

                if(bankTypeSpinner.getSelectedItem().toString().equals("--Select Bank--")){
                    hasError = true;
                    bankTypeSpinner.setBackground(getDrawable(R.drawable.error_outline));
                    Snackbar.make(v, "Please Select a Bank Type", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    bankTypeSpinner.setBackground(getDrawable(R.drawable.new_card_outline));
                }

                if(!hasError){

                    PaymentMethod cardDetail = new PaymentMethod();
                    cardDetail.setAccountName(holderName.getText().toString());
                    cardDetail.setCardNumber(creditCardeditText.getText().toString());
                    cardDetail.setCardType(cardTypeSpinner.getSelectedItem().toString());
                    cardDetail.setAccountType(accountTypeSpinner.getSelectedItem().toString());
                    cardDetail.setBankName(bankTypeSpinner.getSelectedItem().toString());
                    cardDetail.setBalance(Helper.generateCardBalance());
                    cardDetail.setUid(SystemInterface.UserData.uid);
                    cardDetail.setDateCreated(new Timestamp(new Date()));

                    final ProgressDialog builder = new ProgressDialog(CardManagerActivity.this);

                    builder.setMessage(getResources().getString(R.string.dialog_message));
                    builder.setTitle(R.string.dialog_title);
                    builder.show();

                    connectToBank(builder, cardDetail);

                }

            }
        });


//
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private void connectToBank(final ProgressDialog progressDialog, final PaymentMethod cardDetail){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
//
                progressDialog.setTitle(R.string.connect_to_bank_title);
                progressDialog.setMessage(getResources().getString(R.string.connection_mesg));

//                Do the database connection and update database here
                SmarterCardEngine smarterCardEngine = new SmarterCardEngine();
                smarterCardEngine.addToWallet(cardDetail);
//                once database is updated call the completed message box
                verificationComplete(progressDialog);
            }
        }, 5000);
    }

    private void verificationComplete(final ProgressDialog progressDialog){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                final AlertDialog.Builder builder = new AlertDialog.Builder(CardManagerActivity.this);
              builder.setTitle(R.string.verification_complete);
                builder.setMessage(R.string.verification_mesg);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        dialog.cancel();
                        finish();

                    }
                });
                progressDialog.cancel();
                final AlertDialog dialog = builder.create();
                dialog.show();
            }
        }, 8000);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

}
