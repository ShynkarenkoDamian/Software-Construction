package lab10.task3_builder;

import lab10.common.ConsoleLogger;
import lab10.domain.Car;
import lab10.domain.Client;

import java.time.Clock;
import java.time.LocalDateTime;

/**
 * Розпорядник (Director): знає готові послідовності кроків для типових бронювань.
 * Кожну послідовність описано в одному місці, тому типові бронювання формуються однаково.
 */
public class BookingDirector {

    private static final String SOURCE = "BookingDirector";
    private static final String AIRPORT_ADDRESS = "Міжнародний аеропорт «Чернівці»";

    private final Clock clock;

    public BookingDirector(Clock clock) {
        this.clock = clock;
    }

    /** Типове бронювання «Поїздка з дитиною»: дитяче крісло і додаткове страхування. */
    public Booking buildChildTrip(Client client, Car car, LocalDateTime startTime) {
        ConsoleLogger.log(SOURCE, "Типове бронювання «Поїздка з дитиною»: дитяче крісло + додаткове страхування");
        return new Booking.Builder(clock)
                .withClient(client)
                .withCar(car)
                .withStartTime(startTime)
                .withChildSeat()
                .withExtraInsurance()
                .withComment("Типове бронювання «Поїздка з дитиною»")
                .build();
    }

    /** Типове бронювання «Аеропорт»: подача авто до аеропорту. */
    public Booking buildAirportTrip(Client client, Car car, LocalDateTime startTime) {
        ConsoleLogger.log(SOURCE, "Типове бронювання «Аеропорт»: подача авто до аеропорту");
        return new Booking.Builder(clock)
                .withClient(client)
                .withCar(car)
                .withStartTime(startTime)
                .withDeliveryTo(AIRPORT_ADDRESS)
                .withComment("Типове бронювання «Аеропорт»")
                .build();
    }
}
