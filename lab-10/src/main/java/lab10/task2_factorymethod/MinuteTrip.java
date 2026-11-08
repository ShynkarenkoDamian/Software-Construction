package lab10.task2_factorymethod;

import lab10.domain.Car;
import lab10.domain.Client;

import java.time.Duration;

/** Конкретний продукт: хвилинна поїздка. */
class MinuteTrip extends Trip {

    private static final double PRICE_PER_MINUTE = 5.00;

    MinuteTrip(Car car, Client client) {
        super(car, client);
    }

    @Override
    protected double calculateCost(Duration duration) {
        return duration.toMinutes() * PRICE_PER_MINUTE;
    }

    @Override
    public String getRentalName() {
        return "Хвилинна оренда";
    }

    @Override
    public String describeTariff() {
        return formatMoney(PRICE_PER_MINUTE) + " за хвилину";
    }
}
