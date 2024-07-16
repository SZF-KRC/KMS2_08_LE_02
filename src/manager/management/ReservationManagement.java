package manager.management;

import manager.control.InputControl;
import manager.database.BicycleData;
import manager.database.ReservationData;
import manager.model.*;
import manager.service.RentalService;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

public class ReservationManagement {
    private final InputControl control;
    private final RentalService<Reservation> reservationService;
    private final RentalService<Customer> customerService;
    private final RentalService<Bicycle> bicycleService;
    private final RentalService<Employee> employeeService;
    private final ReservationData reservationData;
    private final BicycleData bicycleData;

    public ReservationManagement(InputControl control, RentalService<Reservation> reservationService, RentalService<Customer> customerService, RentalService<Bicycle> bicycleService, RentalService<Employee> employeeService) {
        this.control = control;
        this.reservationService = reservationService;
        this.customerService = customerService;
        this.bicycleService = bicycleService;
        this.employeeService = employeeService;
        this.reservationData = new ReservationData();
        this.bicycleData = new BicycleData();
    }
    public Reservation addReservation() {
        Customer customer = findItem("Customer ID", customerService, Customer::getId);
        if (customer == null) {
            return null;
        }

        Bicycle bicycle = findItem("Bicycle ID", bicycleService, Bicycle::getId);
        if (bicycle == null) {
            return null;
        }
        if (bicycle.isRented()) {
            System.out.println("Bicycle is already rented. Choose another bicycle.");
            return null;
        }

        bicycle.rent();

        Employee employee = findItem("Employee ID", employeeService, Employee::getId);
        if (employee == null) {
            return null;
        }

        int days = rentDays();

        if (days <= 0) {
            System.out.println("Error: Invalid reservation data. Reservation cannot be created.");
            return null;
        }

        Reservation reservation = new Reservation(control.generateUniqueID(), customer, bicycle, employee, LocalDate.now(), LocalDate.now().plusDays(days));
        reservationData.addReservation(reservation);

        bicycleData.updateBicycleRentStatus(bicycle.getId(), true);
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
            if (bicycle instanceof MountainBike) {
                ((MountainBike) bicycle).setNeedsMaintenance(true);
            }
            reservationData.removeReservation(reservation.getReservationId());
            bicycleData.updateBicycleRentStatus(bicycle.getId(),false);
            bicycleData.updateBicycleMaintenanceStatus(bicycle.getId(),true);
        } else {
            System.out.println("Reservation with ID " + enterId + " not found.");
        }

        return reservation;
    }

    private <T> T findItem(String prompt, RentalService<T> service, Function<T, String> getIdFunction) {
        String id = control.textEntry("Enter " + prompt + ": ");
        T item = service.find(t -> getIdFunction.apply(t).equals(id));
        if (item != null) {
            System.out.println(prompt + " found.");
        } else {
            System.out.println(prompt + " not found.");
        }
        return item;
    }

    private int rentDays(){
        return control.intEntry("Enter how many days you want the reservation for: ");
    }

    public void loadData(){
        List<Reservation> reservations = reservationData.getAllReservations();
        reservationService.loadItems(reservations);
    }
}
