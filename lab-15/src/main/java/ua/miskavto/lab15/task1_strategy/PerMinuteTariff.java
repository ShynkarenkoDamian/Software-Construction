package ua.miskavto.lab15.task1_strategy;

/** ConcreteStrategy: поминутний тариф. */
public class PerMinuteTariff implements Tariff {

    private final double ratePerMinute;

    public PerMinuteTariff(double ratePerMinute) {
        this.ratePerMinute = ratePerMinute;
    }

    @Override
    public String name() {
        return "Поминутний";
    }

    @Override
    public double calculate(Trip trip) {
        return trip.getMinutes() * ratePerMinute;
    }
}
