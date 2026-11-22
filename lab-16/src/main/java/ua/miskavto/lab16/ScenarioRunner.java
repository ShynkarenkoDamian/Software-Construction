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

/**
 * Раннер: окремий метод для кожної ситуації.
 * Завдання 3 (Iterator) виконує колега: його код у пакеті task3_iterator, а метод situation3Iterator()
 * додається сюди (і викликається з Main).
 */
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
}
