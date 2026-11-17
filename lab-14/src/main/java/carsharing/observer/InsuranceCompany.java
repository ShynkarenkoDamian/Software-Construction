package carsharing.observer;

import carsharing.model.CarEvent;
import carsharing.model.CarEventType;
import carsharing.util.Logger;

/** ConcreteObserver: страхова компанія. Реагує лише на удар. */
public class InsuranceCompany implements CarEventObserver {
    @Override
    public void onEvent(CarEvent event) {
        if (event.type() == CarEventType.CRASH) {
            Logger.log(toString(), "реєструє страховий випадок для авто " + event.carPlate());
        }
    }

    @Override
    public String toString() {
        return "InsuranceCompany";
    }
}
