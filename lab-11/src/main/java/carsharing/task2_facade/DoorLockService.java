package carsharing.task2_facade;

import carsharing.common.Log;

/** Підсистема: розблокування дверей. Доступна й окремо (наприклад, для служби підтримки). */
public class DoorLockService {

    public void unlock(String carId) {
        Log.log("DoorLockService", "двері авто " + carId + " розблоковано");
    }
}
