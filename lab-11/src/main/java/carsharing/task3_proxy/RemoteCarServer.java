package carsharing.task3_proxy;

import carsharing.common.Log;

/**
 * Віддалений сервер виробника (RealSubject): кожен виклик повільний і обмежений лімітом.
 * Лічильник звернень потрібен, щоб довести: при відмові сервер не викликається.
 */
public class RemoteCarServer implements CarControl {

    private final String carId;
    private int requestCount;

    public RemoteCarServer(String carId) {
        this.carId = carId;
    }

    @Override
    public void openDoors(String clientId) {
        execute("відкрити двері", clientId);
    }

    @Override
    public void startEngine(String clientId) {
        execute("увімкнути двигун", clientId);
    }

    @Override
    public void honk(String clientId) {
        execute("посигналити", clientId);
    }

    public int getRequestCount() {
        return requestCount;
    }

    private void execute(String command, String clientId) {
        requestCount++;
        Log.log("RemoteCarServer", "запит №" + requestCount + " до сервера виробника: авто " + carId
                + ", команда «" + command + "» (від " + clientId + ")");
    }
}
