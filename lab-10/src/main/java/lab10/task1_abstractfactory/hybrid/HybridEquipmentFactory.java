package lab10.task1_abstractfactory.hybrid;

import lab10.task1_abstractfactory.EnergyCard;
import lab10.task1_abstractfactory.LevelSensor;
import lab10.task1_abstractfactory.TelematicsModule;
import lab10.task1_abstractfactory.VehicleEquipmentFactory;
import lab10.task1_abstractfactory.VehicleType;

/** Конкретна фабрика: набір обладнання для авто типу «гібрид». */
public class HybridEquipmentFactory implements VehicleEquipmentFactory {

    @Override
    public VehicleType getVehicleType() {
        return VehicleType.HYBRID;
    }

    @Override
    public TelematicsModule createTelematicsModule() {
        return new HybridTelematicsModule();
    }

    @Override
    public EnergyCard createEnergyCard() {
        return new HybridEnergyCard();
    }

    @Override
    public LevelSensor createLevelSensor() {
        return new HybridLevelSensor();
    }
}
