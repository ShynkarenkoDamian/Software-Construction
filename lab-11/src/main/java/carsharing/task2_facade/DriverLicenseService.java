package carsharing.task2_facade;

import carsharing.common.Log;

/** Підсистема: перевірка посвідчення водія. */
public class DriverLicenseService {

    public void verify(String clientId) {
        Log.log("DriverLicenseService", "посвідчення водія клієнта " + clientId + " перевірено");
    }
}
