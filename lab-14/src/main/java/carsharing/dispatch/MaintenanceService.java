package carsharing.dispatch;

import carsharing.util.Logger;

/** ConcreteColleague: служба обслуговування. Повідомляє про завершення ремонту. */
public class MaintenanceService extends DispatchService {
    public MaintenanceService(DispatchMediator mediator) {
        super(mediator, "MaintenanceService");
    }

    /** Ремонт завершено (ініціює подію). */
    public void finishRepair(String carPlate) {
        Logger.log(toString(), "ремонт авто " + carPlate + " завершено");
        mediator.reportEvent(this, DispatchEventType.REPAIR_FINISHED, carPlate);
    }
}
