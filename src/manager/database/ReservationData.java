package manager.database;

import manager.model.Bicycle;
import manager.model.Customer;
import manager.model.Reservation;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationData {
    private final CustomerData customerData;
    private final BicycleData bicycleData;

    public ReservationData(){
        this.customerData = new CustomerData();
        this.bicycleData = new BicycleData();
    }

    public void addReservation(Reservation reservation){
        try (Connection connection = DatabaseConnection.getConnection()){
            String query = "INSERT INTO reservations (id,customer_id,bicycle_id,start_date,end_date) VALUES (?,?,?,?,?)";
            try (PreparedStatement statement = connection.prepareStatement(query)){
                statement.setString(1,reservation.getReservationId());
                statement.setString(2,reservation.getCustomer().getId());
                statement.setString(3,reservation.getBicycle().getId());
                statement.setDate(4,Date.valueOf(reservation.getStartDate()));
                statement.setDate(5, Date.valueOf(reservation.getEndDate()));
                statement.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<Reservation> getAllReservations(){
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservations";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)){

            while (resultSet.next()){
                String reservationId = resultSet.getString("id");
                String customerId = resultSet.getString("customer_id");
                String bicycleId = resultSet.getString("bicycle_id");
                LocalDate startDate = resultSet.getDate("start_date").toLocalDate();
                LocalDate endDate = resultSet.getDate("end_date").toLocalDate();

                Customer customer = customerData.getCustomerById(customerId);
                Bicycle bicycle = bicycleData.getBicycleById(bicycleId);

                reservations.add(new Reservation(reservationId,customer,bicycle,startDate,endDate));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return reservations;
    }

    public void removeReservation(String reservationId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Get bicycle ID from the reservation
            String selectQuery = "SELECT bicycle_id FROM reservations WHERE id = ?";
            String bicycleId = null;
            try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
                selectStatement.setString(1, reservationId);
                ResultSet resultSet = selectStatement.executeQuery();
                if (resultSet.next()) {
                    bicycleId = resultSet.getString("bicycle_id");
                }
            }

            if (bicycleId != null) {
                // Delete the reservation
                String deleteQuery = "DELETE FROM reservations WHERE id = ?";
                try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
                    deleteStatement.setString(1, reservationId);
                    deleteStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
