package com.knymbus.transmo.FirestoreConnection;

import com.knymbus.transmo.SmarterCard.CardTransaction;

public interface FirestoreConnection {
ActiveBusDataManager BusDataManager = new ActiveBusDataManager();
CardTransactionDataManager TransactionDataManager = new CardTransactionDataManager();
PaymentMethodDataManager WalletManager = new PaymentMethodDataManager();

}
