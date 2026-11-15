package carsharing.task2_facade;

import carsharing.common.Log;

/** Підсистема: страхування поїздки. */
public class TripInsuranceService {

    public void insure(String clientId, String carId) {
        Log.log("TripInsuranceService", "поїздку клієнта " + clientId + " на авто " + carId + " застраховано");
    }
}
