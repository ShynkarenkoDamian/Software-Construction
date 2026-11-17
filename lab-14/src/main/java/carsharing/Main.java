package carsharing;

import carsharing.dispatch.CarWashService;
import carsharing.dispatch.ClientApp;
import carsharing.dispatch.DispatchCenter;
import carsharing.dispatch.FleetMap;
import carsharing.dispatch.MaintenanceService;
import carsharing.dispatch.SupportService;
import carsharing.dispatch.TowService;
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

    /** Завдання 2: диспетчерська служба (Mediator). */
    static void scenarioDispatch() {
        Logger.log("Runner", "=== Ситуація 2: диспетчерська служба ===");
        DispatchCenter center = new DispatchCenter();
        ClientApp clientApp = new ClientApp(center, "Іван");
        FleetMap fleetMap = new FleetMap(center);
        TowService towService = new TowService(center);
        MaintenanceService maintenance = new MaintenanceService(center);
        SupportService support = new SupportService(center);

        center.register(clientApp);
        center.register(fleetMap);
        center.register(towService);
        center.register(support);

        Logger.log("Runner", "--- Служба обслуговування повідомляє про завершення ремонту ---");
        maintenance.finishRepair("AA1234BB");

        Logger.log("Runner", "--- Клієнт повідомляє про ДТП ---");
        clientApp.reportAccident("AA1234BB");

        Logger.log("Runner", "--- Додається служба мийки; нове ДТП, після евакуації авто йде на мийку ---");
        center.register(new CarWashService(center));
        clientApp.reportAccident("AA5678CC");
        towService.completeEvacuation("AA5678CC");
    }
}
