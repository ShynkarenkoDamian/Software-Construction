package carsharing.dispatch;

import carsharing.util.Logger;

/** ConcreteColleague: служба евакуації. Виїжджає за командою, сама повідомляє про завершення евакуації. */
public class TowService extends DispatchService {
    public TowService(DispatchMediator mediator) {
        super(mediator, "TowService");
    }

    /** Команда координатора. */
    public void callTowTruck(String carPlate) {
        Logger.log(toString(), "виїжджає евакуатор за авто " + carPlate);
    }

    /** Евакуатор доставив авто (ініціює подію). */
    public void completeEvacuation(String carPlate) {
        Logger.log(toString(), "авто " + carPlate + " доставлено з місця ДТП");
        mediator.reportEvent(this, DispatchEventType.EVACUATION_COMPLETED, carPlate);
    }
}
