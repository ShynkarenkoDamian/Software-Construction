package ua.miskavto.lab15.task1_strategy;

/** ConcreteStrategy: фіксований тариф для корпоративних клієнтів. */
public class CorporateFixedTariff implements Tariff {

    private final double fixedPrice;

    public CorporateFixedTariff(double fixedPrice) {
        this.fixedPrice = fixedPrice;
    }

    @Override
    public String name() {
        return "Корпоративний (фіксований)";
    }

    @Override
    public double calculate(Trip trip) {
        return fixedPrice;
    }
}
