package lab10.task1_abstractfactory.petrol;

import lab10.task1_abstractfactory.EnergyCard;
import lab10.task1_abstractfactory.VehicleType;

/** Конкретний продукт: паливна картка (бензин) (тип «бензинове»). Видимий лише в межах пакета — створюється тільки фабрикою. */
class PetrolEnergyCard implements EnergyCard {

    @Override
    public VehicleType getVehicleType() {
        return VehicleType.PETROL;
    }

    @Override
    public String describe() {
        return "паливна картка (бензин)";
    }
}
