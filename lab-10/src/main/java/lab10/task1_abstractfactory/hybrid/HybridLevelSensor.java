package lab10.task1_abstractfactory.hybrid;

import lab10.task1_abstractfactory.LevelSensor;
import lab10.task1_abstractfactory.VehicleType;

/** Конкретний продукт: датчик рівня пального і заряду батареї (тип «гібрид»). Видимий лише в межах пакета — створюється тільки фабрикою. */
class HybridLevelSensor implements LevelSensor {

    @Override
    public VehicleType getVehicleType() {
        return VehicleType.HYBRID;
    }

    @Override
    public String describe() {
        return "датчик рівня пального і заряду батареї";
    }
}
