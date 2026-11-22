package ua.miskavto.lab16.task1_visitor;

/** Element: авто парку. Містить лише дані та accept(); бізнес-операцій тут немає. */
public abstract class Vehicle {

    private final String plate;
    private final double value;
    private final int mileageKm;
    private final int monthlyKm;

    protected Vehicle(String plate, double value, int mileageKm, int monthlyKm) {
        this.plate = plate;
        this.value = value;
        this.mileageKm = mileageKm;
        this.monthlyKm = monthlyKm;
    }

    public String getPlate() {
        return plate;
    }

    /** Вартість авто, грн. */
    public double getValue() {
        return value;
    }

    /** Загальний пробіг, км. */
    public int getMileageKm() {
        return mileageKm;
    }

    /** Пробіг за місяць звітного періоду, км. */
    public int getMonthlyKm() {
        return monthlyKm;
    }

    public abstract String typeName();

    public abstract void accept(FleetVisitor visitor);

    @Override
    public String toString() {
        return plate + " [" + typeName() + ", вартість " + String.format(java.util.Locale.ROOT, "%.2f", value)
                + " грн, пробіг " + mileageKm + " км, за місяць " + monthlyKm + " км]";
    }
}
