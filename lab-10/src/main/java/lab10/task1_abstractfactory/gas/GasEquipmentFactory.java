package lab10.task1_abstractfactory.gas;

import lab10.task1_abstractfactory.EnergyCard;
import lab10.task1_abstractfactory.LevelSensor;
import lab10.task1_abstractfactory.TelematicsModule;
import lab10.task1_abstractfactory.VehicleEquipmentFactory;
import lab10.task1_abstractfactory.VehicleType;

/** Конкретна фабрика: набір обладнання для авто типу «на газу». */
public class GasEquipmentFactory implements VehicleEquipmentFactory {

    @Override
    public VehicleType getVehicleType() {
        return VehicleType.GAS;
    }

    @Override
    public TelematicsModule createTelematicsModule() {
        return new GasTelematicsModule();
    }

    @Override
    public EnergyCard createEnergyCard() {
        return new GasEnergyCard();
    }

    @Override
    public LevelSensor createLevelSensor() {
        return new GasLevelSensor();
    }
}
