package manager.test;

import manager.database.*;
import manager.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationDataTest {

    private ReservationData reservationData;
    private Customer customer;
    private Bicycle bicycle;
    private Employee employee;

    @BeforeEach
    void setUp() {
        reservationData = new ReservationData();

        // Vyčistenie databázy pred každým testom
        try (Connection connection = DatabaseConnection.getConnection()) {
            String clearReservationsQuery = "DELETE FROM reservations";
            String clearCustomersQuery = "DELETE FROM customers";
            String clearBicyclesQuery = "DELETE FROM bicycles";
            String clearEmployeesQuery = "DELETE FROM employees";

            try (PreparedStatement reservationsStmt = connection.prepareStatement(clearReservationsQuery);
                 PreparedStatement customersStmt = connection.prepareStatement(clearCustomersQuery);
                 PreparedStatement bicyclesStmt = connection.prepareStatement(clearBicyclesQuery);
                 PreparedStatement employeesStmt = connection.prepareStatement(clearEmployeesQuery)) {
                reservationsStmt.executeUpdate();
                customersStmt.executeUpdate();
                bicyclesStmt.executeUpdate();
                employeesStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Failed to clean up database before test");
        }

        CustomerData customerData = new CustomerData();
        customer = new Customer("1", "John Doe", "123456789");
        customerData.addCustomer(customer);

        BicycleData bicycleData = new BicycleData();
        bicycle = new MountainBike("1", "Model A");
        bicycleData.addBicycle(bicycle);

        EmployeeData employeeData = new EmployeeData();
        employee = new Employee("1", "Jane Smith", 5000);
        employeeData.addEmployee(employee);
    }

    @Test
    void testAddReservation() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(7);

        Reservation reservation = new Reservation("1", customer, bicycle, employee, startDate, endDate);
        reservationData.addReservation(reservation);

        List<Reservation> reservations = reservationData.getAllReservations();
        assertEquals(1, reservations.size());
        assertEquals("1", reservations.get(0).getReservationId());
    }
}
