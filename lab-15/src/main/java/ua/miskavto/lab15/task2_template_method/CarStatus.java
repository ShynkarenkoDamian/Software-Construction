package ua.miskavto.lab15.task2_template_method;

/**
 * Статус авто після огляду.
 */
public enum CarStatus {
    RETURNED_FROM_TRIP("повернено з поїздки"),
    AVAILABLE("доступне"),
    SENT_FOR_ENERGY("відправлено на поповнення енергії");

    private final String title;

    CarStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
