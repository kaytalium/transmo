package com.knymbus.transmo.SmarterCard;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.knymbus.transmo.Helper.Helper;
import com.knymbus.transmo.PaymentActivity;
import com.knymbus.transmo.R;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

class WalletAdapter extends FirestoreRecyclerAdapter<PaymentMethod, WalletAdapter.CardHolder> {

    private Context $context;
    private double smarterCardBalance;

    public WalletAdapter(@NonNull FirestoreRecyclerOptions<PaymentMethod> options, Context $contect, double smarterCardBalance) {
        super(options);
        this.$context = $contect;
        this.smarterCardBalance = smarterCardBalance;

    }

    @Override
    protected void onBindViewHolder(@NonNull CardHolder cardHolder, int i, @NonNull final PaymentMethod paymentMethod) {

       cardHolder.accountType.setText(paymentMethod.getAccountType());
       cardHolder.cardHolder.setText(paymentMethod.getAccountName());

       cardHolder.cardType.setText(paymentMethod.getCardType());

//       perform some clean up action here
        cardHolder.bankName.setText(Helper.splitString(paymentMethod.getBankName()));

//        mask the account number and only show the last 4 digits
        cardHolder.cardNumber.setText(Helper.maskedCardNumber(paymentMethod.getCardNumber()));

        cardHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText($context, "hey im a "+paymentMethod.getCardType(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent($context, PaymentActivity.class);
                intent.putExtra("accountNumber", Helper.maskedCardNumber(paymentMethod.getCardNumber()));
                intent.putExtra("accountBalance",paymentMethod.getBalance());
                intent.putExtra("smarterCardBalance", smarterCardBalance);
                intent.putExtra("documentId", paymentMethod.getDocId());

                Log.d("Ovel", "smarterCardBalance: "+smarterCardBalance);

                $context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_topup_payments, parent,false);
        return new CardHolder(v);
    }

    public class CardHolder extends RecyclerView.ViewHolder {

        TextView bankName;
        TextView accountType;
        TextView cardHolder;
        TextView cardNumber;
        TextView cardType;
        CardView parentLayout;

        public CardHolder(@NonNull View itemView) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.parentOfWalletItem);
            bankName = itemView.findViewById(R.id.topup_bank_name);
            accountType = itemView.findViewById(R.id.tv_topup_card_account_type);
            cardHolder = itemView.findViewById(R.id.tv_topup_owner_name);
            cardNumber = itemView.findViewById(R.id.tv_topup_card_number);
            cardType = itemView.findViewById(R.id.tv_card_account_card_type);
        }


    }
}
