package carsharing.dispatch;

import carsharing.util.Logger;

/**
 * ConcreteMediator: координатор диспетчерської. Усі правила реагування на події служб
 * зібрано тут; служби не знають одна про одну.
 */
public class DispatchCenter implements DispatchMediator {
    private ClientApp clientApp;
    private FleetMap fleetMap;
    private TowService towService;
    private SupportService supportService;
    private CarWashService carWash; // необов'язкова служба

    /** Підключає службу до координатора. */
    public void register(DispatchService service) {
        if (service instanceof ClientApp s) {
            clientApp = s;
        } else if (service instanceof FleetMap s) {
            fleetMap = s;
        } else if (service instanceof TowService s) {
            towService = s;
        } else if (service instanceof SupportService s) {
            supportService = s;
        } else if (service instanceof CarWashService s) {
            carWash = s;
        }
        Logger.log(toString(), "підключено службу: " + service);
    }

    @Override
    public void reportEvent(DispatchService sender, DispatchEventType type, String carPlate) {
        Logger.log(toString(), "отримав від " + sender + ": " + type.getLabel() + " (авто " + carPlate + ")");
        switch (type) {
            case REPAIR_FINISHED -> {
                Logger.log(toString(), "правило: ремонт завершено -> повернути авто на карту");
                fleetMap.returnCar(carPlate);
            }
            case ACCIDENT_REPORTED -> {
                Logger.log(toString(), "правило: ДТП -> завершити поїздку, викликати евакуатор, прибрати з карти, передати підтримці");
                clientApp.finishTrip(carPlate);
                towService.callTowTruck(carPlate);
                fleetMap.removeCar(carPlate);
                supportService.handleCase(carPlate);
            }
            case EVACUATION_COMPLETED -> {
                if (carWash != null) {
                    Logger.log(toString(), "правило: авто евакуйовано -> направити на мийку");
                    carWash.washCar(carPlate);
                } else {
                    Logger.log(toString(), "правило: після евакуації додаткових кроків немає");
                }
            }
        }
    }

    @Override
    public String toString() {
        return "DispatchCenter";
    }
}
