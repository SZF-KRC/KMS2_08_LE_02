package manager.model;

public class MountainBike extends Bicycle{

    public MountainBike(String id, String model){
        super(id, model);
    }
    @Override
    public void performMaintenance() {
        System.out.println("Performing maintenance on mountain bike " + getId());
    }
}
