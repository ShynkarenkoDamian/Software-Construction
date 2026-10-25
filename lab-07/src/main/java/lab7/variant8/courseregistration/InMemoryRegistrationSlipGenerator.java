package lab7.variant8.courseregistration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Підміна для демо й тестів: файл не створюється, лише запам'ятовується ім'я, яке мав би мати PDF.
 */
public class InMemoryRegistrationSlipGenerator implements RegistrationSlipGenerator {

    private final List<String> slips = new ArrayList<>();

    @Override
    public void generateRegistrationSlip(String studentId, String courseCode) {
        slips.add("registration_" + studentId + "_" + courseCode + ".pdf");
    }

    public List<String> slips() {
        return Collections.unmodifiableList(slips);
    }
}
