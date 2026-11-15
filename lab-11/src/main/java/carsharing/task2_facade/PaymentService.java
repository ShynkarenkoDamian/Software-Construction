package carsharing.task2_facade;

import carsharing.common.Log;

/** Підсистема: блокування коштів на картці. */
public class PaymentService {

    public void holdFunds(String clientId) {
        Log.log("PaymentService", "кошти на картці клієнта " + clientId + " заблоковано");
    }
}
