package lab10.task2_factorymethod;

import lab10.domain.Car;
import lab10.domain.Client;

import java.time.Duration;
import java.util.Locale;

/**
 * Абстрактний продукт: поїздка. Кожен вид оренди має власний розрахунок вартості.
 */
public abstract class Trip {

    private static final Locale UA = Locale.forLanguageTag("uk-UA");

    private final Car car;
    private final Client client;
    private Duration duration;
    private double cost;
    private boolean finished;

    protected Trip(Car car, Client client) {
        this.car = car;
        this.client = client;
    }

    /** Завершує поїздку і розраховує вартість за правилами свого виду оренди. */
    public void finish(Duration duration) {
        this.duration = duration;
        this.cost = calculateCost(duration);
        this.finished = true;
    }

    /** Вартість поїздки заданої тривалості за правилами виду оренди. */
    protected abstract double calculateCost(Duration duration);

    /** Назва виду оренди. */
    public abstract String getRentalName();

    /** Опис тарифу виду оренди. */
    public abstract String describeTariff();

    public Car getCar() {
        return car;
    }

    public Client getClient() {
        return client;
    }

    public Duration getDuration() {
        return duration;
    }

    public double getCost() {
        return cost;
    }

    public boolean isFinished() {
        return finished;
    }

    public static String formatMoney(double amount) {
        return String.format(UA, "%.2f", amount) + " грн";
    }

    public static String formatDuration(Duration duration) {
        return duration.toHours() + " год " + duration.toMinutesPart() + " хв";
    }

    @Override
    public String toString() {
        String result = getRentalName() + ": авто " + car + ", клієнт " + client;
        if (finished) {
            result += ", тривалість " + formatDuration(duration) + ", вартість " + formatMoney(cost);
        }
        return result;
    }
}
