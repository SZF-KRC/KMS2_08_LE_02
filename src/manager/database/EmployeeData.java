package manager.database;

import manager.model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeData {

    public void addEmployee(Employee employee){
        try (Connection connection = DatabaseConnection.getConnection()){
            String query = "INSERT INTO employees (id,name,salary) VALUES (?,?,?)";
            try (PreparedStatement statement = connection.prepareStatement(query)){
                statement.setString(1,employee.getId());
                statement.setString(2,employee.getName());
                statement.setDouble(3,employee.getSalary());
                statement.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<Employee> getAllEmployees(){
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM employees";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)){

            while (resultSet.next()){
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                employees.add(new Employee(id,name, salary));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return employees;
    }

    public void deleteEmployee(String employeeId) {
        try (Connection connection = DatabaseConnection.getConnection()){
            String query = "DELETE FROM employees WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)){
                statement.setString(1, employeeId);
                statement.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
