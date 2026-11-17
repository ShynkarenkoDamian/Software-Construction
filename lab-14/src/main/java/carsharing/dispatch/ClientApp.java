package carsharing.dispatch;

import carsharing.util.Logger;

/** ConcreteColleague: застосунок клієнта. Повідомляє про ДТП, за командою координатора завершує поїздку. */
public class ClientApp extends DispatchService {
    public ClientApp(DispatchMediator mediator, String clientName) {
        super(mediator, "ClientApp " + clientName);
    }

    /** Клієнт повідомляє про ДТП (ініціює подію). */
    public void reportAccident(String carPlate) {
        Logger.log(toString(), "клієнт повідомляє про ДТП з авто " + carPlate);
        mediator.reportEvent(this, DispatchEventType.ACCIDENT_REPORTED, carPlate);
    }

    /** Команда координатора. */
    public void finishTrip(String carPlate) {
        Logger.log(toString(), "завершує поїздку на авто " + carPlate);
    }
}
