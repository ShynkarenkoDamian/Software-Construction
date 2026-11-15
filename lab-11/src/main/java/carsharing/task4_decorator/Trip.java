package carsharing.task4_decorator;

/**
 * Поїздка (Component): спільний інтерфейс для базової поїздки й для поїздки з опціями.
 * Опції змінюють опис і вартість хвилини.
 */
public interface Trip {

    String getDescription();

    /** Вартість однієї хвилини, грн. */
    double getRatePerMinute();

    int getMinutes();

    default double getCost() {
        return getRatePerMinute() * getMinutes();
    }
}
