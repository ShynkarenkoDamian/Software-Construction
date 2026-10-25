package lab7.variant8.courseregistration;

import java.util.Objects;

/**
 * Високорівнева бізнес-логіка реєстрації. Залежить ЛИШЕ від абстракцій; конкретні реалізації
 * не створює (жодного {@code new} інфраструктурних класів) і отримує через конструктор
 * (Constructor Injection). Збирає граф об'єктів Composition Root ({@code Main}).
 *
 * Порядок кроків і тексти листа збережено: бронювання місця → квитанція → лист.
 */
public class CourseRegistrationService {

    private static final String MAIL_SUBJECT = "Підтвердження реєстрації на курс";

    private final CourseRepository courseRepository;
    private final RegistrationSlipGenerator slipGenerator;
    private final EmailGateway emailGateway;

    public CourseRegistrationService(CourseRepository courseRepository,
                                     RegistrationSlipGenerator slipGenerator,
                                     EmailGateway emailGateway) {
        this.courseRepository = Objects.requireNonNull(courseRepository, "courseRepository");
        this.slipGenerator = Objects.requireNonNull(slipGenerator, "slipGenerator");
        this.emailGateway = Objects.requireNonNull(emailGateway, "emailGateway");
    }

    public void registerStudent(RegistrationRequest request) {
        requireValid(request);

        courseRepository.bookCourseSeat(request.getCourseCode(), request.getStudentId());
        slipGenerator.generateRegistrationSlip(request.getStudentId(), request.getCourseCode());
        emailGateway.sendOfficialMail(request.getStudentEmail(), MAIL_SUBJECT,
                "Вас успішно зараховано на курс " + request.getCourseCode() + ".");
    }

    /** Guard clauses: та сама умова, що й у вихідному коді (null або порожній рядок), без довгого {@code ||}-ланцюжка. */
    private static void requireValid(RegistrationRequest request) {
        if (request == null) {
            throw invalidRequest();
        }
        if (isNullOrEmpty(request.getStudentId())) {
            throw invalidRequest();
        }
        if (isNullOrEmpty(request.getCourseCode())) {
            throw invalidRequest();
        }
    }

    private static boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    private static IllegalArgumentException invalidRequest() {
        return new IllegalArgumentException("Некоректна заявка на реєстрацію.");
    }
}
