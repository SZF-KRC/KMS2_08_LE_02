package manager.management;

import manager.control.InputControl;
import manager.database.EmployeeData;
import manager.model.Employee;
import manager.service.RentalService;
import java.util.List;

public class EmployeeManager {
    private final InputControl control;
    private final RentalService<Employee> employeeService;
    private final EmployeeData employeeData;

    public EmployeeManager(InputControl control, RentalService<Employee> employeeService) {
        this.control = control;
        this.employeeService = employeeService;
        this.employeeData = new EmployeeData();
    }

    public Employee addEmployee() {
        String name = control.stringEntry("Enter new name: ");
        double salary = control.doubleEntry("Enter salary: ");

        if (salary <= 0) {
            System.out.println("Salary must be a positive number. Please try again.");
            return null;
        }

        Employee employee = new Employee(control.generateUniqueID(), name, salary);
        employeeData.addEmployee(employee);
        return employee;
    }


    public Employee deleteEmployee() {
        String enterId = control.textEntry("Enter ID Customer to delete");
        Employee employee = employeeService.getItems().stream()
                .filter(c -> c.getId().equals(enterId))
                .findFirst()
                .orElse(null);

        if (employee != null) {
            employeeData.deleteEmployee(employee.getId());
            System.out.println("Employee " + employee.getName() + " with ID " + employee.getId() + " will be deleted.");
            employeeService.removeItem(employee); // Remove from the service list
        } else {
            System.out.println("Employee with ID " + enterId + " not found.");
        }

        return employee;
    }

    public void loadData(){
        List<Employee> employees = employeeData.getAllEmployees();
        employeeService.loadItems(employees);
    }
}
