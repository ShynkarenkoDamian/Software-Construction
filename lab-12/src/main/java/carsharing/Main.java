package carsharing;

import carsharing.common.Log;
import carsharing.task1_flyweight.CarMarker;
import carsharing.task1_flyweight.CarModel;
import carsharing.task1_flyweight.CarModelFactory;
import carsharing.task1_flyweight.RouteMap;
import carsharing.task2_composite.ParkingLot;
import carsharing.task2_composite.Zone;
import carsharing.task2_composite.ZoneAnalytics;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

/** Клас-раннер: окремий метод для кожної ситуації варіанта 8 «Каршерінг «Міськавто»». */
public class Main {

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        situation1Flyweight();
        situation2Composite();
        situation3Bridge();
    }

    private static void header(String title) {
        System.out.println();
        System.out.println("=== " + title + " ===");
    }

    /** Завдання 1: авто на мапі міста (Flyweight). */
    private static void situation1Flyweight() {
        header("Завдання 1. Авто на мапі міста (Flyweight)");

        final int markerCount = 100_000;
        final int carCount = 40;
        String[][] modelData = {
                {"Renault Zoe", "zoe.png", "електро, 5 місць", "zoe.jpg"},
                {"Nissan Leaf", "leaf.png", "електро, 5 місць", "leaf.jpg"},
                {"Skoda Octavia", "octavia.png", "бензин, 5 місць", "octavia.jpg"},
                {"Hyundai Kona", "kona.png", "гібрид, 5 місць", "kona.jpg"}
        };

        CarModelFactory factory = new CarModelFactory();
        RouteMap map = new RouteMap();
        Random random = new Random(1);

        Log.log("Runner", "1) формування " + markerCount + " точок маршрутів для " + carCount
                + " авто " + modelData.length + " моделей");
        for (int i = 0; i < markerCount; i++) {
            int car = i % carCount;
            String[] d = modelData[car % modelData.length];
            CarModel model = factory.getModel(d[0], d[1], d[2], d[3]);
            String plate = String.format("CE%04dAA", 1000 + car);
            double lat = 48.27 + random.nextDouble() * 0.04;
            double lon = 25.91 + random.nextDouble() * 0.05;
            int minuteOfDay = i % 1440;
            String time = String.format("%02d:%02d", minuteOfDay / 60, minuteOfDay % 60);
            map.addMarker(new CarMarker(plate, lat, lon, time, model));
        }

        Log.log("Runner", "2) кількість позначок: " + map.getMarkerCount());
        Log.log("Runner", "   кількість об'єктів моделей: " + factory.getModelCount());

        Log.log("Runner", "3) дві позначки різних авто однієї моделі");
        CarMarker first = map.getMarker(0);
        CarMarker second = map.getMarker(4);
        Log.log("Runner", first.describe());
        Log.log("Runner", "   об'єкт моделі: @" + Integer.toHexString(System.identityHashCode(first.getModel())));
        Log.log("Runner", second.describe());
        Log.log("Runner", "   об'єкт моделі: @" + Integer.toHexString(System.identityHashCode(second.getModel())));
        Log.log("Runner", "   посилаються на той самий об'єкт моделі: " + (first.getModel() == second.getModel()));
    }

    /** Завдання 2: зони міста і тарифікація (Composite). */
    private static void situation2Composite() {
        header("Завдання 2. Зони міста і тарифікація (Composite)");

        Log.log("Runner", "1) збирання структури: місто → райони → квартали → (бізнес-центр) → майданчики");
        Zone city = new Zone("Чернівці");

        Zone districtA = new Zone("Район Шевченківський");
        Zone quarterCenter = new Zone("Квартал Центр");
        quarterCenter.add(new ParkingLot("Майданчик Центральна площа", 12, 4800));
        Zone businessCenter = new Zone("Бізнес-центр Буковина");
        businessCenter.add(new ParkingLot("Майданчик БЦ-1", 8, 3500));
        businessCenter.add(new ParkingLot("Майданчик БЦ-2", 5, 2100));
        quarterCenter.add(businessCenter);
        Zone quarterSadhora = new Zone("Квартал Садгора");
        quarterSadhora.add(new ParkingLot("Майданчик Садгора-1", 6, 1900));
        districtA.add(quarterCenter);
        districtA.add(quarterSadhora);

        Zone districtB = new Zone("Район Першотравневий");
        Zone quarterRosha = new Zone("Квартал Роша");
        quarterRosha.add(new ParkingLot("Майданчик Роша-1", 10, 3900));
        quarterRosha.add(new ParkingLot("Майданчик Роша-2", 4, 1200));
        districtB.add(quarterRosha);

        city.add(districtA);
        city.add(districtB);

        Log.log("Runner", "2) структура деревом з кількістю авто і виручкою кожного вузла");
        city.print(0);

        Log.log("Runner", "3) показники для міста і одного району тим самим викликом");
        ZoneAnalytics analytics = new ZoneAnalytics();
        analytics.printSummary(city);
        analytics.printSummary(districtA);
    }

    /** Завдання 3: звіти для різних отримувачів (Bridge). */
    private static void situation3Bridge() {
        header("Завдання 3. Звіти для різних отримувачів (Bridge)");
        // TODO (колега): змоделювати ситуацію, див. carsharing.task3_bridge.package-info
        Log.log("Runner", "не реалізовано: точка розширення для Завдання 3");
    }
}
