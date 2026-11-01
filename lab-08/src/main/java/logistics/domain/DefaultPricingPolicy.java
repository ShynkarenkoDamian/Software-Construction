package logistics.domain;

/** Ставки з legacy-коду: 15 грн/кг та 0.5% страховки. */
public final class DefaultPricingPolicy implements PricingPolicy {

    @Override
    public double ratePerKg() {
        return 15.0;
    }

    @Override
    public double insuranceRate() {
        return 0.005;
    }
}
