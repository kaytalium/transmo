package com.knymbus.transmo.SmarterCard;

import com.google.firebase.Timestamp;

public class CardTransaction {

    private Timestamp date;
    private Integer operatorNumber;
    private String route;
    private String ticketNumber;
    private String UserID;

    public CardTransaction() {}

    public CardTransaction(Timestamp date, Integer operatorNumber, String route, String ticketNumber, String userID) {
        this.date = date;
        this.operatorNumber = operatorNumber;
        this.route = route;
        this.ticketNumber = ticketNumber;
        UserID = userID;
    }

    public Timestamp getDate() {
        return date;
    }

    public Integer getOperatorNumber() {
        return operatorNumber;
    }

    public String getRoute() {
        return route;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public String getUserID() {
        return UserID;
    }
}
