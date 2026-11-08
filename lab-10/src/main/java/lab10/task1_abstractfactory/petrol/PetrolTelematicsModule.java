package lab10.task1_abstractfactory.petrol;

import lab10.task1_abstractfactory.TelematicsModule;
import lab10.task1_abstractfactory.VehicleType;

/** Конкретний продукт: модуль телематики бензинового авто (тип «бензинове»). Видимий лише в межах пакета — створюється тільки фабрикою. */
class PetrolTelematicsModule implements TelematicsModule {

    @Override
    public VehicleType getVehicleType() {
        return VehicleType.PETROL;
    }

    @Override
    public String describe() {
        return "модуль телематики бензинового авто";
    }
}
