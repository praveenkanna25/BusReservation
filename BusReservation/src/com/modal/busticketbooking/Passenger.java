
package com.modal.busticketbooking;

import java.util.Date;

public class Passenger {
    private String name;
    private String phoneNumber;
    private int seatNumber;
    private Date bookingDate; 

    public Passenger(String name, String phoneNumber, int seatNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.seatNumber = seatNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }
    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }
    }