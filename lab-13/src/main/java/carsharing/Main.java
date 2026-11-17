package carsharing;

import carsharing.access.BanCheck;
import carsharing.access.CarClass;
import carsharing.access.DebtCheck;
import carsharing.access.DriverLicenseCheck;
import carsharing.access.DrivingExperienceCheck;
import carsharing.access.FundsCheck;
import carsharing.access.TripAccessService;
import carsharing.access.TripRequest;
import carsharing.command.BlockEngineCommand;
import carsharing.command.CloseDoorsCommand;
import carsharing.command.HazardLightsCommand;
import carsharing.command.OpenDoorsCommand;
import carsharing.command.RemoteControl;
import carsharing.model.Car;
import carsharing.model.Customer;
import carsharing.util.Logger;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/** Раннер: окремий метод для кожної ситуації. */
public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        scenarioRemoteCommands();
        System.out.println();
        scenarioTripAccess();
    }

    /** Завдання 1: віддалені команди авто (Command). */
    static void scenarioRemoteCommands() {
        Logger.log("Runner", "=== Ситуація 1: віддалені команди авто ===");
        Car car = new Car("AA1234BB");
        RemoteControl remote = new RemoteControl();

        Logger.log("Runner", "--- Команди «відкрити» і «заблокувати двигун» ---");
        remote.send("Клієнт", new OpenDoorsCommand(car));
        remote.send("Служба підтримки", new BlockEngineCommand(car));

        Logger.log("Runner", "--- Скасування блокування двигуна ---");
        remote.undoLast();

        Logger.log("Runner", "--- Авто втратило зв'язок, 2 команди ---");
        car.setOnline(false);
        remote.send("Служба підтримки", new HazardLightsCommand(car));
        remote.send("Клієнт", new CloseDoorsCommand(car));

        Logger.log("Runner", "--- Авто повернулося на зв'язок ---");
        remote.carOnline(car);

        remote.printJournal();
    }

    /** Завдання 2: допуск клієнта до поїздки (Chain of Responsibility). */
    static void scenarioTripAccess() {
        Logger.log("Runner", "=== Ситуація 2: допуск клієнта до поїздки ===");
        TripAccessService service = new TripAccessService();

        // Склад і порядок перевірок задаються під час виконання, окремо для кожного класу авто
        service.configure(CarClass.STANDARD,
                new DriverLicenseCheck(), new DebtCheck(), new FundsCheck(), new BanCheck());
        service.configure(CarClass.PREMIUM,
                new DriverLicenseCheck(), new DrivingExperienceCheck(3), new DebtCheck(),
                new FundsCheck(), new BanCheck());

        Logger.log("Runner", "--- Клієнт проходить усі перевірки для звичайного авто ---");
        Customer olena = new Customer("Олена Коваль", true, 5, 0, 2000, false);
        service.requestAccess(new TripRequest(olena, "AA1234BB", CarClass.STANDARD, 500));

        Logger.log("Runner", "--- Клієнт із заборгованістю зупиняється на перевірці заборгованості ---");
        Customer petro = new Customer("Петро Мельник", true, 8, 350, 2000, false);
        service.requestAccess(new TripRequest(petro, "AA1234BB", CarClass.STANDARD, 500));

        Logger.log("Runner", "--- Клієнт зі стажем 1 рік бере преміальне авто ---");
        Customer andrii = new Customer("Андрій Бойко", true, 1, 0, 10000, false);
        service.requestAccess(new TripRequest(andrii, "KA7777XX", CarClass.PREMIUM, 3000));
    }
}
