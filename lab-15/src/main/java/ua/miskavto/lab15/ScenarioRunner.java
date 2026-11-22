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

/**
 * Раннер: окремий метод для кожної ситуації.
 * Завдання 3 (State) виконує колега: його код у пакеті task3_state, а метод situation3State()
 * додається сюди (і викликається з Main).
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
}
