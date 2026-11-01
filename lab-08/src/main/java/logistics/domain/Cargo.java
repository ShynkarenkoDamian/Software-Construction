package logistics.domain;

/**
 * Вантаж.
 * Information Expert: саме Cargo знає вагу, вартість і тип доставки,
 * тому саме він рахує вартість перевезення та страховки.
 */
public final class Cargo {

    private final double weightKg;
    private final double volumeCubMetres;
    private final double declaredValue;
    private final DeliveryTariff tariff;

    public Cargo(double weightKg, double volumeCubMetres, double declaredValue, DeliveryTariff tariff) {
        if (weightKg <= 0) {
            throw new IllegalArgumentException("Вага має бути більшою за 0");
        }
        if (volumeCubMetres <= 0) {
            throw new IllegalArgumentException("Об'єм має бути більшим за 0");
        }
        if (declaredValue < 0) {
            throw new IllegalArgumentException("Заявлена вартість не може бути від'ємною");
        }
        if (tariff == null) {
            throw new IllegalArgumentException("Тип доставки обов'язковий");
        }
        this.weightKg = weightKg;
        this.volumeCubMetres = volumeCubMetres;
        this.declaredValue = declaredValue;
        this.tariff = tariff;
    }

    /** Вартість перевезення: вага * ставка за кг, потім коефіцієнт типу доставки. */
    public double transportFee(PricingPolicy pricing) {
        return tariff.applyTo(weightKg * pricing.ratePerKg());
    }

    /** Вартість страховки: заявлена вартість * страховий відсоток. */
    public double insuranceFee(PricingPolicy pricing) {
        return declaredValue * pricing.insuranceRate();
    }

    /** Загальна вартість = перевезення + страховка. */
    public double totalCost(PricingPolicy pricing) {
        return transportFee(pricing) + insuranceFee(pricing);
    }

    public double weightKg() {
        return weightKg;
    }

    public double volumeCubMetres() {
        return volumeCubMetres;
    }

    public double declaredValue() {
        return declaredValue;
    }

    public String deliveryType() {
        return tariff.name();
    }
}
