package carsharing.task2_facade;

import carsharing.common.Log;

/** Клієнт фасаду: застосунок. Підсистем не знає, працює лише з TripFacade. */
public class MobileApp {

    private final TripFacade tripFacade;

    public MobileApp(TripFacade tripFacade) {
        this.tripFacade = tripFacade;
    }

    public void startTrip(String clientId, String carId) {
        Log.log("MobileApp", "клієнт " + clientId + " натиснув «Почати поїздку»");
        tripFacade.startTrip(clientId, carId);
    }
}
