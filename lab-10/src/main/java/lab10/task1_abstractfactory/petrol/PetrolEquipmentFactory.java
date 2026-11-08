package lab10.task1_abstractfactory.petrol;

import lab10.task1_abstractfactory.EnergyCard;
import lab10.task1_abstractfactory.LevelSensor;
import lab10.task1_abstractfactory.TelematicsModule;
import lab10.task1_abstractfactory.VehicleEquipmentFactory;
import lab10.task1_abstractfactory.VehicleType;

/** Конкретна фабрика: набір обладнання для авто типу «бензинове». */
public class PetrolEquipmentFactory implements VehicleEquipmentFactory {

    @Override
    public VehicleType getVehicleType() {
        return VehicleType.PETROL;
    }

    @Override
    public TelematicsModule createTelematicsModule() {
        return new PetrolTelematicsModule();
    }

    @Override
    public EnergyCard createEnergyCard() {
        return new PetrolEnergyCard();
    }

    @Override
    public LevelSensor createLevelSensor() {
        return new PetrolLevelSensor();
    }
}
