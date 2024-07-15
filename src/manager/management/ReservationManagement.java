package manager.management;

import manager.control.InputControl;
import manager.model.Bicycle;
import manager.model.Customer;
import manager.model.Reservation;
import manager.service.RentalService;

import java.time.LocalDate;
import java.util.Date;

public class ReservationManagement {
    private InputControl control;
    private RentalService<Reservation> reservationService;
    private RentalService<Customer> customerService;
    private RentalService<Bicycle> bicycleService;

    public ReservationManagement(InputControl control, RentalService<Reservation> reservationService, RentalService<Customer> customerService, RentalService<Bicycle> bicycleService) {
        this.control = control;
        this.reservationService = reservationService;
        this.customerService = customerService;
        this.bicycleService = bicycleService;
    }
    public Reservation addReservation() {
        Reservation customer = new Reservation(control.generateUniqueID(), findCustomer(), findBicycle(), LocalDate.now(), LocalDate.now().plusDays(rentDays()));
        return customer;
    }

    public Reservation deleteReservation() {
        String enterId = control.textEntry("Enter Customer ID to delete");
        Reservation reservation = reservationService.getItems().stream()
                .filter(c -> c.getReservationId().equals(enterId))
                .findFirst()
                .orElse(null);

        if (reservation != null) {
            System.out.println("Reservation for " + reservation.getCustomer() + " with ID " + reservation.getReservationId() + " will be deleted.");
        } else {
            System.out.println("Reservation with ID " + enterId + " not found.");
        }

        return reservation;
    }

    private Customer findCustomer(){
        String customerID = control.textEntry("Enter Customer ID");
        Customer customer = customerService.getItems().stream()
                .filter(c -> c.getId().equals(customerID))
                .findFirst()
                .orElse(null);
        if (customer != null) {
            System.out.println("Customer " + customer.getName() + " with ID " + customer.getId() + " will be deleted.");
        } else {
            System.out.println("Customer with ID " + customerID + " not found.");
        }

        return customer;
    }

    private Bicycle findBicycle(){
        String bicycleID = control.textEntry("Enter Customer ID");
        Bicycle bicycle = bicycleService.getItems().stream()
                .filter(c -> c.getId().equals(bicycleID))
                .findFirst()
                .orElse(null);
        if (bicycle != null) {
            System.out.println("Bicycle " + bicycle.getModel() + " with ID " + bicycle.getId() + " will be deleted.");
        } else {
            System.out.println("Bicycle with ID " + bicycleID + " not found.");
        }

        return bicycle;
    }

    private int rentDays(){
        return control.intEntry("Enter how many days you want the reservation for");
    }

}
