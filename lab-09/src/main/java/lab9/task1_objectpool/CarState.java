package lab9.task1_objectpool;

/** Стан автомобіля парку. */
public enum CarState {
    FREE("вільне"),
    RENTED("в оренді");

    private final String title;

    CarState(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
