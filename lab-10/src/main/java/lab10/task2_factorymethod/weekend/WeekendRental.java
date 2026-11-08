package lab10.task2_factorymethod.weekend;

import lab10.domain.Car;
import lab10.domain.Client;
import lab10.task2_factorymethod.RentalProcess;
import lab10.task2_factorymethod.Trip;

/** Конкретний творець (доданий пізніше): оренда на вихідні. Клас RentalProcess не змінювався. */
public class WeekendRental extends RentalProcess {

    @Override
    protected Trip createTrip(Car car, Client client) {
        return new WeekendTrip(car, client);
    }
}
