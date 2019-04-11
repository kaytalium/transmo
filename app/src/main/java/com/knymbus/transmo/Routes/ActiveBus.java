package com.knymbus.transmo.Routes;

public class ActiveBus {

    public String busModel, busNumber;
    public RouteOrigin destination, origin;


    public ActiveBus() {}

    public ActiveBus(String busModel, String busNunber, RouteOrigin destination, RouteOrigin origin) {
        this.busModel = busModel;
        this.busNumber = busNunber;
        this.destination = destination;
        this.origin = origin;
    }

    public String getBusModel() {
        return busModel;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public RouteOrigin getDestination() {
        return destination;
    }

    public RouteOrigin getOrigin() {
        return origin;
    }
}


