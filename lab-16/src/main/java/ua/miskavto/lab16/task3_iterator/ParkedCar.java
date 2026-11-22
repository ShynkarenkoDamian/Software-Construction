package ua.miskavto.lab16.task3_iterator;

/** Елемент колекції: авто на майданчику. Незмінний, містить лише дані для обходів. */
public class ParkedCar {

    private final String plate;
    private final int chargePercent;
    private final int idleHours;

    public ParkedCar(String plate, int chargePercent, int idleHours) {
        this.plate = plate;
        this.chargePercent = chargePercent;
        this.idleHours = idleHours;
    }

    public String getPlate() {
        return plate;
    }

    /** Заряд батареї, %. */
    public int getChargePercent() {
        return chargePercent;
    }

    /** Скільки годин авто простоює без поїздок. */
    public int getIdleHours() {
        return idleHours;
    }

    @Override
    public String toString() {
        return plate + " (заряд " + chargePercent + "%, простій " + idleHours + " год)";
    }
}
