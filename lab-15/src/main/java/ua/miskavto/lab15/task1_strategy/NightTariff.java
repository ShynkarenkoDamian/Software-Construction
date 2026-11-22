package ua.miskavto.lab15.task1_strategy;

/**
 * ConcreteStrategy: нічний тариф.
 * Додано ОДНИМ класом: Trip, Client та інші тарифи не змінювалися.
 */
public class NightTariff implements Tariff {

    private final double ratePerMinute;
    private final double ratePerKm;

    public NightTariff(double ratePerMinute, double ratePerKm) {
        this.ratePerMinute = ratePerMinute;
        this.ratePerKm = ratePerKm;
    }

    @Override
    public String name() {
        return "Нічний";
    }

    @Override
    public double calculate(Trip trip) {
        return trip.getMinutes() * ratePerMinute + trip.getKm() * ratePerKm;
    }
}
