package carsharing.task2_composite;

import carsharing.common.Log;

/** Leaf: паркувальний майданчик, нащадків не має. */
public class ParkingLot implements ZoneComponent {

    private final String name;
    private final int carCount;
    private final int revenue;

    public ParkingLot(String name, int carCount, int revenue) {
        this.name = name;
        this.carCount = carCount;
        this.revenue = revenue;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getCarCount() {
        return carCount;
    }

    @Override
    public int getRevenue() {
        return revenue;
    }

    @Override
    public void print(int level) {
        Log.log("ParkingLot", "  ".repeat(level) + "- " + name + ": авто " + carCount + ", виручка " + revenue + " грн");
    }
}
