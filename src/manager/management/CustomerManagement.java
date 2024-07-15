package manager.management;

import manager.control.InputControl;
import manager.model.Customer;
import manager.service.RentalService;

public class CustomerManagement {
    private InputControl control;
    private RentalService<Customer> customerService;


    public CustomerManagement(InputControl control, RentalService<Customer> customerService){
        this.control = control;
        this.customerService = customerService;
    }

    public Customer addCustomer() {
        Customer customer = new Customer(control.generateUniqueID(), control.stringEntry("Enter new name: "), control.phoneNumberEntry("Enter new phone: "));
        return customer;
    }

    public Customer deleteCustomer() {
        String enterId = control.textEntry("Enter ID Customer to delete");
        Customer customer = customerService.getItems().stream()
                .filter(c -> c.getId().equals(enterId))
                .findFirst()
                .orElse(null);

        if (customer != null) {
            System.out.println("Customer " + customer.getName() + " with ID " + customer.getId() + " will be deleted.");
        } else {
            System.out.println("Customer with ID " + enterId + " not found.");
        }

        return customer;
    }


}
