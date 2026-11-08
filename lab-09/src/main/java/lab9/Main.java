package lab9;

import lab9.common.ConsoleLogger;
import lab9.task1_objectpool.Car;
import lab9.task1_objectpool.CarPool;
import lab9.task1_objectpool.Client;
import lab9.task1_objectpool.PersonalSettings;
import lab9.task2_prototype.EndZone;
import lab9.task2_prototype.InsuranceType;
import lab9.task2_prototype.TariffConfigurator;
import lab9.task2_prototype.TariffPlan;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Раннер лабораторної роботи №9, варіант 8 — «Каршерінг «Міськавто»».
 * Окремий метод на кожну проблемну ситуацію.
 */
public class Main {

    public static void main(String[] args) {
        // щоб українські символи коректно виводилися в консолі незалежно від платформи
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8));

        situation1ObjectPool();
        situation2Prototype();
        // TODO (колега): situation3Singleton();
    }

    // ------------------------------------------------------------------
    // Ситуація 1. Видача автомобілів парку — Object Pool
    // ------------------------------------------------------------------
    static void situation1ObjectPool() {
        ConsoleLogger.section("Ситуація 1. Видача автомобілів парку (Object Pool)");

        CarPool pool = new CarPool(List.of(
                new CarPool.CarSpec("CE1234AA", "Nissan Leaf"),
                new CarPool.CarSpec("CE2345BB", "Renault Zoe"),
                new CarPool.CarSpec("CE3456CC", "Hyundai Kona"),
                new CarPool.CarSpec("CE4567DD", "Skoda Octavia")));

        Client tkachuk = client("Ткачук", "tkachuk@example.com", "вул. Головна, 12", 5);
        Client melnyk = client("Мельник", "melnyk@example.com", "вул. Руська, 3", 4);
        Client kovalenko = client("Коваленко", "kovalenko@example.com", "вул. Університетська, 7", 2);
        Client bondar = client("Бондар", "bondar@example.com", "просп. Незалежності, 20", 6);
        Client shevchuk = client("Шевчук", "shevchuk@example.com", "вул. Шевченка, 1", 1);
        Client lysenko = client("Лисенко", "lysenko@example.com", "вул. Лесі Українки, 9", 7);

        ConsoleLogger.log("Main", "6 клієнтів одночасно бронюють авто, у парку 4 авто");
        Car tkachukCar = null;
        for (Client client : List.of(tkachuk, melnyk, kovalenko, bondar, shevchuk, lysenko)) {
            Optional<Car> car = pool.rent(client);
            if (car.isPresent()) {
                car.get().signIn(client.getSettings());
                ConsoleLogger.log("Car " + car.get(), "Клієнт " + client + " увійшов у свої налаштування: "
                        + car.get().describePersonalData());
                if (client == tkachuk) {
                    tkachukCar = car.get();
                }
            }
        }

        ConsoleLogger.log("Main", "Ткачук завершує поїздку");
        Optional<Client> next = pool.release(tkachukCar);
        if (next.isPresent()) {
            Client client = next.get();
            // авто щойно видане наступному клієнту — перевіряємо, що даних Ткачука в ньому немає
            ConsoleLogger.log("Car " + tkachukCar, "Стан одразу після видачі клієнту " + client + ": "
                    + tkachukCar.describePersonalData());
            tkachukCar.signIn(client.getSettings());
            ConsoleLogger.log("Car " + tkachukCar, "Клієнт " + client + " увійшов у свої налаштування: "
                    + tkachukCar.describePersonalData());
        }

        pool.printStatistics();
    }

    private static Client client(String name, String account, String address, int seat) {
        return new Client(name, new PersonalSettings(account, List.of(address), seat));
    }

    // ------------------------------------------------------------------
    // Ситуація 2. Корпоративні тарифні плани — Prototype
    // ------------------------------------------------------------------
    static void situation2Prototype() {
        ConsoleLogger.section("Ситуація 2. Корпоративні тарифні плани (Prototype)");

        TariffPlan standard = TariffConfigurator.configureStandardTariff();

        List<TariffPlan> corporate = new ArrayList<>();
        for (String company : List.of("Компанія А", "Компанія Б", "Компанія В")) {
            TariffPlan tariff = standard.copy();
            tariff.setName("Корпоративний: " + company);
            corporate.add(tariff);
            ConsoleLogger.log("Main", "Отримано тариф «" + tariff.getName()
                    + "» копіюванням стандартного — без повторного налаштування");
        }

        List<String> standardBefore = standard.describe();
        TariffPlan companyA = corporate.get(0);
        TariffPlan companyB = corporate.get(1);

        ConsoleLogger.log("Main", "Змінюємо лише тариф «" + companyA.getName()
                + "»: повне страхування, нова зона завершення, нова ціна за хвилину");
        companyA.getInsurance().setType(InsuranceType.FULL);
        companyA.addEndZone(new EndZone("Аеропорт"));
        companyA.setPricePerMinute(4.50);

        printSideBySide(standard, companyA);

        ConsoleLogger.log("Main", "Стандартний тариф не змінився: " + standard.describe().equals(standardBefore));
        ConsoleLogger.log("Main", "Тариф «" + companyB.getName() + "» не зазнав змін (крім назви): "
                + companyB.describe().subList(1, 7).equals(standardBefore.subList(1, 7)));
        ConsoleLogger.log("Main", "Спільні вкладені об'єкти стандартного тарифу і тарифу «" + companyA.getName()
                + "»: страхування — " + yesNo(standard.getInsurance() == companyA.getInsurance())
                + ", зона «Центр» — " + yesNo(standard.getEndZones().get(0) == companyA.getEndZones().get(0))
                + ", бонус — " + yesNo(standard.getBonuses().get(0) == companyA.getBonuses().get(0)));
    }

    private static String yesNo(boolean value) {
        return value ? "так" : "ні";
    }

    private static void printSideBySide(TariffPlan... plans) {
        System.out.println();
        List<List<String>> columns = new ArrayList<>();
        for (TariffPlan plan : plans) {
            columns.add(plan.describe());
        }
        int rows = columns.get(0).size();
        for (int row = 0; row < rows; row++) {
            StringBuilder line = new StringBuilder();
            for (List<String> column : columns) {
                line.append(String.format("%-54s", column.get(row)));
            }
            System.out.println(line.toString().stripTrailing());
        }
        System.out.println();
    }
}
