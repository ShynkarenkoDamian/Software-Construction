package lab10.task1_abstractfactory.gas;

import lab10.task1_abstractfactory.EnergyCard;
import lab10.task1_abstractfactory.VehicleType;

/** Конкретний продукт: газова картка (заправка газом) (тип «на газу»). Видимий лише в межах пакета — створюється тільки фабрикою. */
class GasEnergyCard implements EnergyCard {

    @Override
    public VehicleType getVehicleType() {
        return VehicleType.GAS;
    }

    @Override
    public String describe() {
        return "газова картка (заправка газом)";
    }
}
