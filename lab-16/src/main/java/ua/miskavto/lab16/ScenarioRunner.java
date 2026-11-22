package ua.miskavto.lab16;

import ua.miskavto.lab16.task2_memento.Car;
import ua.miskavto.lab16.task2_memento.ClientProfile;
import ua.miskavto.lab16.common.Log;
import ua.miskavto.lab16.task1_visitor.Co2EmissionsVisitor;
import ua.miskavto.lab16.task1_visitor.ElectricCar;
import ua.miskavto.lab16.task1_visitor.Fleet;
import ua.miskavto.lab16.task1_visitor.HybridCar;
import ua.miskavto.lab16.task1_visitor.InsuranceVisitor;
import ua.miskavto.lab16.task1_visitor.MaintenanceVisitor;
import ua.miskavto.lab16.task1_visitor.Minibus;
import ua.miskavto.lab16.task1_visitor.PetrolCar;
import ua.miskavto.lab16.task3_iterator.ParkedCar;
import ua.miskavto.lab16.task3_iterator.ParkingLot;
import ua.miskavto.lab16.task3_iterator.Zone;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** Раннер: окремий метод для кожної ситуації. */
public class ScenarioRunner {

    /** Завдання 1 (Visitor): операції над парком. */
    public void situation1FleetOperations() {
        Log.section("Ситуація 1. Операції над парком (Visitor)");

        Fleet fleet = new Fleet();
        fleet.add(new PetrolCar("CE1001AA", 600_000, 24_500, 2_100));
        fleet.add(new HybridCar("CE1002AA", 750_000, 31_200, 1_800));
        fleet.add(new ElectricCar("CE1003AA", 1_100_000, 18_900, 2_400));
        fleet.add(new Minibus("CE1004AA", 1_400_000, 47_800, 3_000, 9));
        fleet.add(new ElectricCar("CE1005AA", 1_050_000, 39_900, 2_200));

        Log.log("Runner", "Сценарій 1: розрахунок вартості страхування для всього парку");
        fleet.logState("до операції");
        fleet.accept(new InsuranceVisitor());
        fleet.logState("після операції");

        Log.log("Runner", "Сценарій 2: планування ТО для того самого парку");
        fleet.logState("до операції");
        fleet.accept(new MaintenanceVisitor());
        fleet.logState("після операції");

        Log.log("Runner", "Сценарій 3: додано операцію «викиди CO2» (один новий клас, без змін у класах авто)");
        fleet.logState("до операції");
        fleet.accept(new Co2EmissionsVisitor());
        fleet.logState("після операції");
    }

    /** Завдання 2 (Memento): налаштування авто для клієнта. */
    public void situation2CarSettings() {
        Log.section("Ситуація 2. Налаштування авто для клієнта (Memento)");

        ClientProfile profile = new ClientProfile("Іван Коваль");
        Car car = new Car("CE1234AA", "Hyundai Ioniq");

        Log.log("Runner", "Сценарій 1: налаштувати авто CE1234AA і зберегти в профіль");
        car.setSeatPosition(5);
        car.setMirrorAngle(12);
        car.setClimateTemperature(21.5);
        car.setMultimedia(8, "Радіо Промінь");
        Log.log(car.getPlate(), "Налаштування: " + car.describe());
        profile.save("основні", car);

        Log.log("Runner", "Сценарій 2: зміна налаштувань під час поїздки, потім відновлення збережених");
        car.setSeatPosition(7);
        car.setClimateTemperature(18.0);
        car.setMultimedia(3, "Радіо Культура");
        Log.log(car.getPlate(), "Стан до відновлення: " + car.describe());
        profile.restore("основні", car);
        Log.log(car.getPlate(), "Стан після відновлення: " + car.describe());

        Log.log("Runner", "Сценарій 3: відновлення в іншому авто тієї ж моделі");
        Car sameModel = new Car("CE5678BB", "Hyundai Ioniq");
        Log.log(sameModel.getPlate(), "Стан до відновлення: " + sameModel.describe());
        profile.restore("основні", sameModel);
        Log.log(sameModel.getPlate(), "Стан після відновлення: " + sameModel.describe());

        Log.log("Runner", "Додатково: спроба відновити в авто іншої моделі");
        Car otherModel = new Car("CE9999CC", "Kia Rio");
        Log.log(otherModel.getPlate(), "Стан до відновлення: " + otherModel.describe());
        profile.restore("основні", otherModel);
        Log.log(otherModel.getPlate(), "Стан після спроби: " + otherModel.describe());
    }

    /** Завдання 3 (Iterator): обхід авто для обслуговування. */
    public void situation3Iterator() {
        Log.section("Ситуація 3. Обхід авто для обслуговування (Iterator)");

        Zone center = buildCenter();
        Zone city = buildCity(center);

        city.logState("до обходів");

        Log.log("Runner", "Сценарій 1: авто з низьким зарядом (< 20%) у порядку маршруту");
        Iterator<ParkedCar> lowCharge = city.lowChargeRouteIterator(20);
        int n = 0;
        while (lowCharge.hasNext()) {
            Log.log("Служба обслуговування", "зупинка " + (++n) + ": " + lowCharge.next());
        }

        Log.log("Runner", "Сценарій 2: усі авто зони «Центр», включно з вкладеними зонами");
        n = 0;
        for (ParkedCar car : center) {
            Log.log("Диспетчер", "авто " + (++n) + ": " + car);
        }

        Log.log("Runner", "Сценарій 3: авто, що простоюють понад добу, і одночасно інший обхід");
        Iterator<ParkedCar> idle = city.idleIterator(24);
        Iterator<ParkedCar> route = city.lowChargeRouteIterator(20);
        List<ParkedCar> idleSeen = new ArrayList<>();
        List<ParkedCar> routeSeen = new ArrayList<>();
        while (idle.hasNext() || route.hasNext()) {
            if (idle.hasNext()) {
                ParkedCar car = idle.next();
                idleSeen.add(car);
                Log.log("Аналітика", "простій: " + car + " [видано " + idleSeen.size() + "]");
            }
            if (route.hasNext()) {
                ParkedCar car = route.next();
                routeSeen.add(car);
                Log.log("Служба обслуговування", "низький заряд: " + car + " [видано " + routeSeen.size() + "]");
            }
        }
        Log.log("Runner", "Незалежність обходів: результат Аналітики збігається з окремим обходом — "
                + (idleSeen.equals(drain(city.idleIterator(24))))
                + "; результат Служби обслуговування збігається з окремим обходом — "
                + (routeSeen.equals(drain(city.lowChargeRouteIterator(20)))));

        city.logState("після обходів (колекція не змінилась)");
    }

    private static List<ParkedCar> drain(Iterator<ParkedCar> iterator) {
        List<ParkedCar> result = new ArrayList<>();
        iterator.forEachRemaining(result::add);
        return result;
    }

    /** Зона «Центр» із двома рівнями вкладених зон. */
    private static Zone buildCenter() {
        ParkingLot station = new ParkingLot("Вокзал", 1);
        station.addCar(new ParkedCar("CE2001AA", 15, 3));
        station.addCar(new ParkedCar("CE2002AA", 80, 30));
        ParkingLot square = new ParkingLot("Центральна площа", 3);
        square.addCar(new ParkedCar("CE2003AA", 45, 5));
        square.addCar(new ParkedCar("CE2004AA", 10, 26));
        ParkingLot townHall = new ParkingLot("Ратуша", 4);
        townHall.addCar(new ParkedCar("CE2005AA", 18, 2));
        townHall.addCar(new ParkedCar("CE2006AA", 90, 50));
        ParkingLot armenian = new ParkingLot("Вірменська вул.", 5);
        armenian.addCar(new ParkedCar("CE2007AA", 8, 1));
        armenian.addCar(new ParkedCar("CE2008AA", 60, 12));

        Zone pedestrian = new Zone("Пішохідна зона");
        pedestrian.addLot(armenian);
        Zone oldTown = new Zone("Старе місто");
        oldTown.addLot(townHall);
        oldTown.addSubZone(pedestrian);
        Zone center = new Zone("Центр");
        center.addLot(station);
        center.addLot(square);
        center.addSubZone(oldTown);
        return center;
    }

    /** Місто = «Центр» + зона «Садгора». */
    private static Zone buildCity(Zone center) {
        ParkingLot depot = new ParkingLot("ТЦ Депот", 2);
        depot.addCar(new ParkedCar("CE2009AA", 95, 40));
        depot.addCar(new ParkedCar("CE2010AA", 19, 25));
        ParkingLot sadgoraPark = new ParkingLot("Парк Садгора", 6);
        sadgoraPark.addCar(new ParkedCar("CE2011AA", 22, 6));
        sadgoraPark.addCar(new ParkedCar("CE2012AA", 5, 48));

        Zone sadgora = new Zone("Садгора");
        sadgora.addLot(depot);
        sadgora.addLot(sadgoraPark);

        Zone city = new Zone("Місто");
        city.addSubZone(center);
        city.addSubZone(sadgora);
        return city;
    }
}
