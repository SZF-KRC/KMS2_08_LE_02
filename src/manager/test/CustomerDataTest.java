package manager.test;

import manager.database.CustomerData;
import manager.model.Customer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomerDataTest {

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

