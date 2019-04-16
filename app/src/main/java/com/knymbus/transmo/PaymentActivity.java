package com.knymbus.transmo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.knymbus.transmo.Helper.Helper;
import com.knymbus.transmo.Helper.SystemInterface;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PaymentActivity extends AppCompatActivity {

    private TextView cardNumber;
    private TextView accountBalance;
    private Double accountCalBalance; //Available balance from the card
    private Button btnMakepayment;
    private Spinner paymentOptions;
    private double smarterCardBalance = 0.00;
    private String documentId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Payment Option");
        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorDarkStylishGreen));

//        Get all the objects from the design
        cardNumber = findViewById(R.id.tv_pmt_card_number);
        accountBalance = findViewById(R.id.tv_pmt_card_balance);
        btnMakepayment = findViewById(R.id.btn_pmt_make_payment);
        paymentOptions = findViewById(R.id.spin_pmt_options);

        getIncomingIntent();

        ArrayAdapter<String> myPaymentOptionSpinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.payment_amount));

        myPaymentOptionSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentOptions.setAdapter(myPaymentOptionSpinnerAdapter);
        paymentOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(getResources().getColor(R.color.colorDarkBlack)); //Change selected text color
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnMakepayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(PaymentActivity.this);

                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        dialog.cancel();


                    }
                });

                if(paymentOptions.getSelectedItem().toString().equals("--Select An Amount--")){
                    builder.setTitle(R.string.title_payment_option);
                    builder.setMessage(R.string.no_selection_made);
                    final AlertDialog dialog = builder.create();
                    dialog.show();
                }

                if(!paymentOptions.getSelectedItem().toString().equals("--Select An Amount--")){
                    boolean flag = false;
                    final double d = Double.valueOf(paymentOptions.getSelectedItem().toString());
                    if(accountCalBalance < 100 || d > accountCalBalance){
                        builder.setTitle(R.string.insufficient_funds);
                        builder.setMessage(R.string.insufficient_card_balance);
                        final AlertDialog dialog = builder.create();
                        dialog.show();
                        flag = true;
                    }

//                    All green from here we can make the necessary changes
                        if(!flag){

                            DocumentReference docRef = FirebaseFirestore.getInstance().collection(SystemInterface.CardTransaction.smarterCardInfo)
                                    .document(SystemInterface.UserData.uid);
                            smarterCardBalance += d;
                            Map<String, Object>updateObj = new HashMap<>();
                            updateObj.put("balance",smarterCardBalance);
                            docRef.update(updateObj).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
//                                    Toast.makeText(PaymentActivity.this, "Smarter Card Updated Successfully",Toast.LENGTH_LONG).show();
                                    builder.setTitle(R.string.topup_transaction_success);
                                    builder.setMessage(R.string.topup_transaction_success_msg);
                                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // User clicked OK button
                                            dialog.cancel();
                                            finish();
                                        }
                                    });
                                    final AlertDialog dialog = builder.create();
                                    dialog.show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(PaymentActivity.this, "Something Went Wrong, Please try again",Toast.LENGTH_LONG).show();
                                }
                            });

//                            Now we need to reduce the balance on the card to show the balance changing in real time
                          DocumentReference cardsDdocRef = FirebaseFirestore.getInstance().collection(SystemInterface.CardTransaction.paymentMethod)
                                    .document(documentId);
//                          Update the account balance here
                          double newcardBalance = accountCalBalance - d;
                            Map<String, Object>updateCardObj = new HashMap<>();
                            updateCardObj.put("balance",String.valueOf(newcardBalance));
                            cardsDdocRef.update(updateCardObj);
                        }

                }
            }
        });
    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("accountNumber") && getIntent().hasExtra("accountBalance")
                && getIntent().hasExtra("smarterCardBalance") && getIntent().hasExtra("documentId")){
            cardNumber.setText(getIntent().getStringExtra("accountNumber"));
            accountCalBalance = Double.valueOf(getIntent().getStringExtra("accountBalance"));
            smarterCardBalance = getIntent().getDoubleExtra("smarterCardBalance", 0.0);
            documentId = getIntent().getStringExtra("documentId");

            accountBalance.setText(Helper.stringBuilder("%s",Helper.formatToMoney(accountCalBalance)));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }



}
