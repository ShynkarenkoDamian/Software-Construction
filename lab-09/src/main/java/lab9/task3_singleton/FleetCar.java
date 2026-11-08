package lab9.task3_singleton;

/**
 * Запис про автомобіль на карті парку: розташування і стан.
 * <p>
 * Конструктор і зміна стану пакетні: змінювати стан авто можна лише через {@link FleetMap}.
 */
public class FleetCar {

    private final String plateNumber;
    private final String location;
    private CarStatus status = CarStatus.AVAILABLE;
    private String holder;

    FleetCar(String plateNumber, String location) {
        this.plateNumber = plateNumber;
        this.location = location;
    }

    /** Змінює стан авто (викликається лише картою парку). */
    void occupy(CarStatus newStatus, String holder) {
        this.status = newStatus;
        this.holder = holder;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public String getLocation() {
        return location;
    }

    public CarStatus getStatus() {
        return status;
    }

    /** Для кого авто зарезервоване або кому видане; {@code null}, якщо авто вільне. */
    public String getHolder() {
        return holder;
    }

    @Override
    public String toString() {
        return plateNumber;
    }
}
