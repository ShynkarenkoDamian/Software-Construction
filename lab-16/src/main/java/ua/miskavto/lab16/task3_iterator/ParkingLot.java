package ua.miskavto.lab16.task3_iterator;

import java.util.ArrayList;
import java.util.List;

/** Майданчик: містить авто й має номер зупинки в маршруті служби обслуговування. */
public class ParkingLot {

    private final String name;
    private final int routeOrder;
    private final List<ParkedCar> cars = new ArrayList<>();

    public ParkingLot(String name, int routeOrder) {
        this.name = name;
        this.routeOrder = routeOrder;
    }

    public void addCar(ParkedCar car) {
        cars.add(car);
    }

    public String getName() {
        return name;
    }

    public int getRouteOrder() {
        return routeOrder;
    }

    /** Доступно лише ітераторам цього пакета: клієнти списку авто не бачать. */
    List<ParkedCar> cars() {
        return cars;
    }
}
