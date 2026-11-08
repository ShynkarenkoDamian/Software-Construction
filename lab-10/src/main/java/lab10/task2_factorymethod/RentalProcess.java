package lab10.task2_factorymethod;

import lab10.common.ConsoleLogger;
import lab10.domain.Car;
import lab10.domain.Client;

import java.time.Duration;

/**
 * Творець (Creator) шаблону Factory Method: загальний процес оренди.
 * <p>
 * Метод {@link #rent} описує процес один раз: перевірити авто і клієнта, відкрити поїздку,
 * завершити її, виставити оплату. Він {@code final} і не містить жодних розгалужень за видом
 * оренди. Єдине, що змінюється, — яку саме поїздку створити, і це визначає
 * фабричний метод {@link #createTrip}, який перевизначають нащадки.
 */
public abstract class RentalProcess {

    private static final String SOURCE = "RentalProcess";

    public final Trip rent(Car car, Client client, Duration duration) {
        verify(car, client);

        Trip trip = createTrip(car, client); // фабричний метод
        ConsoleLogger.log(SOURCE, "Поїздку відкрито: " + trip.getRentalName()
                + ", авто " + car + ", клієнт " + client);

        trip.finish(duration);
        ConsoleLogger.log(SOURCE, "Поїздку завершено, тривалість " + Trip.formatDuration(duration));

        ConsoleLogger.log(SOURCE, "Виставлено оплату: " + Trip.formatMoney(trip.getCost())
                + "; тариф: " + trip.describeTariff());
        return trip;
    }

    /** Фабричний метод: кожен вид оренди створює свій вид поїздки. */
    protected abstract Trip createTrip(Car car, Client client);

    private void verify(Car car, Client client) {
        if (car == null || client == null || client.getDriverLicense() == null
                || client.getDriverLicense().isBlank()) {
            throw new IllegalArgumentException("Оренда неможлива: не вказано авто або посвідчення водія клієнта");
        }
        ConsoleLogger.log(SOURCE, "Перевірка авто " + car + " і клієнта " + client + ": успішно");
    }
}
