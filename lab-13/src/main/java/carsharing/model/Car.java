package carsharing.model;

import carsharing.util.Logger;

/** Автомобіль каршерингу. У шаблоні Command — Receiver: саме він виконує справжню дію. */
public class Car {
    private final String plate;
    private boolean online = true;
    private boolean doorsOpen = false;
    private boolean hazardOn = false;
    private boolean engineBlocked = false;

    public Car(String plate) {
        this.plate = plate;
    }

    public String getPlate() {
        return plate;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
        Logger.log("Car " + plate, online ? "авто на зв'язку" : "авто втратило зв'язок");
    }

    public boolean isDoorsOpen() {
        return doorsOpen;
    }

    public void setDoorsOpen(boolean doorsOpen) {
        this.doorsOpen = doorsOpen;
        Logger.log("Car " + plate, doorsOpen ? "двері відчинено" : "двері зачинено");
    }

    public boolean isHazardOn() {
        return hazardOn;
    }

    public void setHazardOn(boolean hazardOn) {
        this.hazardOn = hazardOn;
        Logger.log("Car " + plate, hazardOn ? "аварійну сигналізацію увімкнено" : "аварійну сигналізацію вимкнено");
    }

    public boolean isEngineBlocked() {
        return engineBlocked;
    }

    public void setEngineBlocked(boolean engineBlocked) {
        this.engineBlocked = engineBlocked;
        Logger.log("Car " + plate, engineBlocked ? "двигун заблоковано" : "двигун розблоковано");
    }
}
