package carsharing.task3_bridge;

/**
 * Канал доставки звіту (Implementor): відповідає на питання «як доставляється».
 * Нічого не знає про те, який саме звіт доставляє.
 */
public interface ReportChannel {

    void deliver(String title, String body);
}
