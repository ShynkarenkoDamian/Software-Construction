package ua.miskavto.lab15.task1_strategy;

/** Strategy: спільний інтерфейс усіх тарифів. */
public interface Tariff {

    String name();

    /** Базова вартість поїздки за цим тарифом (без коефіцієнта години пік). */
    double calculate(Trip trip);
}
