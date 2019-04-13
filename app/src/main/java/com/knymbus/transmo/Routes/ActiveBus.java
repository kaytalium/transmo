package com.knymbus.transmo.Routes;

public class ActiveBus {

    private String busModel, busNumber, status;
    private RouteOrigin origin;
    private RouteDestination destination;


    public ActiveBus() {}

    public ActiveBus(String busModel, String busNunber, RouteDestination destination, RouteOrigin origin, String status) {
        this.busModel = busModel;
        this.busNumber = busNunber;
        this.destination = destination;
        this.origin = origin;
        this.status = status;
    }

    public String getStatus() {return status;}

    public String getBusModel() {
        return busModel;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public RouteDestination getDestination() {
        return destination;
    }

    public RouteOrigin getOrigin() {
        return origin;
    }
}


