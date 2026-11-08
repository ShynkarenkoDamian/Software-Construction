package lab10.task1_abstractfactory.electric;

import lab10.task1_abstractfactory.EnergyCard;
import lab10.task1_abstractfactory.LevelSensor;
import lab10.task1_abstractfactory.TelematicsModule;
import lab10.task1_abstractfactory.VehicleEquipmentFactory;
import lab10.task1_abstractfactory.VehicleType;

/** Конкретна фабрика: набір обладнання для авто типу «електромобіль». */
public class ElectricEquipmentFactory implements VehicleEquipmentFactory {

    @Override
    public VehicleType getVehicleType() {
        return VehicleType.ELECTRIC;
    }

    @Override
    public TelematicsModule createTelematicsModule() {
        return new ElectricTelematicsModule();
    }

    @Override
    public EnergyCard createEnergyCard() {
        return new ElectricEnergyCard();
    }

    @Override
    public LevelSensor createLevelSensor() {
        return new ElectricLevelSensor();
    }
}
