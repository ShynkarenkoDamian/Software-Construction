package logistics.domain;

/** Standard: без націнки (у legacy-коді коефіцієнта для Standard не було). */
public final class StandardTariff implements DeliveryTariff {

    @Override
    public String name() {
        return "Standard";
    }

    @Override
    public double applyTo(double baseFee) {
        return baseFee;
    }
}
