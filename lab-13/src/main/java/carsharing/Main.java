package carsharing;

import carsharing.command.BlockEngineCommand;
import carsharing.command.CloseDoorsCommand;
import carsharing.command.HazardLightsCommand;
import carsharing.command.OpenDoorsCommand;
import carsharing.command.RemoteControl;
import carsharing.model.Car;
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

    /** Завдання 2 (Chain of Responsibility, «Допуск клієнта до поїздки») — виконує колега. */
    static void scenarioTripAccess() {
        // TODO (колега): побудувати ланцюг перевірок у пакеті carsharing.access
        //  і відтворити демонстраційний сценарій із завдання.
        Logger.log("Runner", "=== Ситуація 2: допуск клієнта до поїздки — буде виконано окремо ===");
    }
}
