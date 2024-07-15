package manager.model;

import manager.interfaces.IBicycle;

public abstract class Bicycle implements IBicycle {
    private String id;
    private String model;
    private boolean isRented;

    public Bicycle(String id, String model){
        this.id = id;
        this.model = model;
        this.isRented=false;
    }

    @Override
    public void rent(){
        if (!isRented){
            isRented = true;
            System.out.println("Bicycle " + id + " rented.");
        }else {
            System.out.println("Bicycle " + id + " is already rented.");
        }
    }

    @Override
    public void returnBicycle(){
        if (isRented) {
            isRented = false;
            System.out.println("Bicycle " + id + " returned.");
        } else {
            System.out.println("Bicycle " + id + " is not rented.");
        }
    }

    public String getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public boolean isRented() {
        return isRented;
    }
}
