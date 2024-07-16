package manager.database;

import manager.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerData {

    public void addCustomer(Customer customer){
        try (Connection connection = DatabaseConnection.getConnection()){
            String query = "INSERT INTO customers (id,name,contact) VALUES (?,?,?)";
            try (PreparedStatement statement = connection.prepareStatement(query)){
                statement.setString(1,customer.getId());
                statement.setString(2,customer.getName());
                statement.setString(3,customer.getContact());
                statement.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<Customer> getAllCustomers(){
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)){

            while (resultSet.next()){
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String contact = resultSet.getString("contact");
                customers.add(new Customer(id,name,contact));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return customers;
    }

    public Customer getCustomerById(String customerId) {
        Customer customer = null;
        String query = "SELECT * FROM customers WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String contact = resultSet.getString("contact");
                customer = new Customer(id, name, contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }

    public boolean hasActiveReservation(String customerId) {
        String query = "SELECT COUNT(*) FROM reservations WHERE customer_id = ? AND end_date >= CURRENT_DATE";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void deleteCustomer(String customerId) {
        try (Connection connection = DatabaseConnection.getConnection()){
            String query = "DELETE FROM customers WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)){
                statement.setString(1, customerId);
                statement.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
