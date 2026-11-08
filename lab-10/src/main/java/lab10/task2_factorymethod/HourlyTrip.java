package lab10.task2_factorymethod;

import lab10.domain.Car;
import lab10.domain.Client;

import java.time.Duration;

/** Конкретний продукт: погодинна поїздка. */
class HourlyTrip extends Trip {

    private static final double PRICE_PER_HOUR = 240.00;

    HourlyTrip(Car car, Client client) {
        super(car, client);
    }

    @Override
    protected double calculateCost(Duration duration) {
        long startedHours = (duration.toMinutes() + 59) / 60;
        return startedHours * PRICE_PER_HOUR;
    }

    @Override
    public String getRentalName() {
        return "Погодинна оренда";
    }

    @Override
    public String describeTariff() {
        return formatMoney(PRICE_PER_HOUR) + " за годину (кожна розпочата година)";
    }
}
