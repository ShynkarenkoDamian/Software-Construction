package lab7.variant8.courseregistration.legacy;

/**
 * ВИХІДНИЙ код завдання 5 з порушенням DIP, без змін (змінено лише рядок package).
 * Збережений у тестах для порівняльного (characterization) тесту.
 */

// Опис предметної області: Університетська автоматизована система реєстрації на дисципліни.
// Завдання коду: Реєстрація студента на навчальний курс, зменшення кількості вільних місць у БД MariaDB, формування PDF-квитанції про зарахування та надсилання інституційного email.

public class Variant8_CourseRegistrationService {

    public static class RegistrationRequest {
        private String studentId;
        private String studentEmail;
        private String courseCode;

        public String getStudentId() { return studentId; }
        public void setStudentId(String studentId) { this.studentId = studentId; }
        public String getStudentEmail() { return studentEmail; }
        public void setStudentEmail(String studentEmail) { this.studentEmail = studentEmail; }
        public String getCourseCode() { return courseCode; }
        public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    }

    public static class MariadbCourseRepository {
        public void bookCourseSeat(String courseCode, String studentId) {
            System.out.println("[MariaDB Storage] Студента " + studentId + " зараховано на курс '" + courseCode + "'. Кількість місць зменшено.");
        }
    }

    public static class PdfInvoiceGenerator {
        public void generateRegistrationSlip(String studentId, String courseCode) {
            System.out.println("[PDF Engine] Згенеровано PDF-повідомлення 'registration_" + studentId + "_" + courseCode + ".pdf'.");
        }
    }

    public static class UniversityEmailGateway {
        public void sendOfficialMail(String email, String subject, String body) {
            System.out.println("[University Mail Gateway] Лист на " + email + " | Тема: '" + subject + "'.");
        }
    }

    public static class CourseRegistrationService {
        private MariadbCourseRepository courseRepository;
        private PdfInvoiceGenerator pdfGenerator;
        private UniversityEmailGateway emailGateway;

        public CourseRegistrationService() {
            this.courseRepository = new MariadbCourseRepository();
            this.pdfGenerator = new PdfInvoiceGenerator();
            this.emailGateway = new UniversityEmailGateway();
        }

        public void registerStudent(RegistrationRequest request) {
            if (request == null || request.getStudentId() == null || request.getStudentId().isEmpty() || request.getCourseCode() == null || request.getCourseCode().isEmpty()) {
                throw new IllegalArgumentException("Некоректна заявка на реєстрацію.");
            }

            this.courseRepository.bookCourseSeat(request.getCourseCode(), request.getStudentId());
            this.pdfGenerator.generateRegistrationSlip(request.getStudentId(), request.getCourseCode());
            this.emailGateway.sendOfficialMail(request.getStudentEmail(), "Підтвердження реєстрації на курс", "Вас успішно зараховано на курс " + request.getCourseCode() + ".");
        }
    }
}
