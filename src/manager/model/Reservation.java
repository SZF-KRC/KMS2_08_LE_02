package manager.model;

import java.time.LocalDate;

public class Reservation {
    private String reservationId;
    private Customer customer;
    private Bicycle bicycle;
    private LocalDate startDate;
    private LocalDate endDate;

    public Reservation(String reservationId, Customer customer, Bicycle bicycle, LocalDate startDate, LocalDate endDate) {
        this.reservationId = reservationId;
        this.customer = customer;
        this.bicycle = bicycle;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getReservationId() {
        return reservationId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Bicycle getBicycle() {
        return bicycle;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId='" + reservationId + '\'' +
                ", customer=" + customer +
                ", bicycle=" + bicycle +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
