package com.knymbus.transmo.SmarterCard;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.knymbus.transmo.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class WalletAdapter extends FirestoreRecyclerAdapter<PaymentMethod, WalletAdapter.CardHolder> {

    private Context $context;

    public WalletAdapter(@NonNull FirestoreRecyclerOptions<PaymentMethod> options, Context $contect) {
        super(options);
        this.$context = $contect;

    }

    @Override
    protected void onBindViewHolder(@NonNull CardHolder cardHolder, int i, @NonNull PaymentMethod paymentMethod) {
       cardHolder.bankName.setText(paymentMethod.getBankName());
       cardHolder.cardType.setText(paymentMethod.getAccountType());
       cardHolder.cardHolder.setText(paymentMethod.getAccountName());
       cardHolder.cardNumber.setText(paymentMethod.getCardNumber());
       cardHolder.subType.setText(paymentMethod.getSubType());
    }

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_topup_payments, parent,false);
        return new CardHolder(v);
    }

    public class CardHolder extends RecyclerView.ViewHolder {

        TextView bankName;
        TextView cardType;
        TextView cardHolder;
        TextView cardNumber;
        TextView subType;

        public CardHolder(@NonNull View itemView) {
            super(itemView);

            bankName = itemView.findViewById(R.id.topup_bank_name);
            cardType = itemView.findViewById(R.id.tv_topup_card_account_type);
            cardHolder = itemView.findViewById(R.id.tv_topup_owner_name);
            cardNumber = itemView.findViewById(R.id.tv_topup_card_number);
            subType = itemView.findViewById(R.id.tv_card_account_subtype);
        }
    }
}
