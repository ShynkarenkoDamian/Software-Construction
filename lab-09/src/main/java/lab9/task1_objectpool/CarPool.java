package lab9.task1_objectpool;

import lab9.common.ConsoleLogger;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

/**
 * Пул автомобілів парку (шаблон Object Pool).
 * <p>
 * Правила:
 * <ul>
 *   <li>авто вводяться в роботу ліниво, але не більше, ніж є у парку;</li>
 *   <li>повернене авто скидається і видається наступному клієнту без повторного введення в роботу;</li>
 *   <li>якщо вільного авто немає, клієнт стає в чергу (FIFO) і отримує перше авто, що звільниться.</li>
 * </ul>
 * Методи синхронізовані, щоб пул лишався коректним і за одночасних запитів.
 */
public class CarPool {

    private static final String SOURCE = "CarPool";

    /** Опис фізичного авто парку (ще не введеного в роботу). */
    public record CarSpec(String plateNumber, String model) {
    }

    private final Deque<CarSpec> notCommissioned;
    private final Deque<Car> freeCars = new ArrayDeque<>();
    private final List<Car> allCars = new ArrayList<>();
    private final Queue<Client> waitingQueue = new ArrayDeque<>();

    public CarPool(List<CarSpec> fleet) {
        this.notCommissioned = new ArrayDeque<>(fleet);
    }

    /**
     * Видає авто клієнту. Порожній результат означає, що клієнта поставлено в чергу.
     */
    public synchronized Optional<Car> rent(Client client) {
        Car car = null;
        if (!freeCars.isEmpty()) {
            car = freeCars.poll();
        } else if (!notCommissioned.isEmpty()) {
            car = commission(notCommissioned.poll());
        }

        if (car == null) {
            waitingQueue.add(client);
            ConsoleLogger.log(SOURCE, "Вільних авто немає: клієнт " + client
                    + " ставиться в чергу (позиція " + waitingQueue.size() + ")");
            return Optional.empty();
        }

        car.handOutTo(client);
        ConsoleLogger.log(SOURCE, "Авто " + car + " → клієнт " + client);
        return Optional.of(car);
    }

    /**
     * Приймає авто після поїздки, стирає персональні дані і, якщо є черга,
     * одразу видає авто першому клієнту, що очікує.
     *
     * @return клієнт із черги, який отримав це авто, або порожній результат
     */
    public synchronized Optional<Client> release(Car car) {
        if (!allCars.contains(car) || car.getState() != CarState.RENTED) {
            throw new IllegalStateException("Авто " + car + " не можна повернути: воно не в оренді");
        }

        car.resetForNextRental();
        ConsoleLogger.log(SOURCE, car + " повернено, персональні дані стерто");

        Client next = waitingQueue.poll();
        if (next == null) {
            freeCars.add(car);
            return Optional.empty();
        }

        car.handOutTo(next);
        ConsoleLogger.log(SOURCE, "Авто " + car + " → клієнт " + next + " (з черги очікування)");
        return Optional.of(next);
    }

    /** «Дорога» операція: введення нового авто в роботу. Виконується не більше разу на авто. */
    private Car commission(CarSpec spec) {
        ConsoleLogger.expensiveOperation(SOURCE,
                "Введення авто " + spec.plateNumber() + " (" + spec.model()
                        + ") в роботу: реєстрація в системі, підключення телематики, страхування...",
                "Авто " + spec.plateNumber() + " введено в роботу",
                5);
        Car car = new Car(spec.plateNumber(), spec.model());
        allCars.add(car);
        return car;
    }

    public synchronized int getCommissionedCount() {
        return allCars.size();
    }

    public synchronized int getWaitingCount() {
        return waitingQueue.size();
    }

    public synchronized void printStatistics() {
        ConsoleLogger.log(SOURCE, "Статистика парку:");
        for (Car car : allCars) {
            ConsoleLogger.log(SOURCE, "  " + car + " (" + car.getModel() + "): поїздок — " + car.getTripsCount());
        }
        ConsoleLogger.log(SOURCE, "  Загальна кількість введених у роботу авто: " + allCars.size());
        ConsoleLogger.log(SOURCE, "  Клієнтів у черзі очікування: " + waitingQueue.size());
    }
}
