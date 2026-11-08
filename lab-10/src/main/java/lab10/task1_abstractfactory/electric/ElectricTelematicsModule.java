package lab10.task1_abstractfactory.electric;

import lab10.task1_abstractfactory.TelematicsModule;
import lab10.task1_abstractfactory.VehicleType;

/** Конкретний продукт: модуль телематики електромобіля (тип «електромобіль»). Видимий лише в межах пакета — створюється тільки фабрикою. */
class ElectricTelematicsModule implements TelematicsModule {

    @Override
    public VehicleType getVehicleType() {
        return VehicleType.ELECTRIC;
    }

    @Override
    public String describe() {
        return "модуль телематики електромобіля";
    }
}
