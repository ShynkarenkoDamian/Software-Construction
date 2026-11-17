package carsharing.command;

import carsharing.model.Car;
import carsharing.util.Logger;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Invoker: приймає команди від підтримки/клієнта.
 * Авто на зв'язку — виконує одразу, поза зв'язком — ставить у чергу цього авто.
 * Зберігає історію (для скасування) і журнал виконаних команд.
 */
public class RemoteControl {
    private final Map<Car, Deque<Command>> pending = new HashMap<>();
    private final Deque<Command> history = new ArrayDeque<>();
    private final List<String> journal = new ArrayList<>();

    public void send(String sender, Command command) {
        Car car = command.getCar();
        Logger.log("RemoteControl", sender + " надіслав команду: " + command.describe());
        if (car.isOnline()) {
            run(command);
        } else {
            pending.computeIfAbsent(car, c -> new ArrayDeque<>()).add(command);
            Logger.log("RemoteControl", "авто поза зв'язком, команду поставлено в чергу");
        }
    }

    /** Авто з'явилося на зв'язку: виконуємо накопичені команди по черзі. */
    public void carOnline(Car car) {
        car.setOnline(true);
        Deque<Command> queue = pending.getOrDefault(car, new ArrayDeque<>());
        while (!queue.isEmpty()) {
            run(queue.poll());
        }
    }

    /** Скасувати останню виконану команду. */
    public void undoLast() {
        if (history.isEmpty()) {
            Logger.log("RemoteControl", "немає команд для скасування");
            return;
        }
        Command command = history.pop();
        Logger.log("RemoteControl", "скасування команди: " + command.describe());
        command.undo();
        journal.add("[" + Logger.time() + "] СКАСОВАНО: " + command.describe());
    }

    public void printJournal() {
        Logger.log("RemoteControl", "журнал команд (" + journal.size() + " записів):");
        for (String entry : journal) {
            System.out.println("    " + entry);
        }
    }

    private void run(Command command) {
        command.execute();
        history.push(command);
        journal.add("[" + Logger.time() + "] ВИКОНАНО: " + command.describe());
    }
}
