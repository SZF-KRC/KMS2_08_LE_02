package manager.management;

import manager.control.InputControl;
import manager.database.BicycleData;
import manager.model.Bicycle;
import manager.model.MountainBike;
import manager.service.RentalService;

import java.util.List;

public class BicycleManagement {
    private InputControl control;
    private RentalService<Bicycle> bicycleService;
    private BicycleData bicycleData;

    public BicycleManagement(InputControl control, RentalService<Bicycle> bicycleService) {
        this.control = control;
        this.bicycleService = bicycleService;
        this.bicycleData = new BicycleData();
    }

    public Bicycle addBicycle() {
        Bicycle bicycle = new MountainBike(control.generateUniqueID(), control.textEntry("Enter model name: "));
        bicycleData.addBicycle(bicycle);
        return bicycle;
    }

    public Bicycle deleteBicycle() {
        String enterId = control.textEntry("Enter Bicycle ID to delete: ");
        Bicycle bicycle = bicycleService.getItems().stream()
                .filter(c -> c.getId().equals(enterId))
                .findFirst()
                .orElse(null);

        if (bicycle != null) {
            if (bicycleData.isBicycleRented(bicycle.getId())) {
                System.out.println("Bicycle " + bicycle.getModel() + " is currently rented. Please return the bicycle before deleting.");
                return null;
            } else {
                bicycleData.deleteBicycle(bicycle.getId());
                System.out.println("Bicycle " + bicycle.getModel() + " with ID " + bicycle.getId() + " will be deleted.");
                bicycleService.removeItem(bicycle); // Remove from the service list
            }
        } else {
            System.out.println("Bicycle with ID " + enterId + " not found.");
        }

        return bicycle;
    }

    public void loadData(){
        List<Bicycle> bicycles = bicycleData.getAllBicycles();
        bicycleService.loadItems(bicycles);
    }
}
