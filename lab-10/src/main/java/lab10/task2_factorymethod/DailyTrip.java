package lab10.task2_factorymethod;

import lab10.domain.Car;
import lab10.domain.Client;

import java.time.Duration;

/** Конкретний продукт: добова поїздка. */
class DailyTrip extends Trip {

    private static final double PRICE_PER_DAY = 1500.00;

    DailyTrip(Car car, Client client) {
        super(car, client);
    }

    @Override
    protected double calculateCost(Duration duration) {
        long startedDays = (duration.toMinutes() + 24 * 60 - 1) / (24 * 60);
        return Math.max(1, startedDays) * PRICE_PER_DAY;
    }

    @Override
    public String getRentalName() {
        return "Добова оренда";
    }

    @Override
    public String describeTariff() {
        return formatMoney(PRICE_PER_DAY) + " за добу (кожна розпочата доба)";
    }
}
