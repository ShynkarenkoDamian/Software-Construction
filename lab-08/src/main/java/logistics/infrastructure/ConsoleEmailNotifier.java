package logistics.infrastructure;

import logistics.application.TrackingNotifier;

/** Імітація відправки email: просто друкує повідомлення в консоль. */
public class ConsoleEmailNotifier implements TrackingNotifier {

    @Override
    public void sendTrackingCode(String email, String trackingCode) {
        System.out.println("[Email] Трекінг-код " + trackingCode + " надіслано на " + email);
    }
}
