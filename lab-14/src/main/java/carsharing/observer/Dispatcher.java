package carsharing.observer;

import carsharing.model.CarEvent;
import carsharing.util.Logger;

/** ConcreteObserver: диспетчер. Реагує на удар і виїзд за межі зони. */
public class Dispatcher implements CarEventObserver {
    @Override
    public void onEvent(CarEvent event) {
        switch (event.type()) {
            case CRASH -> Logger.log(toString(), "відкриває інцидент: удар авто " + event.carPlate());
            case OUT_OF_ZONE -> Logger.log(toString(), "фіксує виїзд за межі зони авто " + event.carPlate());
            default -> { }
        }
    }

    @Override
    public String toString() {
        return "Dispatcher";
    }
}
