package lab7.variant8.courseregistration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Перевіряє структуру рішення: напрям залежностей, Constructor Injection, «чисті» абстракції. */
class DependencyInversionTest {

    private static final List<Class<?>> ABSTRACTIONS =
            List.of(CourseRepository.class, RegistrationSlipGenerator.class, EmailGateway.class);
    private static final List<Class<?>> DETAILS =
            List.of(MariadbCourseRepository.class, PdfInvoiceGenerator.class, UniversityEmailGateway.class);

    @Test
    @DisplayName("Сервіс має поля лише абстрактних типів, незмінні (final)")
    void serviceFieldsAreFinalAbstractions() {
        for (Field field : CourseRegistrationService.class.getDeclaredFields()) {
            if (field.isSynthetic() || Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            assertTrue(Modifier.isFinal(field.getModifiers()), field.getName() + " має бути final");
            assertTrue(ABSTRACTIONS.contains(field.getType()), field.getName() + " має бути абстракцією");
        }
    }

    @Test
    @DisplayName("Constructor Injection: єдиний публічний конструктор приймає три абстракції")
    void constructorTakesAbstractions() {
        Constructor<?>[] constructors = CourseRegistrationService.class.getConstructors();

        assertEquals(1, constructors.length);
        assertEquals(ABSTRACTIONS, Arrays.asList(constructors[0].getParameterTypes()));
    }

    @Test
    @DisplayName("Сервіс не посилається на жодну конкретну реалізацію (ні в полях, ні в методах)")
    void serviceDoesNotReferenceDetails() {
        for (Method method : CourseRegistrationService.class.getDeclaredMethods()) {
            for (Class<?> type : method.getParameterTypes()) {
                assertFalse(DETAILS.contains(type), method.getName());
            }
            assertFalse(DETAILS.contains(method.getReturnType()), method.getName());
        }
    }

    @Test
    @DisplayName("Абстракції — інтерфейси, сформульовані без деталей реалізації; реалізації залежать від них")
    void abstractionsAreCleanAndDetailsImplementThem() {
        for (Class<?> abstraction : ABSTRACTIONS) {
            assertTrue(abstraction.isInterface());
            String name = abstraction.getSimpleName().toLowerCase();
            for (String detail : List.of("mariadb", "pdf", "university", "inmemory")) {
                assertFalse(name.contains(detail), abstraction.getSimpleName() + " містить деталь «" + detail + "»");
            }
            for (Method method : abstraction.getDeclaredMethods()) {
                assertEquals(String.class, method.getParameterTypes()[0], "сигнатури — мовою домену (рядки), без типів інфраструктури");
            }
        }
        assertTrue(CourseRepository.class.isAssignableFrom(MariadbCourseRepository.class));
        assertTrue(CourseRepository.class.isAssignableFrom(InMemoryCourseRepository.class));
        assertTrue(RegistrationSlipGenerator.class.isAssignableFrom(PdfInvoiceGenerator.class));
        assertTrue(RegistrationSlipGenerator.class.isAssignableFrom(InMemoryRegistrationSlipGenerator.class));
        assertTrue(EmailGateway.class.isAssignableFrom(UniversityEmailGateway.class));
        assertTrue(EmailGateway.class.isAssignableFrom(InMemoryEmailGateway.class));
    }
}
