package com.dto.repo;
import com.modal.busticketbooking.*;
import java.util.ArrayList;

public class BusRepository {
    private ArrayList<Bus> buses = new ArrayList<>();
    private PassengerDetails passengerDetails = new PassengerDetails();
    public void addBus(Bus bus) {
        buses.add(bus);
    }

    public ArrayList<Bus> getBuses() {
        return buses;
    }
    public PassengerDetails getPassengerDetails() {
        return passengerDetails;
    }
}