package carsharing.observer;

import carsharing.model.CarEvent;
import carsharing.util.Logger;

/** ConcreteObserver: служба обслуговування парку. Реагує на низький заряд і завершення поїздки. */
public class FleetService implements CarEventObserver {
    @Override
    public void onEvent(CarEvent event) {
        switch (event.type()) {
            case LOW_BATTERY -> Logger.log(toString(), "планує зарядку авто " + event.carPlate() + " (" + event.details() + ")");
            case TRIP_FINISHED -> Logger.log(toString(), "позначає авто " + event.carPlate() + " вільним");
            default -> { }
        }
    }

    @Override
    public String toString() {
        return "FleetService";
    }
}
