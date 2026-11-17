package carsharing.dispatch;

import carsharing.util.Logger;

/** ConcreteColleague: карта парку. Лише виконує команди координатора. */
public class FleetMap extends DispatchService {
    public FleetMap(DispatchMediator mediator) {
        super(mediator, "FleetMap");
    }

    /** Команда координатора. */
    public void removeCar(String carPlate) {
        Logger.log(toString(), "прибирає авто " + carPlate + " з карти");
    }

    /** Команда координатора. */
    public void returnCar(String carPlate) {
        Logger.log(toString(), "повертає авто " + carPlate + " на карту");
    }
}
