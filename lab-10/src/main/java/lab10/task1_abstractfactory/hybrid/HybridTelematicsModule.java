package lab10.task1_abstractfactory.hybrid;

import lab10.task1_abstractfactory.TelematicsModule;
import lab10.task1_abstractfactory.VehicleType;

/** Конкретний продукт: модуль телематики гібрида (тип «гібрид»). Видимий лише в межах пакета — створюється тільки фабрикою. */
class HybridTelematicsModule implements TelematicsModule {

    @Override
    public VehicleType getVehicleType() {
        return VehicleType.HYBRID;
    }

    @Override
    public String describe() {
        return "модуль телематики гібрида";
    }
}
