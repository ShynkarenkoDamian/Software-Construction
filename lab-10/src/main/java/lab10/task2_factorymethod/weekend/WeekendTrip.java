package lab10.task2_factorymethod.weekend;

import lab10.domain.Car;
import lab10.domain.Client;
import lab10.task2_factorymethod.Trip;

import java.time.Duration;

/** Конкретний продукт (доданий пізніше): поїздка за орендою на вихідні. */
class WeekendTrip extends Trip {

    private static final double WEEKEND_PRICE = 1800.00;

    WeekendTrip(Car car, Client client) {
        super(car, client);
    }

    @Override
    protected double calculateCost(Duration duration) {
        return WEEKEND_PRICE;
    }

    @Override
    public String getRentalName() {
        return "Оренда на вихідні";
    }

    @Override
    public String describeTariff() {
        return formatMoney(WEEKEND_PRICE) + " фіксовано за вихідні";
    }
}
