package logistics.application;

/**
 * Indirection + Protected Variations: спосіб сповіщення (email, SMS) ховається за інтерфейсом.
 * Параметри — прості рядки, тому інтерфейс не залежить від доменних класів.
 */
public interface TrackingNotifier {

    void sendTrackingCode(String email, String trackingCode);
}
