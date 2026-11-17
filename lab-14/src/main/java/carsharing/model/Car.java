package carsharing.model;

import carsharing.observer.CarEventObserver;
import carsharing.util.Logger;

import java.util.ArrayList;
import java.util.List;

/** Subject: авто не знає конкретних отримувачів, а сповіщає всіх підписників. */
public class Car {
    private final String plate;
    private final List<CarEventObserver> observers = new ArrayList<>();

    public Car(String plate) {
        this.plate = plate;
    }

    public String getPlate() {
        return plate;
    }

    public void subscribe(CarEventObserver observer) {
        observers.add(observer);
        Logger.log("Car " + plate, "новий підписник: " + observer);
    }

    public void unsubscribe(CarEventObserver observer) {
        observers.remove(observer);
        Logger.log("Car " + plate, "відписався: " + observer);
    }

    /** Фіксує подію і сповіщає всіх поточних підписників. */
    public void publish(CarEventType type, String details) {
        CarEvent event = new CarEvent(type, plate, details);
        String info = details.isEmpty() ? "" : " (" + details + ")";
        Logger.log("Car " + plate, "подія: " + type.getLabel() + info + ", підписників: " + observers.size());
        for (CarEventObserver observer : new ArrayList<>(observers)) {
            observer.onEvent(event);
        }
    }
}
