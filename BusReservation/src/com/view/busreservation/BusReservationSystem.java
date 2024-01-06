package com.view.busreservation;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

import com.dto.repo.BusRepository;
import com.modal.busticketbooking.Bus;
import com.modal.busticketbooking.Passenger;
import com.viewmodal.busreservation.BusViewModel;

class BusReservationSystem {
    public static void main(String[] args) {
        BusRepository busRepository = new BusRepository();
        BusViewModel busViewModel = new BusViewModel(busRepository);

        busViewModel.addBus(101, "Parveen Travels", "Chennai", 20,"07:40 PM");
        busViewModel.addBus(102, "Apple Transport", "Chennai", 45,"08:30 PM");
        busViewModel.addBus(103, "Sundara Travel", "Coimbatore", 30,"06:30 PM");
        busViewModel.addBus(104, "Rahim Travel", "Coimbatore", 45,"08:10 PM");
        busViewModel.addBus(105, "KPN Travel", "Trichy", 35,"07:20 PM");

        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        do {
        	displayBuses(busViewModel);
            displayMainMenu();
            int userChoice = getUserChoice(scanner);

            switch (userChoice) {
                case 1:
                    displayBuses(busViewModel);
                    break;
                case 2:
                    bookSeats(scanner, busViewModel);
                    break;
                case 3:
                    checkAvailableSeats(scanner, busViewModel);
                    break;
                case 4:
                    displayFilledSeats(scanner, busViewModel);
                    break;
                case 5:
                    displayBusAndPassengerDetails(scanner, busViewModel);
                    break;
                case 6:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (!exit);

        scanner.close();
    }

    private static void displayMainMenu() {
        System.out.println("\nBus Reservation System Menu:");
        System.out.println("___________________________________________________________________________________________________________________________________________");
        System.out.println("\n1. Display all buses");
        System.out.println("2. Book seats on a bus");
        System.out.println("3. Check available seats on a bus");
        System.out.println("4. Display number of filled seats on a bus");
        System.out.println("5. Display Bus and Passenger Details");
        System.out.println("6. Exit");
        System.out.println("___________________________________________________________________________________________________________________________________________");
        System.out.print("\nEnter your choice (1-6): ");
    }

    private static void displayBuses(BusViewModel busViewModel) {
        ArrayList<Bus> buses = busViewModel.getBuses();
        if (buses.isEmpty()) {
            System.out.println("No buses found");
            return;
        }
        System.out.println("+-------------+---------------+---------------+----------+---------------+----------------+----------------------+");
        System.out.println("| Bus Number  | Bus Name      | Destination   | Capacity | Booked Seats  | Available Seats | Departure Time | Booking Date         |");
        System.out.println("+-------------+---------------+---------------+----------+---------------+----------------+----------------------+");

        for (Bus bus : buses) {
            String bookingDateInfo = getBookingDateInfo(bus.getBookingDate());
            System.out.printf("| %-11d| %-14s| %-14s| %-8d| %-13d| %-14d| %-16s| %-20s|%n",
                    bus.getBusNumber(), bus.getBusOperator(), bus.getDestination(),
                    bus.getCapacity(), bus.getBookedSeats(), (bus.getCapacity() - bus.getBookedSeats()),
                    bus.getDepartureTime(),bookingDateInfo);
        }

        System.out.println("+-------------+---------------+---------------+----------+---------------+----------------+----------------------+");
    }

    private static void bookSeats(Scanner scanner, BusViewModel busViewModel) {
    	int busNumber;
        Bus selectedBus;

        do {
            System.out.print("Enter bus number: ");
            busNumber = scanner.nextInt();
            selectedBus = busViewModel.getBusByNumber(busNumber);

            if (selectedBus == null) {
                System.out.println("Bus " + busNumber + " not found. Please select a valid bus number.");
            }
        } while (selectedBus == null);

        // Display available and booked seats
        displaySeatLayout(busViewModel, busNumber);

        System.out.print("Enter number of seats to book: ");
        int seats = scanner.nextInt();

        // Get seat numbers to book
        Set<Integer> seatNumbersToBook = new HashSet<>();
        for (int i = 0; i < seats; i++) {
            System.out.print("Enter seat number " + (i + 1) + ": ");
            int seatNumber = scanner.nextInt();
            seatNumbersToBook.add(seatNumber);
        }

        // Get passenger details
        ArrayList<Passenger> passengers = new ArrayList<>();
        for (int seatNumber : seatNumbersToBook) {
            scanner.nextLine(); // Consume newline character
            System.out.print("Enter passenger name for seat " + seatNumber + ": ");
            String name = scanner.nextLine();
            System.out.print("Enter passenger phone number: ");
            String phoneNumber = scanner.nextLine();
            passengers.add(new Passenger(name, phoneNumber, seatNumber));
        }

        // Book seats
        boolean success = selectedBus.bookSeats(passengers);
        if (success) {
            System.out.println("Seats booked successfully!");
        } else {
            System.out.println("Seats could not be booked. Please try again.");
        }
    }

    private static void displaySeatLayout(BusViewModel busViewModel, int busNumber) {
        Bus bus = busViewModel.getBusByNumber(busNumber);
        if (bus != null) {
            System.out.println("Seat Layout for Bus " + busNumber + ":");
            // representation of available (O) and booked (X) seats
            for (int i = 1; i <= bus.getCapacity(); i++) {
                if (bus.checkAvailability(1) && bus.getBookedSeats() < bus.getCapacity() && bus.isSeatAvailable(i)) {
                    System.out.print("O ");
                } else {
                    System.out.print("X ");
                }
                if (i % 4 == 0) {
                    System.out.println();
                }
            }
            System.out.println();
        } else {
            
        	System.out.println("Bus " + busNumber + " not found");
         
        	return;
        }
        
    }

    private static void checkAvailableSeats(Scanner scanner, BusViewModel busViewModel) {
        System.out.print("Enter bus number: ");
        int busNumberToCheck = scanner.nextInt();
        Bus busToCheck = busViewModel.getBusByNumber(busNumberToCheck);

        if (busToCheck != null) {
            busToCheck.displayAvailableSeats();
            busToCheck.displaySeatLayout();
        } else {
            System.out.println("Bus " + busNumberToCheck + " not found");
        return;
        }
    }

    private static void displayFilledSeats(Scanner scanner, BusViewModel busViewModel) {
        System.out.print("Enter bus number: ");
        int busNumberToDisplay = scanner.nextInt();
        Bus busToDisplay = busViewModel.getBusByNumber(busNumberToDisplay);

        if (busToDisplay != null) {
            System.out.println("Number of filled seats in Bus " + busNumberToDisplay + ": " + busToDisplay.getBookedSeats());
        } else {
            System.out.println("Bus " + busNumberToDisplay + " not found");
        return;
        }
    }

    private static void displayBusAndPassengerDetails(Scanner scanner, BusViewModel busViewModel) {
        System.out.print("Enter bus number: ");
        int busNumberToDisplay = scanner.nextInt();
        Bus busToDisplay = busViewModel.getBusByNumber(busNumberToDisplay);

        if (busToDisplay != null) {
            busToDisplay.displayBusDetailsInTable();
            busToDisplay.displayPassengerDetails();
        } else {
            System.out.println("Bus " + busNumberToDisplay + " not found");
        return;
        }
    }

    private static int getUserChoice(Scanner scanner) {
        int choice = -1;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("Enter your choice (1-6): ");
                choice = scanner.nextInt();
                scanner.nextLine(); 
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }

        return choice;
    }
    private static String getBookingDateInfo(Date bookingDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(new Date());
        String busDate = sdf.format(bookingDate);

        if (busDate.equals(currentDate)) {
            return "Today";
        } else if (busDate.equals(getTomorrowDate(currentDate))) {
            return "Tomorrow";
        } else {
            return "Day after tomorrow";
        }
    }

    private static String getTomorrowDate(String currentDate) {
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
    }