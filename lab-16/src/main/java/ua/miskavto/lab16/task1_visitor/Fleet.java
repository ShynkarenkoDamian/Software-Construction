package ua.miskavto.lab16.task1_visitor;

import ua.miskavto.lab16.common.Log;

import java.util.ArrayList;
import java.util.List;

/** ObjectStructure: парк. Виконує операцію над усіма авто за один прохід. */
public class Fleet {

    private final List<Vehicle> vehicles = new ArrayList<>();

    public void add(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public void accept(FleetVisitor visitor) {
        for (Vehicle vehicle : vehicles) {
            vehicle.accept(visitor);
        }
        visitor.onFinish();
    }

    public void logState(String stage) {
        Log.log("Fleet", "Стан парку " + stage + " (" + vehicles.size() + " авто):");
        for (Vehicle vehicle : vehicles) {
            Log.log("Fleet", "   " + vehicle);
        }
    }
}
