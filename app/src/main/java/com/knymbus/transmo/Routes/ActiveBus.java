package com.knymbus.transmo.Routes;

import com.google.firebase.firestore.ServerTimestamp;
import java.util.Map;

public class ActiveBus {

    public String busModel, busNunber;
    public Map<String, ServerTimestamp> destination, origin;


    public ActiveBus() {}

    public ActiveBus(String busModel, String busNunber, Map<String, ServerTimestamp> destination, Map<String, ServerTimestamp> origin) {
        this.busModel = busModel;
        this.busNunber = busNunber;
        this.destination = destination;
        this.origin = origin;
    }

    public String getBusModel() {
        return busModel;
    }

    public String getBusNunber() {
        return busNunber;
    }

    public Map<String, ServerTimestamp> getDestination() {
        return destination;
    }

    public Map<String, ServerTimestamp> getOrigin() {
        return origin;
    }
}
