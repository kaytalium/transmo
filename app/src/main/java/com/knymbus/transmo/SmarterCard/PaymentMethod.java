package com.knymbus.transmo.SmarterCard;

public class PaymentMethod {

    private String accountName;
    private String accountType;
    private String balance;
    private String bankName;
    private String cardNumber;
    private String subType;

    public PaymentMethod() {}

    public PaymentMethod(String accountName, String accountType, String balance, String bankName, String cardNumber, String subType) {
        this.accountName = accountName;
        this.accountType = accountType;
        this.balance = balance;
        this.bankName = bankName;
        this.cardNumber = cardNumber;
        this.subType = subType;
    }

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

    public String getSubType() {
        return subType;
    }
}
