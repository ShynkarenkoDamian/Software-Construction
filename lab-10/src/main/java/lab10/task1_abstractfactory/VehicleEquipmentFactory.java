package lab10.task1_abstractfactory;

/**
 * Абстрактна фабрика: створює сімейство взаємопов'язаних елементів для авто одного типу.
 * Інтерфейс не дозволяє отримати елементи з різних сімейств: кожна фабрика видає
 * лише елементи свого типу.
 */
public interface VehicleEquipmentFactory {

    /** Тип авто, набір для якого створює ця фабрика. */
    VehicleType getVehicleType();

    TelematicsModule createTelematicsModule();

    EnergyCard createEnergyCard();

    LevelSensor createLevelSensor();
}
