package carsharing.dispatch;

/** Типи подій, про які служби повідомляють координатору. */
public enum DispatchEventType {
    REPAIR_FINISHED("завершення ремонту"),
    ACCIDENT_REPORTED("ДТП"),
    EVACUATION_COMPLETED("завершення евакуації");

    private final String label;

    DispatchEventType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
