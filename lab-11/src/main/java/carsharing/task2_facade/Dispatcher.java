package carsharing.task2_facade;

import carsharing.common.Log;

/** Клієнт фасаду: диспетчер. Підсистем не знає, працює лише з TripFacade. */
public class Dispatcher {

    private final TripFacade tripFacade;

    public Dispatcher(TripFacade tripFacade) {
        this.tripFacade = tripFacade;
    }

    public void startTrip(String clientId, String carId) {
        Log.log("Dispatcher", "диспетчер починає поїздку для клієнта " + clientId);
        tripFacade.startTrip(clientId, carId);
    }
}
