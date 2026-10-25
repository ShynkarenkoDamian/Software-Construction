package lab7.variant8.courseregistration;

/**
 * Низькорівнева реалізація: університетський поштовий шлюз. Вивід симулює відправку, як у вихідному коді.
 */
public class UniversityEmailGateway implements EmailGateway {

    @Override
    public void sendOfficialMail(String email, String subject, String body) {
        System.out.println("[University Mail Gateway] Лист на " + email + " | Тема: '" + subject + "'.");
    }
}
