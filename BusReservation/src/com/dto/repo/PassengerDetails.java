package com.dto.repo;

import com.modal.busticketbooking.Passenger;
import java.util.ArrayList;

public class PassengerDetails {
    private ArrayList<Passenger> passengers;

    public PassengerDetails() {
        this.passengers = new ArrayList<>();
    }

    public void addPassenger(Passenger passenger) {
        passengers.add(passenger);
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }
}
