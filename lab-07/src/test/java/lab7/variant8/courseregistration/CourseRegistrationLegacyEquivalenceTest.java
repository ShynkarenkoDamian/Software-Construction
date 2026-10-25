package lab7.variant8.courseregistration;

import lab7.variant8.courseregistration.legacy.Variant8_CourseRegistrationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Characterization-тест: «продакшн»-збірка нового сервісу поводиться як вихідний клас із {@code new} усередині:
 * той самий консольний вивід і те саме повідомлення про помилку.
 */
class CourseRegistrationLegacyEquivalenceTest {

    private static String systemOut(Runnable action) {
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer, true, StandardCharsets.UTF_8));
        try {
            action.run();
            return buffer.toString(StandardCharsets.UTF_8);
        } finally {
            System.setOut(original);
        }
    }

    private static CourseRegistrationService productionService() {
        return new CourseRegistrationService(
                new MariadbCourseRepository(), new PdfInvoiceGenerator(), new UniversityEmailGateway());
    }

    private static String[] data(String studentId, String email, String courseCode) {
        return new String[]{studentId, email, courseCode};
    }

    private static final List<String[]> VALID = List.of(
            data("S-1", "s1@uni.local", "CS-101"),
            data("S-2", null, "MATH-2"),
            data(" ", "x@uni.local", "PHYS-3"));

    private static final List<String[]> INVALID = List.of(
            data(null, "e@uni.local", "CS-101"),
            data("", "e@uni.local", "CS-101"),
            data("S-1", "e@uni.local", null),
            data("S-1", "e@uni.local", ""));

    private static RegistrationRequest newRequest(String[] d) {
        RegistrationRequest request = new RegistrationRequest();
        request.setStudentId(d[0]);
        request.setStudentEmail(d[1]);
        request.setCourseCode(d[2]);
        return request;
    }

    private static Variant8_CourseRegistrationService.RegistrationRequest legacyRequest(String[] d) {
        Variant8_CourseRegistrationService.RegistrationRequest request = new Variant8_CourseRegistrationService.RegistrationRequest();
        request.setStudentId(d[0]);
        request.setStudentEmail(d[1]);
        request.setCourseCode(d[2]);
        return request;
    }

    @Test
    @DisplayName("Консольний вивід збігається з вихідним сервісом для коректних заявок")
    void sameOutputForValidRequests() {
        for (String[] d : VALID) {
            String expected = systemOut(() ->
                    new Variant8_CourseRegistrationService.CourseRegistrationService().registerStudent(legacyRequest(d)));
            String actual = systemOut(() -> productionService().registerStudent(newRequest(d)));

            assertEquals(expected, actual);
        }
    }

    @Test
    @DisplayName("Некоректні заявки: той самий тип і текст винятку, що й у вихідному сервісі")
    void sameErrorForInvalidRequests() {
        for (String[] d : INVALID) {
            IllegalArgumentException expected = assertThrows(IllegalArgumentException.class, () ->
                    new Variant8_CourseRegistrationService.CourseRegistrationService().registerStudent(legacyRequest(d)));
            IllegalArgumentException actual = assertThrows(IllegalArgumentException.class,
                    () -> productionService().registerStudent(newRequest(d)));

            assertEquals(expected.getMessage(), actual.getMessage());
        }
    }

    @Test
    @DisplayName("null-заявка: той самий виняток, що й у вихідному сервісі")
    void sameErrorForNullRequest() {
        IllegalArgumentException expected = assertThrows(IllegalArgumentException.class, () ->
                new Variant8_CourseRegistrationService.CourseRegistrationService().registerStudent(null));
        IllegalArgumentException actual = assertThrows(IllegalArgumentException.class,
                () -> productionService().registerStudent(null));

        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    @DisplayName("Підміна реалізацій: in-memory збірка нічого не друкує, на відміну від «продакшн»-збірки")
    void inMemoryWiringIsSilent() {
        String console = systemOut(() -> new CourseRegistrationService(
                new InMemoryCourseRepository(), new InMemoryRegistrationSlipGenerator(), new InMemoryEmailGateway())
                .registerStudent(newRequest(VALID.get(0))));

        assertEquals("", console);
    }
}
