package ua.miskavto.lab15.task2_template_method;

/**
 * Авто парку. Для огляду достатньо номера, рівня енергії (паливо / заряд / газ, у %) та статусу.
 */
public class Car {

    private final String plate;
    private final int energyPercent;
    private CarStatus status = CarStatus.RETURNED_FROM_TRIP;

    public Car(String plate, int energyPercent) {
        this.plate = plate;
        this.energyPercent = energyPercent;
    }

    public String getPlate() {
        return plate;
    }

    public int getEnergyPercent() {
        return energyPercent;
    }

    public CarStatus getStatus() {
        return status;
    }

    public void setStatus(CarStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return plate + " (енергія " + energyPercent + "%, статус: " + status.getTitle() + ")";
    }
}
