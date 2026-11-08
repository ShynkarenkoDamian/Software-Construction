package lab10.task1_abstractfactory.hybrid;

import lab10.task1_abstractfactory.EnergyCard;
import lab10.task1_abstractfactory.VehicleType;

/** Конкретний продукт: комбінована картка (пальне і зарядка) (тип «гібрид»). Видимий лише в межах пакета — створюється тільки фабрикою. */
class HybridEnergyCard implements EnergyCard {

    @Override
    public VehicleType getVehicleType() {
        return VehicleType.HYBRID;
    }

    @Override
    public String describe() {
        return "комбінована картка (пальне і зарядка)";
    }
}
