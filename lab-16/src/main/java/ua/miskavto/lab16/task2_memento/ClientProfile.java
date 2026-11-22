package ua.miskavto.lab16.task2_memento;

import ua.miskavto.lab16.common.Log;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Caretaker: профіль клієнта. Зберігає знімки за іменем і віддає їх авто для відновлення,
 * але не бачить їхнього вмісту й не може його змінити.
 */
public class ClientProfile {

    private final String clientName;
    private final Map<String, Car.Snapshot> snapshots = new LinkedHashMap<>();

    public ClientProfile(String clientName) {
        this.clientName = clientName;
    }

    public void save(String name, Car car) {
        snapshots.put(name, car.save());
        Log.log("Profile:" + clientName, "Збережено налаштування «" + name + "» з авто " + car.getPlate()
                + " (" + car.getModel() + ")");
    }

    public boolean restore(String name, Car car) {
        Car.Snapshot snapshot = snapshots.get(name);
        if (snapshot == null) {
            Log.log("Profile:" + clientName, "Налаштувань «" + name + "» немає");
            return false;
        }
        boolean restored = car.restore(snapshot);
        if (restored) {
            Log.log("Profile:" + clientName, "Налаштування «" + name + "» відновлено в авто " + car.getPlate());
        }
        return restored;
    }
}
