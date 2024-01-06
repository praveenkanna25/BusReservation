package com.modal.busticketbooking;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import java.util.Date;

public class Bus {
    private int busNumber;
    private String busOperator;
    private String destination;
    private int capacity;
    private int bookedSeats; private String departureTime;
    private Date bookingDate;
    private int[] availability;
    private ArrayList<Passenger> passengers;

    public Bus(int busNumber, String busOperator, String destination, int capacity, String departureTime) {
        this.busNumber = busNumber;
        this.busOperator = busOperator;
        this.destination = destination;
        this.capacity = capacity;
        this.bookedSeats = 0;
        this.passengers = new ArrayList<>(capacity);
        this.departureTime = departureTime; 
        this.bookingDate = new Date(); 
        this.availability = new int[]{capacity, 0, 0}; 
    }
    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setAvailability(int[] availability) {
        this.availability = availability;
    }

    public int[] getAvailability() {
        return availability;
    }

    public int getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(int busNumber) {
        this.busNumber = busNumber;
    }

    public String getBusOperator() {
        return busOperator;
    }

    public void setBusOperator(String busOperator) {
        this.busOperator = busOperator;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getBookedSeats() {
        return bookedSeats;
    }

    public boolean checkAvailability(int seats) {
        return capacity - bookedSeats >= seats;
    }

    public boolean isSeatAvailable(int seatNumber) {
        for (Passenger passenger : passengers) {
            if (passenger.getSeatNumber() == seatNumber) {
                return false;
            }
        }
        return true;
    }

    public boolean bookSeats(ArrayList<Passenger> passengers) {
        int seats = passengers.size();

        //  seats are available
        if (checkAvailability(seats)) {
            //  duplicate seat numbers
            Set<Integer> bookedSeatNumbers = new HashSet<>();
            for (Passenger passenger : this.passengers) {
                bookedSeatNumbers.add(passenger.getSeatNumber());
           
                availability[getBookingDateIndex()] -= seats; 
            }

            for (Passenger passenger : passengers) {
                int seatNumber = passenger.getSeatNumber();
                if (bookedSeatNumbers.contains(seatNumber) || seatNumber < 1 || seatNumber > capacity) {
                    System.out.println("Invalid seat number or seat already booked: " + seatNumber);
                    return false; // Seats not booked
                }
               
            }  for (Passenger passenger : passengers) {
                passenger.setBookingDate(bookingDate);
            }

            // Book the seats
            bookedSeats += seats;
            this.passengers.addAll(passengers);
            availability[getBookingDateIndex()] -= seats;
            System.out.println("Seats booked successfully for " + seats + " passengers");
            return true; // Seats booked successfully
        } else {
            System.out.println("Sorry, seats are not available");
            return false; // Seats not booked
        }
    }

    public void displayAvailableSeats() {
        System.out.println("Available Seats in Bus " + busNumber + ": " + (capacity - bookedSeats));
    }

    public void displayPassengerDetails() {
        if (!passengers.isEmpty()) {
            System.out.println("\n+-----------------+---------------------+-------------------+");
            System.out.println("| Seat Number       | Passenger Name      | Phone Number      |");
            System.out.println("+-----------------+---------------------+-------------------+");
            for (Passenger passenger : passengers) {
                System.out.printf("| %-15d| %-20s| %-17s|%n",
                        passenger.getSeatNumber(), passenger.getName(), passenger.getPhoneNumber());
            }
            System.out.println("+-----------------+---------------------+-------------------+");
        } else {
            System.out.println("No passengers booked for Bus " + busNumber);
        }
    }

    public void displayBusDetailsInTable() {
        String border = "+---------------------+---------------------+---------------------+------------+---------------+-----------------+----------------------+";
        String header = "| Bus Number          | Bus Name            | Destination         | Capacity   | Booked Seats  | Available Seats | Departure Time        |";
        System.out.println(border);
        System.out.println(header);
        System.out.printf("| %-20d| %-20s| %-20s| %-11d| %-14d| %-16d| %-20s|%n",
                getBusNumber(), getBusOperator(), getDestination(),
                getCapacity(), getBookedSeats(), (getCapacity() - getBookedSeats()), getDepartureTime());
        System.out.println(border);
    }
 private int getBookingDateIndex() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(new Date());
        String busDate = sdf.format(bookingDate);

        if (busDate.equals(currentDate)) {
            return 0; // Today
        } else if (busDate.equals(getTomorrowDate(currentDate))) {
            return 1; // Tomorrow
        } else {
            return 2; // Day after tomorrow
        }
    }
    private String getTomorrowDate(String currentDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate currentLocalDate = LocalDate.parse(currentDate, formatter);
            LocalDate tomorrowLocalDate = currentLocalDate.plusDays(1);

            return tomorrowLocalDate.format(formatter);
        } catch (Exception e) {
            e.printStackTrace(); 
            return currentDate; 
        }
    }
  
	public void displaySeatLayout() {
		 System.out.println("Seat Layout for Bus " + busNumber + ":");
	        for (int i = 1; i <= capacity; i++) {
	            if (isSeatAvailable(i)) {
	                System.out.print("O ");
	            } else {
	                System.out.print("X ");
	            }
	            if (i % 4 == 0) {
	                System.out.println();
	            }
	        }
	        System.out.println();
		
	}


}
