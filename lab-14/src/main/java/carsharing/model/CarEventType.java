package carsharing.model;

/** Типи подій телематики авто (за текстом лаби). */
public enum CarEventType {
    LOW_BATTERY("низький рівень заряду"),
    CRASH("удар"),
    OUT_OF_ZONE("виїзд за межі зони"),
    TRIP_FINISHED("завершення поїздки");

    private final String label;

    CarEventType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
