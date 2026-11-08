package lab10.task1_abstractfactory;

import lab10.common.ConsoleLogger;

import java.util.EnumSet;
import java.util.Set;

/**
 * Служба обслуговування парку — клієнт абстрактної фабрики.
 * <p>
 * Працює лише з інтерфейсами {@link VehicleEquipmentFactory}, {@link TelematicsModule},
 * {@link EnergyCard} і {@link LevelSensor}. Не знає жодного конкретного класу обладнання
 * і не містить перевірок типу авто, тому новий тип авто не потребує змін у цьому класі.
 */
public class FleetMaintenanceService {

    private static final String SOURCE = "FleetMaintenanceService";

    public ServicedCar commissionCar(String plateNumber, VehicleEquipmentFactory factory) {
        ConsoleLogger.log(SOURCE, "Введення авто " + plateNumber + " в роботу (тип: " + factory.getVehicleType() + ")");

        TelematicsModule telematics = factory.createTelematicsModule();
        EnergyCard energyCard = factory.createEnergyCard();
        LevelSensor levelSensor = factory.createLevelSensor();

        logElement("Модуль телематики", telematics);
        logElement("Картка поповнення енергії", energyCard);
        logElement("Датчик рівня", levelSensor);

        Set<VehicleType> types = EnumSet.of(telematics.getVehicleType(),
                energyCard.getVehicleType(), levelSensor.getVehicleType());
        if (types.size() == 1) {
            ConsoleLogger.log(SOURCE, "Перевірка узгодженості: усі елементи набору належать до типу «"
                    + types.iterator().next() + "»");
        } else {
            ConsoleLogger.log(SOURCE, "ПОМИЛКА: у наборі змішано типи " + types);
        }

        ServicedCar car = new ServicedCar(plateNumber, telematics, energyCard, levelSensor);
        ConsoleLogger.log(SOURCE, "Авто " + plateNumber + " введено в роботу");
        return car;
    }

    private void logElement(String role, EquipmentElement element) {
        ConsoleLogger.log(SOURCE, "  " + role + ": " + element.describe()
                + " [тип: " + element.getVehicleType() + "]");
    }
}
