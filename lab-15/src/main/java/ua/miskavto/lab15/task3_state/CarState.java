package ua.miskavto.lab15.task3_state;

/**
 * State: інтерфейс стану авто. Містить усі операції, які можна виконати з авто.
 * Реалізації за замовчуванням — зрозуміла відмова («операція в цьому стані недопустима»);
 * кожен конкретний стан перевизначає лише ті операції, які в ньому дозволені.
 */
public interface CarState {

    String title();

    default void reserve(SharedCar car, String client) {
        car.refuse("бронювання", "забронювати можна лише доступне авто");
    }

    default void unlock(SharedCar car, String client) {
        car.refuse("відкриття", "відкрити можна лише заброньоване авто (своїм клієнтом) або авто на паузі");
    }

    default void pause(SharedCar car) {
        car.refuse("пауза", "поставити на паузу можна лише авто в поїздці");
    }

    default void endTrip(SharedCar car) {
        car.refuse("завершення поїздки", "завершити поїздку можна лише в стані «в поїздці»");
    }

    /** Плин часу. У більшості станів на нього нічого не реагує, тому за замовчуванням — нічого не робить. */
    default void tick(SharedCar car, int minutes) {
    }

    default void sendToMaintenance(SharedCar car) {
        car.refuse("відправлення на обслуговування", "відправити на обслуговування можна лише доступне авто");
    }

    default void returnFromMaintenance(SharedCar car) {
        car.refuse("повернення з обслуговування", "авто не перебуває на обслуговуванні");
    }
}
