package carsharing.observer;

import carsharing.model.CarEvent;
import carsharing.util.Logger;

/** ConcreteObserver: клієнт (підписаний лише на час поїздки). Реагує на заряд, зону і завершення поїздки. */
public class Client implements CarEventObserver {
    private final String name;

    public Client(String name) {
        this.name = name;
    }

    @Override
    public void onEvent(CarEvent event) {
        switch (event.type()) {
            case LOW_BATTERY -> Logger.log(this.toString(), "отримав попередження про низький заряд: " + event.details());
            case OUT_OF_ZONE -> Logger.log(this.toString(), "отримав попередження про виїзд за межі зони");
            case TRIP_FINISHED -> Logger.log(this.toString(), "отримав повідомлення про завершення поїздки");
            default -> { } // на інші події клієнт не реагує
        }
    }

    @Override
    public String toString() {
        return "Client " + name;
    }
}
