package lab9.task2_prototype;

/** Умови страхування у тарифі. */
public enum InsuranceType {
    BASIC("базове"),
    FULL("повне");

    private final String title;

    InsuranceType(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
