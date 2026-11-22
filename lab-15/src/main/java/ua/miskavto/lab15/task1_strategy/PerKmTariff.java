package ua.miskavto.lab15.task1_strategy;

/** ConcreteStrategy: кілометровий тариф. */
public class PerKmTariff implements Tariff {

    private final double ratePerKm;

    public PerKmTariff(double ratePerKm) {
        this.ratePerKm = ratePerKm;
    }

    @Override
    public String name() {
        return "Кілометровий";
    }

    @Override
    public double calculate(Trip trip) {
        return trip.getKm() * ratePerKm;
    }
}
