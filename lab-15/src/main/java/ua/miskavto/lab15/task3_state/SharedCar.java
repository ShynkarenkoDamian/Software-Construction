package ua.miskavto.lab15.task3_state;

import ua.miskavto.lab15.common.Log;

/**
 * Context (State): авто каршерингу. Не містить розгалужень за статусом:
 * кожну операцію делегує поточному об'єкту-стану, а переходи виконують самі стани через setState().
 */
public class SharedCar {

    private final String plate;
    private CarState state = new AvailableState();
    private String currentClient;

    public SharedCar(String plate) {
        this.plate = plate;
        Log.log(source(), "Авто додано в парк, стан «" + state.title() + "»");
    }

    // ----- операції (делегування поточному стану) -----

    public void reserve(String client) {
        state.reserve(this, client);
    }

    public void unlock(String client) {
        state.unlock(this, client);
    }

    public void pause() {
        state.pause(this);
    }

    public void endTrip() {
        state.endTrip(this);
    }

    public void tick(int minutes) {
        state.tick(this, minutes);
    }

    public void sendToMaintenance() {
        state.sendToMaintenance(this);
    }

    public void returnFromMaintenance() {
        state.returnFromMaintenance(this);
    }

    // ----- службові методи для станів -----

    public void setState(CarState newState) {
        Log.log(source(), "Перехід: «" + state.title() + "» → «" + newState.title() + "»");
        this.state = newState;
    }

    public void refuse(String operation, String reason) {
        Log.log(source(), "ВІДМОВА: " + operation + " неможливе в стані «" + state.title() + "» — " + reason);
    }

    public String getPlate() {
        return plate;
    }

    public CarState getState() {
        return state;
    }

    public String getCurrentClient() {
        return currentClient;
    }

    public void setCurrentClient(String currentClient) {
        this.currentClient = currentClient;
    }

    public String source() {
        return "SharedCar " + plate;
    }
}
