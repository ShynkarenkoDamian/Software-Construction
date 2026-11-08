package lab10.task1_abstractfactory.gas;

import lab10.task1_abstractfactory.TelematicsModule;
import lab10.task1_abstractfactory.VehicleType;

/** Конкретний продукт: модуль телематики авто на газу (тип «на газу»). Видимий лише в межах пакета — створюється тільки фабрикою. */
class GasTelematicsModule implements TelematicsModule {

    @Override
    public VehicleType getVehicleType() {
        return VehicleType.GAS;
    }

    @Override
    public String describe() {
        return "модуль телематики авто на газу";
    }
}
