package manager.management;

import manager.control.InputControl;
import manager.model.Bicycle;
import manager.model.MountainBike;
import manager.service.RentalService;

public class BicycleManagement {
    private InputControl control;
    private RentalService<Bicycle> bicycleService;

    public BicycleManagement(InputControl control, RentalService<Bicycle> bicycleService) {
        this.control = control;
        this.bicycleService = bicycleService;
    }

    public Bicycle addBicycle() {
        Bicycle bicycle = new MountainBike(control.generateUniqueID(), control.textEntry("Enter model name: "));
        bicycleService.addItem(bicycle);
        return bicycle;
    }

    public Bicycle deleteBicycle() {
        String enterId = control.textEntry("Enter ID Customer to delete");
        Bicycle bicycle = bicycleService.getItems().stream()
                .filter(c -> c.getId().equals(enterId))
                .findFirst()
                .orElse(null);

        if (bicycle != null) {
            System.out.println("Bicycle " + bicycle.getModel() + " with ID " + bicycle.getId() + " will be deleted.");
        } else {
            System.out.println("Bicycle with ID " + enterId + " not found.");
        }

        return bicycle;
    }
}
