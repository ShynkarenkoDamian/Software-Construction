package ua.miskavto.lab15;

import ua.miskavto.lab15.task2_template_method.Car;
import ua.miskavto.lab15.task1_strategy.Client;
import ua.miskavto.lab15.task1_strategy.CombinedTariff;
import ua.miskavto.lab15.task1_strategy.CorporateFixedTariff;
import ua.miskavto.lab15.task1_strategy.NightTariff;
import ua.miskavto.lab15.task1_strategy.PerKmTariff;
import ua.miskavto.lab15.task1_strategy.PerMinuteTariff;
import ua.miskavto.lab15.task1_strategy.Trip;
import ua.miskavto.lab15.task2_template_method.CarInspection;
import ua.miskavto.lab15.task2_template_method.ElectricCarInspection;
import ua.miskavto.lab15.task2_template_method.GasCarInspection;
import ua.miskavto.lab15.task2_template_method.PetrolCarInspection;
import ua.miskavto.lab15.common.Log;
import ua.miskavto.lab15.task3_state.SharedCar;

/**
 * Раннер: окремий метод для кожної ситуації.
 */
public class ScenarioRunner {

    /** Завдання 1 (Strategy): розрахунок вартості поїздки. */
    public void situation1Tariffs() {
        Log.section("Ситуація 1. Розрахунок вартості поїздки (Strategy)");

        Client client = new Client("Іван Коваль", new PerMinuteTariff(4.00));

        Log.log("Runner", "Сценарій 1: поїздка 40 хв / 18 км за трьома тарифами");
        new Trip(client, 40, 18).calculateCost();
        client.setTariff(new PerKmTariff(9.00));
        new Trip(client, 40, 18).calculateCost();
        client.setTariff(new CombinedTariff(2.00, 5.00));
        new Trip(client, 40, 18).calculateCost();

        Log.log("Runner", "Додатково: той самий поминутний тариф у години пік (коефіцієнт 1.5)");
        client.setTariff(new PerMinuteTariff(4.00));
        new Trip(client, 40, 18, 1.5).calculateCost();

        Log.log("Runner", "Сценарій 2: клієнт переходить на корпоративний тариф, розрахунок повторюється");
        client.setTariff(new CorporateFixedTariff(150.00));
        new Trip(client, 40, 18).calculateCost();

        Log.log("Runner", "Сценарій 3: додано нічний тариф (один новий клас, без змін у Trip і Client)");
        client.setTariff(new NightTariff(2.50, 3.00));
        new Trip(client, 40, 18).calculateCost();
    }

    /** Завдання 2 (Template Method): огляд авто перед поверненням у парк. */
    public void situation2Inspection() {
        Log.section("Ситуація 2. Огляд авто перед поверненням у парк (Template Method)");

        Log.log("Runner", "Сценарій 1: огляд бензинового авто");
        CarInspection petrol = new PetrolCarInspection();
        petrol.inspect(new Car("CE1234AA", 60));

        Log.log("Runner", "Сценарій 2: огляд електромобіля тією самою процедурою, заряд 15%");
        CarInspection electric = new ElectricCarInspection();
        electric.inspect(new Car("CE5678BB", 15));

        Log.log("Runner", "Сценарій 3: додано тип «авто на газу» (один новий клас)");
        CarInspection gas = new GasCarInspection();
        gas.inspect(new Car("CE9012CC", 70));
    }

    /** Завдання 3 (State): стан авто. */
    public void situation3State() {
        Log.section("Ситуація 3. Стан авто (State)");

        Log.log("Runner", "Сценарій 1: доступне → заброньоване → в поїздці → на паузі → в поїздці → доступне");
        SharedCar car = new SharedCar("CE3141AA");
        car.reserve("Іван Коваль");
        car.unlock("Петро Мельник");
        car.unlock("Іван Коваль");
        car.pause();
        car.endTrip();
        car.unlock("Іван Коваль");
        car.endTrip();

        Log.log("Runner", "Сценарій 2: бронювання не підтверджене за 20 хвилин");
        car.reserve("Олена Бондар");
        car.tick(10);
        car.tick(10);

        Log.log("Runner", "Сценарій 3: авто на обслуговуванні, спроба забронювати");
        car.sendToMaintenance();
        car.reserve("Марія Шевчук");
        car.returnFromMaintenance();
    }
}
