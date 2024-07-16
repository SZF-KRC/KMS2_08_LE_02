package manager.model;

import manager.control.InputControl;

public class MountainBike extends Bicycle{
    private boolean needsMaintenance;
    private InputControl control = new InputControl();

    public MountainBike(String id, String model){
        super(id, model);
        this.needsMaintenance = false;
    }
    @Override
    public void performMaintenance() {
        System.out.println("Performing maintenance on mountain bike " + getId());
        needsMaintenance = false;
    }

    @Override
    public void rent(){
        if (needsMaintenance) {
            String answer = control.stringEntry("Bicycle " + getId() + " needs maintenance. Do you want to rent it without maintenance? (yes/no)");
            if (!answer.equalsIgnoreCase("yes")) {
                System.out.println("Bicycle " + getId() + " will not be rented.");
                return;
            }
        }
        super.rent();
    }
    @Override
    public void returnBicycle(){
        super.returnBicycle();
        needsMaintenance = true;
    }

    public boolean isNeedsMaintenance() {
        return needsMaintenance;
    }

    public void setNeedsMaintenance(boolean needsMaintenance) {
        this.needsMaintenance = needsMaintenance;
    }
}
