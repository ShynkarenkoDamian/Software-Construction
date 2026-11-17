package carsharing.dispatch;

import carsharing.util.Logger;

/** ConcreteColleague: мийка авто (нова служба, додана без змін в існуючих службах). */
public class CarWashService extends DispatchService {
    public CarWashService(DispatchMediator mediator) {
        super(mediator, "CarWashService");
    }

    /** Команда координатора. */
    public void washCar(String carPlate) {
        Logger.log(toString(), "приймає авто " + carPlate + " на мийку");
    }
}
