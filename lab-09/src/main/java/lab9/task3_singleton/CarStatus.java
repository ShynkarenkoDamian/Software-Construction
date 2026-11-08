package lab9.task3_singleton;

/** Стан автомобіля на карті парку. */
public enum CarStatus {
    AVAILABLE("вільне"),
    RESERVED("зарезервоване"),
    ISSUED("видане");

    private final String title;

    CarStatus(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
