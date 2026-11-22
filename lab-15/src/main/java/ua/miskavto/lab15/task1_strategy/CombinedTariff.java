package ua.miskavto.lab15.task1_strategy;

/** ConcreteStrategy: комбінований тариф (хвилини + кілометри). */
public class CombinedTariff implements Tariff {

    private final double ratePerMinute;
    private final double ratePerKm;

    public CombinedTariff(double ratePerMinute, double ratePerKm) {
        this.ratePerMinute = ratePerMinute;
        this.ratePerKm = ratePerKm;
    }

    @Override
    public String name() {
        return "Комбінований";
    }

    @Override
    public double calculate(Trip trip) {
        return trip.getMinutes() * ratePerMinute + trip.getKm() * ratePerKm;
    }
}
