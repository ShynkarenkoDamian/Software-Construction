package carsharing;

import carsharing.common.Log;
import carsharing.task1_adapter.FleetMap;
import carsharing.task1_adapter.LegacyTelematicsUnit;
import carsharing.task1_adapter.VendorTelematicsAdapter;
import carsharing.task1_adapter.VendorTelematicsBlock;
import carsharing.task2_facade.CarReservationService;
import carsharing.task2_facade.Dispatcher;
import carsharing.task2_facade.DoorLockService;
import carsharing.task2_facade.DriverLicenseService;
import carsharing.task2_facade.MobileApp;
import carsharing.task2_facade.PaymentService;
import carsharing.task2_facade.TelematicsActivator;
import carsharing.task2_facade.TripFacade;
import carsharing.task2_facade.TripInsuranceService;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/** Клас-раннер: окремий метод для кожної ситуації варіанта 8 «Каршерінг «Міськавто»». */
public class Main {

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        situation1Adapter();
        situation2Facade();
        situation3Proxy();
        situation4Decorator();
    }

    private static void header(String title) {
        System.out.println();
        System.out.println("=== " + title + " ===");
    }

    /** Завдання 1: підключення телематики нового постачальника (Adapter). */
    private static void situation1Adapter() {
        header("Завдання 1. Телематика нового постачальника (Adapter)");

        VendorTelematicsBlock block = new VendorTelematicsBlock(
                "CE1234AA", new int[]{48, 17, 30}, new int[]{25, 56, 6}, 21.0);

        Log.log("Runner", "1) дані нового блоку у ЙОГО форматі (без адаптера)");
        block.readPlate();
        block.readLatDms();
        block.readLonDms();
        block.readFuelLiters();

        Log.log("Runner", "2) карта парку з новим блоком через адаптер");
        FleetMap map = new FleetMap();
        map.addCar(new VendorTelematicsAdapter(block, 42.0));
        map.render();

        Log.log("Runner", "3) на карті одночасно авто зі старим і новим блоком");
        map.addCar(new LegacyTelematicsUnit("CE5678BB", 48.2915, 25.9403, 80));
        map.render();
    }

    /** Завдання 2: початок поїздки (Facade). */
    private static void situation2Facade() {
        header("Завдання 2. Початок поїздки (Facade)");

        DoorLockService doorLock = new DoorLockService();
        TripFacade facade = new TripFacade(
                new DriverLicenseService(), new PaymentService(), new CarReservationService(),
                doorLock, new TelematicsActivator(), new TripInsuranceService());
        MobileApp app = new MobileApp(facade);
        Dispatcher dispatcher = new Dispatcher(facade);

        Log.log("Runner", "1) поїздка через застосунок");
        app.startTrip("Ткачук", "CE1234AA");

        Log.log("Runner", "2) поїздка через диспетчера (та сама операція)");
        dispatcher.startTrip("Мельник", "CE5678BB");

        Log.log("Runner", "3) служба підтримки розблоковує двері напряму, без поїздки");
        doorLock.unlock("CE1234AA");
    }

    /** Завдання 3: керування авто через віддалений сервер (Proxy). */
    private static void situation3Proxy() {
        header("Завдання 3. Віддалений сервер (Proxy)");
        // TODO (колега): змоделювати ситуацію, див. carsharing.task3_proxy.package-info
        Log.log("Runner", "не реалізовано: точка розширення для Завдання 3");
    }

    /** Завдання 4: додаткові опції поїздки (Decorator). */
    private static void situation4Decorator() {
        header("Завдання 4. Опції поїздки (Decorator)");
        // TODO (колега): змоделювати ситуацію, див. carsharing.task4_decorator.package-info
        Log.log("Runner", "не реалізовано: точка розширення для Завдання 4");
    }
}
