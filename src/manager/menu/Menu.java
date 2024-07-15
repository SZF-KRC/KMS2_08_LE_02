package manager.menu;

import manager.control.InputControl;
import manager.management.BicycleManagement;
import manager.management.CustomerManagement;
import manager.management.EmployeeManager;
import manager.management.ReservationManagement;
import manager.model.Bicycle;
import manager.model.Customer;
import manager.model.Employee;
import manager.service.RentalService;
import manager.model.Reservation;

public class Menu {
    InputControl control = new InputControl();

    RentalService<Bicycle> bicycleService = new RentalService<>();
    RentalService<Customer> customerService = new RentalService<>();
    RentalService<Employee> employeeService = new RentalService<>();
    RentalService<Reservation> reservationService = new RentalService<>();

    CustomerManagement customManage = new CustomerManagement(control,customerService);
    EmployeeManager employeeManager = new EmployeeManager(control,employeeService);
    BicycleManagement bicycleManagement = new BicycleManagement(control,bicycleService);
    ReservationManagement reservationManagement = new ReservationManagement(control,reservationService,customerService,bicycleService);

    public  void mainMenu(){
        boolean exit = false;
        while (!exit){
            System.out.println("\n*** Main Menu ***");
            System.out.println("0. Exit");
            System.out.println("1. Customer management");
            System.out.println("2. Employee management");
            System.out.println("3. Bicycle management");
            System.out.println("4. Reservation management");
            switch (control.intEntry("Enter index of your choice: ")){
                case 0:exit = true;break;
                case 1:customerManager();break;
                case 2:employeeManager(); break;
                case 3:break;
                case 4:;break;
                default: printDefaultAnswer(4);break;
            }
        }
    }
    private void customerManager(){
        boolean exit = false;
        while (!exit){
            System.out.println("\n*** Personnel Records ***");
            System.out.println("0. Back To Main Menu");
            System.out.println("1. Add New Customer");
            System.out.println("2. Delete Customer");
            System.out.println("3. List All Customers");
            switch (control.intEntry("Enter index of your choice: ")){
                case 0:exit = true;break;
                case 1:customerService.addItem(customManage.addCustomer());break;
                case 2:customerService.removeItem(customManage.deleteCustomer()); break;
                case 3:customerService.forEachItem(customer -> System.out.println("Customer ID: " + customer.getId() + ", Name: " + customer.getName() + ", Phone: " + customer.getContact()));break;
                default:printDefaultAnswer(4);
            }
        }
    }

    private void employeeManager(){
        boolean exit = false;
        while (!exit){
            System.out.println("\n*** Personnel Records ***");
            System.out.println("0. Back To Main Menu");
            System.out.println("1. Add New Employee");
            System.out.println("2. Delete Employee");
            System.out.println("3. List All Employees");
            switch (control.intEntry("Enter index of your choice: ")){
                case 0:exit = true;break;
                case 1:employeeService.addItem(employeeManager.addEmployee());break;
                case 2:employeeService.removeItem(employeeManager.deleteEmployee()); break;
                case 3:employeeService.forEachItem(employee -> System.out.println("Customer ID: " + employee.getId() + ", Name: " + employee.getName() + ", Salary: " + employee.getSalary()));break;
                default:printDefaultAnswer(4);
            }
        }
    }

    private void bicycleManager(){
        boolean exit = false;
        while (!exit){
            System.out.println("\n*** Personnel Records ***");
            System.out.println("0. Back To Main Menu");
            System.out.println("1. Add New Bicycle");
            System.out.println("2. Delete Bicycle");
            System.out.println("3. List All Bicycles");
            switch (control.intEntry("Enter index of your choice: ")){
                case 0:exit = true;break;
                case 1:bicycleService.addItem(bicycleManagement.addBicycle());break;
                case 2:bicycleService.removeItem(bicycleManagement.deleteBicycle()); break;
                case 3:bicycleService.forEachItem(bicycle -> System.out.println("Bicycle ID: " + bicycle.getId() + ", Model: " + bicycle.getModel() ));break;
                default:printDefaultAnswer(4);
            }
        }
    }

    private void reservationManager(){
        boolean exit = false;
        while (!exit){
            System.out.println("\n*** Personnel Records ***");
            System.out.println("0. Back To Main Menu");
            System.out.println("1. Add New Reservation");
            System.out.println("2. Delete Reservation");
            System.out.println("3. List All Reservations");
            switch (control.intEntry("Enter index of your choice: ")){
                case 0:exit = true;break;
                case 1:reservationService.addItem(reservationManagement.addReservation());break;
                case 2:reservationService.removeItem(reservationManagement.deleteReservation()); break;
                case 3:reservationService.forEachItem(reservation -> System.out.println(reservation.toString()));break;
                default:printDefaultAnswer(4);
            }
        }
    }


    private void printDefaultAnswer(int number){
        System.out.println("\n--- Enter only index between 0 and "+ number+" ---\n");
    }
}
