package lab10.task1_abstractfactory;

/** Тип авто за видом енергії. Кожен елемент набору знає, до якого типу він належить. */
public enum VehicleType {
    PETROL("бензинове"),
    HYBRID("гібрид"),
    ELECTRIC("електромобіль"),
    GAS("на газу");

    private final String title;

    VehicleType(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
