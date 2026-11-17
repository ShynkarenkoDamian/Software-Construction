package carsharing;

import carsharing.model.Car;
import carsharing.model.CarEventType;
import carsharing.observer.Client;
import carsharing.observer.Dispatcher;
import carsharing.observer.FleetService;
import carsharing.observer.InsuranceCompany;
import carsharing.util.Logger;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/** Раннер: окремий метод для кожної ситуації. */
public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        scenarioCarEvents();
        System.out.println();
        scenarioDispatch();
    }

    /** Завдання 1: події авто (Observer). */
    static void scenarioCarEvents() {
        Logger.log("Runner", "=== Ситуація 1: події авто ===");
        Car car = new Car("AA1234BB");
        Client client = new Client("Іван");

        car.subscribe(new FleetService());
        car.subscribe(new Dispatcher());
        car.subscribe(new InsuranceCompany());

        Logger.log("Runner", "--- Клієнт починає поїздку і підписується; заряд падає до 10% ---");
        car.subscribe(client);
        car.publish(CarEventType.LOW_BATTERY, "10%");

        Logger.log("Runner", "--- Фіксується удар ---");
        car.publish(CarEventType.CRASH, "");

        Logger.log("Runner", "--- Поїздка завершується, клієнт відписується ---");
        car.publish(CarEventType.TRIP_FINISHED, "");
        car.unsubscribe(client);

        Logger.log("Runner", "--- Наступна подія: клієнт її не отримує ---");
        car.publish(CarEventType.LOW_BATTERY, "5%");
    }

    /** Завдання 2 (Mediator, «Диспетчерська служба») — виконує колега. */
    static void scenarioDispatch() {
        // TODO (колега): реалізувати посередника і служби в пакеті carsharing.dispatch
        //  і відтворити демонстраційний сценарій із завдання.
        Logger.log("Runner", "=== Ситуація 2: диспетчерська служба — буде виконано окремо ===");
    }
}
