package manager.database;

import manager.model.Bicycle;
import manager.model.MountainBike;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BicycleData {

    public void addBicycle(Bicycle bicycle){
        try (Connection connection = DatabaseConnection.getConnection()){
            String query = "INSERT INTO bicycles (id,model,is_rented) VALUES (?,?,?)";
            try (PreparedStatement statement = connection.prepareStatement(query)){
                statement.setString(1,bicycle.getId());
                statement.setString(2,bicycle.getModel());
                statement.setBoolean(3, bicycle.isRented());
                statement.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<Bicycle> getAllBicycles(){
        List<Bicycle> bicycles = new ArrayList<>();
        String query = "SELECT * FROM bicycles";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)){

            while (resultSet.next()){
                String id = resultSet.getString("id");
                String model = resultSet.getString("model");
                boolean isRented = resultSet.getBoolean("is_rented");
                boolean needsMaintenance = resultSet.getBoolean("needs_maintenance");
                MountainBike bicycle = new MountainBike(id, model);
                if (isRented) {
                    bicycle.rent();
                }
                bicycles.add(bicycle);
                bicycle.setNeedsMaintenance(needsMaintenance);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return bicycles;
    }

    public Bicycle getBicycleById(String bicycleId) {
        Bicycle bicycle = null;
        String query = "SELECT * FROM bicycles WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, bicycleId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String id = resultSet.getString("id");
                String model = resultSet.getString("model");
                boolean isRented = resultSet.getBoolean("is_rented");
                boolean needsMaintenance = resultSet.getBoolean("needs_maintenance");
                MountainBike mountainBike = new MountainBike(id, model);
                if (isRented) {
                    mountainBike.rent();
                }
                mountainBike.setNeedsMaintenance(needsMaintenance);
                bicycle = mountainBike;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bicycle;
    }

    public boolean isBicycleRented(String bicycleId) {
        String query = "SELECT is_rented FROM bicycles WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, bicycleId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getBoolean("is_rented");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void deleteBicycle(String bicycleId) {
        try (Connection connection = DatabaseConnection.getConnection()){
            String query = "DELETE FROM bicycles WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)){
                statement.setString(1, bicycleId);
                statement.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateBicycleMaintenanceStatus(String bicycleId,boolean needMaintenance){
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE bicycles SET needs_maintenance = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setBoolean(1, needMaintenance);
                statement.setString(2, bicycleId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBicycleRentStatus(String bicycleId, boolean isRented) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE bicycles SET is_rented = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setBoolean(1, isRented);
                statement.setString(2, bicycleId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
