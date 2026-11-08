package lab10;

import lab10.common.ConsoleLogger;
import lab10.domain.Car;
import lab10.domain.Client;
import lab10.task1_abstractfactory.FleetMaintenanceService;
import lab10.task1_abstractfactory.electric.ElectricEquipmentFactory;
import lab10.task1_abstractfactory.gas.GasEquipmentFactory;
import lab10.task1_abstractfactory.hybrid.HybridEquipmentFactory;
import lab10.task1_abstractfactory.petrol.PetrolEquipmentFactory;
import lab10.task2_factorymethod.DailyRental;
import lab10.task2_factorymethod.HourlyRental;
import lab10.task2_factorymethod.MinuteRental;
import lab10.task2_factorymethod.RentalProcess;
import lab10.task2_factorymethod.weekend.WeekendRental;
import lab10.task3_builder.Booking;
import lab10.task3_builder.BookingDirector;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Раннер лабораторної роботи №10, варіант 8 — «Каршерінг «Міськавто»».
 * Окремий метод на кожну проблемну ситуацію.
 */
public class Main {

    public static void main(String[] args) {
        // щоб українські символи коректно виводилися в консолі незалежно від платформи
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8));

        situation1AbstractFactory();
        situation2FactoryMethod();
        situation3Builder();
    }

    // ------------------------------------------------------------------
    // Ситуація 1. Обслуговування авто різних типів — Abstract Factory
    // ------------------------------------------------------------------
    static void situation1AbstractFactory() {
        ConsoleLogger.section("Ситуація 1. Обслуговування авто різних типів (Abstract Factory)");

        FleetMaintenanceService service = new FleetMaintenanceService();

        ConsoleLogger.log("Main", "Вводимо в роботу бензинове авто, гібрид і електромобіль одним кодом служби");
        service.commissionCar("CE1234AA", new PetrolEquipmentFactory());
        service.commissionCar("CE2345BB", new HybridEquipmentFactory());
        service.commissionCar("CE3456CC", new ElectricEquipmentFactory());

        ConsoleLogger.log("Main", "Додаємо тип «авто на газу»: лише нові класи (пакет gas), "
                + "FleetMaintenanceService не змінюється");
        System.out.println(service.commissionCar("CE4567DD", new GasEquipmentFactory()));
    }

    // ------------------------------------------------------------------
    // Ситуація 2. Види оренди — Factory Method
    // ------------------------------------------------------------------
    static void situation2FactoryMethod() {
        ConsoleLogger.section("Ситуація 2. Види оренди (Factory Method)");

        Car car = new Car("CE1234AA");
        Client client = new Client("Ткачук", "ВХС 123456");
        Duration threeHours = Duration.ofHours(3);

        ConsoleLogger.log("Main", "Поїздка тривалістю 3 години за хвилинним, погодинним і добовим видом оренди");
        RentalProcess[] rentals = {new MinuteRental(), new HourlyRental(), new DailyRental()};
        for (RentalProcess rental : rentals) {
            System.out.println("  → " + rental.rent(car, client, threeHours));
        }

        ConsoleLogger.log("Main", "Додаємо оренду «вихідні»: лише нові класи (пакет weekend), "
                + "RentalProcess не змінюється");
        System.out.println("  → " + new WeekendRental().rent(car, client, threeHours));
    }

    // ------------------------------------------------------------------
    // Ситуація 3. Бронювання авто — Builder
    // ------------------------------------------------------------------
    static void situation3Builder() {
        ConsoleLogger.section("Ситуація 3. Бронювання авто (Builder)");

        // new Booking(...);  // не компілюється: конструктор Booking приватний, лише Booking.Builder.build()

        // «Поточний» час фіксований, щоб перевірка «не в минулому» і журнал були відтворюваними
        ZoneId zone = ZoneId.systemDefault();
        Clock clock = Clock.fixed(LocalDateTime.of(2026, 11, 8, 9, 0).atZone(zone).toInstant(), zone);
        Car car = new Car("CE1234AA");
        Client client = new Client("Ткачук", "ВХС 123456");

        ConsoleLogger.log("Main", "Формуємо бронювання з дитячим кріслом і подачею до адреси: крок за кроком");
        Booking full = new Booking.Builder(clock)
                .withClient(client)
                .withCar(car)
                .withStartTime(LocalDateTime.of(2026, 11, 9, 10, 0))
                .withChildSeat()
                .withDeliveryTo("вул. Головна, 12")
                .build();
        System.out.println(full.describe());

        BookingDirector director = new BookingDirector(clock);
        ConsoleLogger.log("Main", "Формуємо типове бронювання «Аеропорт»");
        Booking airport = director.buildAirportTrip(client, car, LocalDateTime.of(2026, 11, 10, 5, 30));
        System.out.println(airport.describe());

        ConsoleLogger.log("Main", "Формуємо типове бронювання «Поїздка з дитиною» тією ж послідовністю кроків");
        Booking child = director.buildChildTrip(client, car, LocalDateTime.of(2026, 11, 9, 15, 0));
        System.out.println(child.describe());

        ConsoleLogger.log("Main", "Спроба завершити формування бронювання без авто");
        attemptBooking(new Booking.Builder(clock)
                .withClient(client)
                .withStartTime(LocalDateTime.of(2026, 11, 9, 12, 0)));

        ConsoleLogger.log("Main", "Додаткова перевірка: час початку в минулому");
        attemptBooking(new Booking.Builder(clock)
                .withClient(client)
                .withCar(car)
                .withStartTime(LocalDateTime.of(2026, 11, 7, 12, 0)));
    }

    private static void attemptBooking(Booking.Builder builder) {
        try {
            builder.build();
        } catch (IllegalStateException e) {
            ConsoleLogger.log("Main", "Бронювання не створено: некоректний об'єкт у систему не потрапив");
        }
    }
}
