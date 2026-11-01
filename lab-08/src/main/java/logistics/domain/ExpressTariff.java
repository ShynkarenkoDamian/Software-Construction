package logistics.domain;

/** Express: коефіцієнт 1.8 (як у legacy-коді). */
public final class ExpressTariff implements DeliveryTariff {

    private static final double MULTIPLIER = 1.8;

    @Override
    public String name() {
        return "Express";
    }

    @Override
    public double applyTo(double baseFee) {
        return baseFee * MULTIPLIER;
    }
}
