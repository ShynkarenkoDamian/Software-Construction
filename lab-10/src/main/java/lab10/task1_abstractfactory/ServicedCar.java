package lab10.task1_abstractfactory;

/** Авто, введене в роботу з узгодженим набором елементів. */
public class ServicedCar {

    private final String plateNumber;
    private final TelematicsModule telematics;
    private final EnergyCard energyCard;
    private final LevelSensor levelSensor;

    public ServicedCar(String plateNumber, TelematicsModule telematics,
                       EnergyCard energyCard, LevelSensor levelSensor) {
        this.plateNumber = plateNumber;
        this.telematics = telematics;
        this.energyCard = energyCard;
        this.levelSensor = levelSensor;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public TelematicsModule getTelematics() {
        return telematics;
    }

    public EnergyCard getEnergyCard() {
        return energyCard;
    }

    public LevelSensor getLevelSensor() {
        return levelSensor;
    }

    @Override
    public String toString() {
        return "Авто " + plateNumber + " = [" + telematics.describe() + "] + ["
                + energyCard.describe() + "] + [" + levelSensor.describe() + "]";
    }
}
