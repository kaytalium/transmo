package com.knymbus.transmo.SmarterCard;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.knymbus.transmo.Helper.Helper;
import com.knymbus.transmo.Helper.SystemInterface;
import com.knymbus.transmo.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionAdapter extends FirestoreRecyclerAdapter<CardTransaction, TransactionAdapter.CardTransactionHolder> {


    private Context $context;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public TransactionAdapter(@NonNull FirestoreRecyclerOptions<CardTransaction> options, Context mContext) {
        super(options);
        $context = mContext;

    }

    @Override
    protected void onBindViewHolder(@NonNull TransactionAdapter.CardTransactionHolder cardTransactionHolder, int i, @NonNull CardTransaction cardTransaction) {
        cardTransactionHolder.routeNumber.setText(cardTransaction.getRoute());
        cardTransactionHolder.operatorNumber.setText(String.valueOf(cardTransaction.getOperatorNumber()));
        cardTransactionHolder.ticketNumber.setText(cardTransaction.getTicketNumber());
        cardTransactionHolder.transactionDate.setText(Helper.DateFormatter(SystemInterface.DateTimeFormat.dateMonthYear,cardTransaction.getDate().toDate()));
        cardTransactionHolder.transactionTime.setText(Helper.DateFormatter(SystemInterface.DateTimeFormat.timeFormat,cardTransaction.getDate().toDate()));

    }

    @NonNull
    @Override
    public TransactionAdapter.CardTransactionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_transaction, parent,false);
        return new CardTransactionHolder(v);
    }

    public class CardTransactionHolder extends RecyclerView.ViewHolder{

        TextView transactionDate;
        TextView transactionTime;
        TextView ticketNumber;
        TextView operatorNumber;
        TextView routeNumber;

        public CardTransactionHolder(@NonNull View itemView) {
            super(itemView);
            transactionDate = itemView.findViewById(R.id.tv_date_value);
            transactionDate.setText("");

            transactionTime = itemView.findViewById(R.id.tv_time_value);
            transactionTime.setText("");

            ticketNumber = itemView.findViewById(R.id.tv_ticket_value);
            ticketNumber.setText("");

            operatorNumber = itemView.findViewById(R.id.tv_operator_value);
            operatorNumber.setText("");

            routeNumber = itemView.findViewById(R.id.tv_route_value);
            routeNumber.setText("");

        }
    }
}
