package logistics.domain;

/** Freight: коефіцієнт 1.2 (як у legacy-коді). */
public final class FreightTariff implements DeliveryTariff {

    private static final double MULTIPLIER = 1.2;

    @Override
    public String name() {
        return "Freight";
    }

    @Override
    public double applyTo(double baseFee) {
        return baseFee * MULTIPLIER;
    }
}
