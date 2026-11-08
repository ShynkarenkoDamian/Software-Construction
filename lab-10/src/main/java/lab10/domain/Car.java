package lab10.domain;

/** Автомобіль парку (мінімальна модель: потрібен лише номер). */
public class Car {

    private final String plateNumber;

    public Car(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    @Override
    public String toString() {
        return plateNumber;
    }
}
