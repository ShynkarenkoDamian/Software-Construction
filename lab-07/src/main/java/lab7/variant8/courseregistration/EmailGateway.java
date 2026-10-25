package lab7.variant8.courseregistration;

/**
 * АБСТРАКЦІЯ каналу листування. Хто саме відправляє лист (університетський шлюз, тестова заглушка) —
 * деталь реалізації.
 */
public interface EmailGateway {

    void sendOfficialMail(String email, String subject, String body);
}
