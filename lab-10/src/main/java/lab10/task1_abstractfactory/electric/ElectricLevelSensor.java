package lab10.task1_abstractfactory.electric;

import lab10.task1_abstractfactory.LevelSensor;
import lab10.task1_abstractfactory.VehicleType;

/** Конкретний продукт: датчик рівня заряду батареї (тип «електромобіль»). Видимий лише в межах пакета — створюється тільки фабрикою. */
class ElectricLevelSensor implements LevelSensor {

    @Override
    public VehicleType getVehicleType() {
        return VehicleType.ELECTRIC;
    }

    @Override
    public String describe() {
        return "датчик рівня заряду батареї";
    }
}
