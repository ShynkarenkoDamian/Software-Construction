package carsharing.dispatch;

import carsharing.util.Logger;

/** ConcreteColleague: служба підтримки. Лише виконує команди координатора. */
public class SupportService extends DispatchService {
    public SupportService(DispatchMediator mediator) {
        super(mediator, "SupportService");
    }

    /** Команда координатора. */
    public void handleCase(String carPlate) {
        Logger.log(toString(), "приймає звернення щодо авто " + carPlate + " і зв'язується з клієнтом");
    }
}
