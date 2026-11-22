package ua.miskavto.lab16.task2_memento;

import ua.miskavto.lab16.common.Log;

/**
 * Originator: авто з налаштуваннями клієнта (сидіння, дзеркала, клімат, мультимедіа).
 * Поля закриті; знімок створюється й відновлюється лише самим авто.
 */
public class Car {

    private final String plate;
    private final String model;

    private int seatPosition = 3;
    private int mirrorAngle = 0;
    private double climateTemperature = 20.0;
    private int volume = 0;
    private String radioStation = "вимкнено";

    public Car(String plate, String model) {
        this.plate = plate;
        this.model = model;
    }

    public String getPlate() {
        return plate;
    }

    public String getModel() {
        return model;
    }

    public void setSeatPosition(int seatPosition) {
        this.seatPosition = seatPosition;
    }

    public void setMirrorAngle(int mirrorAngle) {
        this.mirrorAngle = mirrorAngle;
    }

    public void setClimateTemperature(double climateTemperature) {
        this.climateTemperature = climateTemperature;
    }

    public void setMultimedia(int volume, String radioStation) {
        this.volume = volume;
        this.radioStation = radioStation;
    }

    public String describe() {
        return model + ": сидіння " + seatPosition + ", дзеркала " + mirrorAngle + "°, клімат "
                + climateTemperature + " °C, мультимедіа: гучність " + volume + ", «" + radioStation + "»";
    }

    // ---------------- Memento ----------------

    /** Вузький інтерфейс знімка для зовнішніх класів: вмісту не видно, можна дізнатися лише модель. */
    public interface Snapshot {
        String getModel();
    }

    /** Повний знімок: приватний клас, його поля доступні лише авто. */
    private static final class SettingsState implements Snapshot {
        private final String model;
        private final int seatPosition;
        private final int mirrorAngle;
        private final double climateTemperature;
        private final int volume;
        private final String radioStation;

        private SettingsState(String model, int seatPosition, int mirrorAngle, double climateTemperature,
                              int volume, String radioStation) {
            this.model = model;
            this.seatPosition = seatPosition;
            this.mirrorAngle = mirrorAngle;
            this.climateTemperature = climateTemperature;
            this.volume = volume;
            this.radioStation = radioStation;
        }

        @Override
        public String getModel() {
            return model;
        }
    }

    public Snapshot save() {
        return new SettingsState(model, seatPosition, mirrorAngle, climateTemperature, volume, radioStation);
    }

    /** @return true, якщо налаштування відновлено; false, якщо знімок від іншої моделі авто */
    public boolean restore(Snapshot snapshot) {
        SettingsState state = (SettingsState) snapshot;
        if (!state.model.equals(model)) {
            Log.log(plate, "Відмова: збережені налаштування для моделі «" + state.model
                    + "», а це авто — «" + model + "»");
            return false;
        }
        this.seatPosition = state.seatPosition;
        this.mirrorAngle = state.mirrorAngle;
        this.climateTemperature = state.climateTemperature;
        this.volume = state.volume;
        this.radioStation = state.radioStation;
        return true;
    }
}
