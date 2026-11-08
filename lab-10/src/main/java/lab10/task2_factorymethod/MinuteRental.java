package lab10.task2_factorymethod;

import lab10.domain.Car;
import lab10.domain.Client;

/** Конкретний творець: оренда, що створює хвилинну поїздку. */
public class MinuteRental extends RentalProcess {

    @Override
    protected Trip createTrip(Car car, Client client) {
        return new MinuteTrip(car, client);
    }
}
