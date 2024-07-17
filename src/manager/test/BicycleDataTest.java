package manager.test;

import manager.database.BicycleData;
import manager.database.DatabaseConnection;
import manager.model.Bicycle;
import manager.model.MountainBike;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BicycleDataTest {

    @BeforeEach
    void setUp() {
        // Vyčistenie databázy pred každým testom
        try (Connection connection = DatabaseConnection.getConnection()) {
            String clearReservationsQuery = "DELETE FROM reservations";
            String clearBicyclesQuery = "DELETE FROM bicycles";

            try (PreparedStatement reservationsStmt = connection.prepareStatement(clearReservationsQuery);
                 PreparedStatement bicyclesStmt = connection.prepareStatement(clearBicyclesQuery)) {
                reservationsStmt.executeUpdate();
                bicyclesStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Failed to clean up database before test");
        }

        // Pridanie testovacích údajov
        BicycleData bicycleData = new BicycleData();
        bicycleData.addBicycle(new MountainBike("1", "Model A"));
        bicycleData.addBicycle(new MountainBike("2", "Model B"));
    }

    @Test
    void testGetAllBicycles() {
        BicycleData bicycleData = new BicycleData();
        List<Bicycle> bicycles = bicycleData.getAllBicycles();
        assertEquals(2, bicycles.size());
    }

    @Test
    void testUpdateBicycleMaintenanceStatus() {
        BicycleData bicycleData = new BicycleData();
        String bicycleId = "1";
        bicycleData.updateBicycleMaintenanceStatus(bicycleId, true);
        MountainBike bike = (MountainBike) bicycleData.getBicycleById(bicycleId);
        assertTrue(bike.isNeedsMaintenance());

        bicycleData.updateBicycleMaintenanceStatus(bicycleId, false);
        bike = (MountainBike) bicycleData.getBicycleById(bicycleId);
        assertFalse(bike.isNeedsMaintenance());
    }
}
