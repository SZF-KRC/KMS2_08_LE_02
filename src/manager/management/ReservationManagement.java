package manager.management;

import manager.control.InputControl;
import manager.database.BicycleData;
import manager.database.ReservationData;
import manager.model.Bicycle;
import manager.model.Customer;
import manager.model.Reservation;
import manager.service.RentalService;

import java.time.LocalDate;
import java.util.List;

public class ReservationManagement {
    private final InputControl control;
    private final RentalService<Reservation> reservationService;
    private final RentalService<Customer> customerService;
    private final RentalService<Bicycle> bicycleService;
    private final ReservationData reservationData;
    private final BicycleData bicycleData;

    public ReservationManagement(InputControl control, RentalService<Reservation> reservationService, RentalService<Customer> customerService, RentalService<Bicycle> bicycleService) {
        this.control = control;
        this.reservationService = reservationService;
        this.customerService = customerService;
        this.bicycleService = bicycleService;
        this.reservationData = new ReservationData();
        this.bicycleData = new BicycleData();
    }
    public Reservation addReservation() {
        Customer customer = findCustomer();
        if (customer == null){return null;}
        Bicycle bicycle = findBicycle();
        if (bicycle == null) {
            return null;
        }
        if (bicycle.isRented()) {
            System.out.println("Bicycle is already rented. Choose another bicycle.");
            return null;
        }

        int days = rentDays();

        if (days <= 0) {
            System.out.println("Error: Invalid reservation data. Reservation cannot be created.");
            return null;
        }

        Reservation reservation = new Reservation(control.generateUniqueID(), customer, bicycle, LocalDate.now(), LocalDate.now().plusDays(days));
        reservationData.addReservation(reservation);
        bicycle.rent();
        bicycleData.updateBicycleRentStatus(bicycle.getId(),true);
        return reservation;
    }

    public Reservation deleteReservation() {
        String enterId = control.textEntry("Enter Reservation ID to delete: ");
        Reservation reservation = reservationService.getItems().stream()
                .filter(c -> c.getReservationId().equals(enterId))
                .findFirst()
                .orElse(null);

        if (reservation != null) {
            System.out.println("Reservation for " + reservation.getCustomer().getName() + " with ID " + reservation.getReservationId() + " will be deleted.");
            Bicycle bicycle = reservation.getBicycle();
            bicycle.returnBicycle();
            reservationData.removeReservation(reservation.getReservationId());
            bicycleData.updateBicycleRentStatus(bicycle.getId(),false);
        } else {
            System.out.println("Reservation with ID " + enterId + " not found.");
        }

        return reservation;
    }

    private Customer findCustomer(){
        String customerID = control.textEntry("Enter Customer ID: ");
        Customer customer = customerService.getItems().stream()
                .filter(c -> c.getId().equals(customerID))
                .findFirst()
                .orElse(null);
        if (customer != null) {
            System.out.println("Customer " + customer.getName() + " with ID " + customer.getId() + " found.");
        } else {
            System.out.println("Customer with ID " + customerID + " not found.");
        }

        return customer;
    }

    private Bicycle findBicycle(){
        String bicycleID = control.textEntry("Enter Bicycle ID: ");
        Bicycle bicycle = bicycleService.getItems().stream()
                .filter(c -> c.getId().equals(bicycleID))
                .findFirst()
                .orElse(null);
        if (bicycle != null) {
            System.out.println("Bicycle " + bicycle.getModel() + " with ID " + bicycle.getId() + " found.");
        } else {
            System.out.println("Bicycle with ID " + bicycleID + " not found.");
        }

        return bicycle;
    }

    private int rentDays(){
        return control.intEntry("Enter how many days you want the reservation for: ");
    }

    public void loadData(){
        List<Reservation> reservations = reservationData.getAllReservations();
        reservationService.loadItems(reservations);
    }
}
