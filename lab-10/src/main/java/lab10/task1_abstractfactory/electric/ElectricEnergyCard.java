package lab10.task1_abstractfactory.electric;

import lab10.task1_abstractfactory.EnergyCard;
import lab10.task1_abstractfactory.VehicleType;

/** Конкретний продукт: картка зарядної мережі (тип «електромобіль»). Видимий лише в межах пакета — створюється тільки фабрикою. */
class ElectricEnergyCard implements EnergyCard {

    @Override
    public VehicleType getVehicleType() {
        return VehicleType.ELECTRIC;
    }

    @Override
    public String describe() {
        return "картка зарядної мережі";
    }
}
