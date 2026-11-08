package lab10.task1_abstractfactory.petrol;

import lab10.task1_abstractfactory.LevelSensor;
import lab10.task1_abstractfactory.VehicleType;

/** Конкретний продукт: датчик рівня пального (тип «бензинове»). Видимий лише в межах пакета — створюється тільки фабрикою. */
class PetrolLevelSensor implements LevelSensor {

    @Override
    public VehicleType getVehicleType() {
        return VehicleType.PETROL;
    }

    @Override
    public String describe() {
        return "датчик рівня пального";
    }
}
