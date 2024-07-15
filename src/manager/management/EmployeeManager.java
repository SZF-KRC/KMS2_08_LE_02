package manager.management;

import manager.control.InputControl;
import manager.model.Employee;
import manager.service.RentalService;

public class EmployeeManager {
    private InputControl control;
    private RentalService<Employee> customerService;

    public EmployeeManager(InputControl control, RentalService<Employee> customerService) {
        this.control = control;
        this.customerService = customerService;
    }

    public Employee addEmployee() {
        Employee employee = new Employee(control.generateUniqueID(), control.stringEntry("Enter new name: "), control.doubleEntry("Enter salary: "));
        return employee;
    }

    public Employee deleteEmployee() {
        String enterId = control.textEntry("Enter ID Customer to delete");
        Employee employee = customerService.getItems().stream()
                .filter(c -> c.getId().equals(enterId))
                .findFirst()
                .orElse(null);

        if (employee != null) {
            System.out.println("Employee " + employee.getName() + " with ID " + employee.getId() + " will be deleted.");
        } else {
            System.out.println("Employee with ID " + enterId + " not found.");
        }

        return employee;
    }
}
