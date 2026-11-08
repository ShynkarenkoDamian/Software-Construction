package lab10.task1_abstractfactory.gas;

import lab10.task1_abstractfactory.LevelSensor;
import lab10.task1_abstractfactory.VehicleType;

/** Конкретний продукт: датчик рівня газу в балоні (тип «на газу»). Видимий лише в межах пакета — створюється тільки фабрикою. */
class GasLevelSensor implements LevelSensor {

    @Override
    public VehicleType getVehicleType() {
        return VehicleType.GAS;
    }

    @Override
    public String describe() {
        return "датчик рівня газу в балоні";
    }
}
