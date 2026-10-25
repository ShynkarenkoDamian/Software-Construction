package lab7.variant8.courseregistration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Підміна для демо й тестів: лист не відправляється, а зберігається в «скриньку» у пам'яті.
 */
public class InMemoryEmailGateway implements EmailGateway {

    /** Відправлений лист. */
    public record SentMail(String email, String subject, String body) {
    }

    private final List<SentMail> outbox = new ArrayList<>();

    @Override
    public void sendOfficialMail(String email, String subject, String body) {
        outbox.add(new SentMail(email, subject, body));
    }

    public List<SentMail> outbox() {
        return Collections.unmodifiableList(outbox);
    }
}
