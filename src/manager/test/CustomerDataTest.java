package manager.test;

import manager.database.CustomerData;
import manager.database.DatabaseConnection;
import manager.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDataTest {

    @BeforeEach
    void setUp() {
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
    }

    @Test
    void testAddCustomer() {
        CustomerData customerData = new CustomerData();
        Customer customer = new Customer("123", "John Doe", "123456789");
        customerData.addCustomer(customer);

        Customer retrievedCustomer = customerData.getCustomerById("123");
        assertNotNull(retrievedCustomer);
        assertEquals("John Doe", retrievedCustomer.getName());
    }
}
