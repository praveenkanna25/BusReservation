package com.viewmodal.busreservation;

import com.modal.busticketbooking.Bus;
import com.modal.busticketbooking.Passenger;
import com.dto.repo.*;

import java.util.ArrayList;

public class BusViewModel {
    private BusRepository busRepository;

    public BusViewModel(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    public void addBus(int busNumber, String busName, String destination, int capacity, String departureTime) {
        Bus bus = new Bus(busNumber, busName, destination, capacity, departureTime);
        busRepository.addBus(bus);
    }

    public ArrayList<Bus> getBuses() {
        return busRepository.getBuses();
    }

    public boolean bookBus(int busNumber, ArrayList<Passenger> passengers) {
        Bus bus = getBusByNumber(busNumber);
        if (bus == null) {
            System.out.println("Bus " + busNumber + " not found");
            return false;
        }

        //  booking date for the passengers
        for (Passenger passenger : passengers) {
            passenger.setBookingDate(bus.getBookingDate());
        }

        return bus.bookSeats(passengers);
    }

    public Bus getBusByNumber(int busNumber) {
        for (Bus bus : busRepository.getBuses()) {
            if (bus.getBusNumber() == busNumber) {
                return bus;
            }
        }
        return null;
    }
}
