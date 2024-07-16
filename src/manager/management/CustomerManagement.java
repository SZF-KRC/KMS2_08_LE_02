package manager.management;

import manager.control.InputControl;
import manager.database.CustomerData;
import manager.model.Customer;
import manager.service.RentalService;

import java.util.List;

public class CustomerManagement {
    private InputControl control;
    private RentalService<Customer> customerService;
    private CustomerData customerData;


    public CustomerManagement(InputControl control, RentalService<Customer> customerService){
        this.control = control;
        this.customerService = customerService;
        this.customerData = new CustomerData();
    }

    public Customer addCustomer() {
        Customer customer = new Customer(control.generateUniqueID(), control.stringEntry("Enter new name: "), control.phoneNumberEntry("Enter new phone: "));
        customerData.addCustomer(customer);
        return customer;
    }

    public Customer deleteCustomer() {
        String enterId = control.textEntry("Enter ID Customer to delete: ");
        Customer customer = customerService.getItems().stream()
                .filter(c -> c.getId().equals(enterId))
                .findFirst()
                .orElse(null);

        if (customer != null) {
            if (customerData.hasActiveReservation(customer.getId())) {
                System.out.println("Customer " + customer.getName() + " has active reservations. Please return the bicycle before deleting the customer.");
                return null;
            } else {
                customerData.deleteCustomer(customer.getId());
                System.out.println("Customer " + customer.getName() + " with ID " + customer.getId() + " will be deleted.");
                customerService.removeItem(customer); // Remove from the service list
            }
        } else {
            System.out.println("Customer with ID " + enterId + " not found.");
        }

        return customer;
    }

    public void loadData(){
        List<Customer> customers = customerData.getAllCustomers();
        customerService.loadItems(customers);
    }


}
