package com.knymbus.transmo.SmarterCard;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;

public class PaymentMethod{

    private String accountName;
    private String accountType;
    private String balance;
    private String bankName;
    private String cardNumber;
    private String cardType;
    private String docId;

    @ServerTimestamp()
    private Timestamp dateCreated;

    private String uid;

    public PaymentMethod() {}

    public PaymentMethod(String accountName, String accountType, String balance,
                         String bankName, String cardNumber, String cardType, Timestamp dateCreated, String uid,
                         String docId) {
        this.accountName = accountName;
        this.accountType = accountType;
        this.balance = balance;
        this.bankName = bankName;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.dateCreated = dateCreated;
        this.uid = uid;
        this.docId =docId;
    }

//    Getters

    public String getAccountName() {
        return accountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getBalance() {
        return balance;
    }

    public String getBankName() {
        return bankName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public Timestamp getDateCreated() {return dateCreated;}

    public String getUid() {return uid;}

    public String getDocId() {return docId;}

    //    Setters


    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public void setDateCreated(Timestamp dateCreated) {this.dateCreated = dateCreated;}

    public void setUid(String uid) {this.uid = uid;}

    public void setDocId(String docId) {this.docId = docId;}
}
