package carsharing.task2_facade;

import carsharing.common.Log;

/** Підсистема: резервування авто. */
public class CarReservationService {

    public void reserve(String carId, String clientId) {
        Log.log("CarReservationService", "авто " + carId + " зарезервовано за клієнтом " + clientId);
    }
}
