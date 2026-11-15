package carsharing.task2_facade;

import carsharing.common.Log;

/** Підсистема: увімкнення телематики. */
public class TelematicsActivator {

    public void activate(String carId) {
        Log.log("TelematicsActivator", "телематику авто " + carId + " увімкнено");
    }
}
