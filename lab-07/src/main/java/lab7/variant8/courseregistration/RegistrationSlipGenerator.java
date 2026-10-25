package lab7.variant8.courseregistration;

/**
 * АБСТРАКЦІЯ генератора підтвердження про зарахування. Формат (PDF чи інший) — деталь реалізації.
 */
public interface RegistrationSlipGenerator {

    void generateRegistrationSlip(String studentId, String courseCode);
}
