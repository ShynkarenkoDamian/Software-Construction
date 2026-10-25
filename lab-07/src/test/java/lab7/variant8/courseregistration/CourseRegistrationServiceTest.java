package lab7.variant8.courseregistration;

import lab7.variant8.courseregistration.InMemoryEmailGateway.SentMail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тести бізнес-логіки БЕЗ БД, PDF і пошти: усі залежності — підміни. Саме це й дає DIP.
 */
class CourseRegistrationServiceTest {

    private static final String INVALID_MESSAGE = "Некоректна заявка на реєстрацію.";

    private final InMemoryCourseRepository repository = new InMemoryCourseRepository();
    private final InMemoryRegistrationSlipGenerator slips = new InMemoryRegistrationSlipGenerator();
    private final InMemoryEmailGateway mail = new InMemoryEmailGateway();
    private final CourseRegistrationService service = new CourseRegistrationService(repository, slips, mail);

    private static RegistrationRequest request(String studentId, String email, String courseCode) {
        RegistrationRequest request = new RegistrationRequest();
        request.setStudentId(studentId);
        request.setStudentEmail(email);
        request.setCourseCode(courseCode);
        return request;
    }

    private void assertNoSideEffects() {
        assertTrue(repository.bookings().isEmpty(), "бронювань не має бути");
        assertTrue(slips.slips().isEmpty(), "квитанцій не має бути");
        assertTrue(mail.outbox().isEmpty(), "листів не має бути");
    }

    @Test
    @DisplayName("Коректна заявка: місце заброньовано, квитанцію створено, лист надіслано")
    void registersStudent() {
        service.registerStudent(request("S-1", "s1@uni.local", "CS-101"));

        assertEquals(List.of("S-1 -> CS-101"), repository.bookings());
        assertEquals(List.of("registration_S-1_CS-101.pdf"), slips.slips());
        assertEquals(List.of(new SentMail("s1@uni.local", "Підтвердження реєстрації на курс",
                "Вас успішно зараховано на курс CS-101.")), mail.outbox());
    }

    @Test
    @DisplayName("Порядок кроків: бронювання → квитанція → лист")
    void stepsRunInOrder() {
        List<String> events = new ArrayList<>();
        CourseRegistrationService ordered = new CourseRegistrationService(
                (course, student) -> events.add("book"),
                (student, course) -> events.add("slip"),
                (email, subject, body) -> events.add("mail"));

        ordered.registerStudent(request("S-1", "s1@uni.local", "CS-101"));

        assertEquals(List.of("book", "slip", "mail"), events);
    }

    @Test
    @DisplayName("Некоректна заявка: IllegalArgumentException і жодного побічного ефекту")
    void invalidRequestIsRejected() {
        List<RegistrationRequest> invalid = new ArrayList<>();
        invalid.add(null);
        invalid.add(request(null, "e@uni.local", "CS-101"));
        invalid.add(request("", "e@uni.local", "CS-101"));
        invalid.add(request("S-1", "e@uni.local", null));
        invalid.add(request("S-1", "e@uni.local", ""));

        for (RegistrationRequest candidate : invalid) {
            IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                    () -> service.registerStudent(candidate));
            assertEquals(INVALID_MESSAGE, error.getMessage());
        }
        assertNoSideEffects();
    }

    @Test
    @DisplayName("Як і у вихідному коді, email не перевіряється й передається шлюзу як є (навіть null)")
    void emailIsNotValidated() {
        service.registerStudent(request("S-1", null, "CS-101"));

        assertNull(mail.outbox().get(0).email());
    }

    @Test
    @DisplayName("Як і у вихідному коді, рядок із пробілів не вважається порожнім")
    void whitespaceOnlyIdIsAccepted() {
        service.registerStudent(request(" ", "e@uni.local", "CS-101"));

        assertEquals(1, repository.bookings().size());
    }

    @Test
    @DisplayName("Збій сховища зупиняє процес: квитанція й лист не створюються")
    void repositoryFailureStopsTheProcess() {
        CourseRegistrationService failing = new CourseRegistrationService(
                (course, student) -> {
                    throw new IllegalStateException("DB is down");
                }, slips, mail);

        assertThrows(IllegalStateException.class,
                () -> failing.registerStudent(request("S-1", "e@uni.local", "CS-101")));
        assertTrue(slips.slips().isEmpty());
        assertTrue(mail.outbox().isEmpty());
    }

    @Test
    @DisplayName("Guard clauses: усі три залежності обов'язкові")
    void dependenciesAreRequired() {
        assertThrows(NullPointerException.class, () -> new CourseRegistrationService(null, slips, mail));
        assertThrows(NullPointerException.class, () -> new CourseRegistrationService(repository, null, mail));
        assertThrows(NullPointerException.class, () -> new CourseRegistrationService(repository, slips, null));
    }
}
