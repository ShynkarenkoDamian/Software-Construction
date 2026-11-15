package carsharing.task2_facade;

import carsharing.common.Log;

/**
 * Facade: уся послідовність початку поїздки описана тут, в одному місці.
 * Підсистеми передаються ззовні, тому залишаються доступними й для окремого використання.
 */
public class TripFacade {

    private final DriverLicenseService license;
    private final PaymentService payment;
    private final CarReservationService reservation;
    private final DoorLockService doorLock;
    private final TelematicsActivator telematics;
    private final TripInsuranceService insurance;

    public TripFacade(DriverLicenseService license, PaymentService payment,
                      CarReservationService reservation, DoorLockService doorLock,
                      TelematicsActivator telematics, TripInsuranceService insurance) {
        this.license = license;
        this.payment = payment;
        this.reservation = reservation;
        this.doorLock = doorLock;
        this.telematics = telematics;
        this.insurance = insurance;
    }

    public void startTrip(String clientId, String carId) {
        Log.log("TripFacade", "startTrip(" + clientId + ", " + carId + ") - початок послідовності");
        license.verify(clientId);
        payment.holdFunds(clientId);
        reservation.reserve(carId, clientId);
        doorLock.unlock(carId);
        telematics.activate(carId);
        insurance.insure(clientId, carId);
        Log.log("TripFacade", "поїздку розпочато");
    }
}
