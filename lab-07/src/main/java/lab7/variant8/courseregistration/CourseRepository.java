package lab7.variant8.courseregistration;

/**
 * АБСТРАКЦІЯ сховища курсів. Належить високорівневому модулю (реєстрації) і сформульована мовою
 * предметної області: у назві немає «MariaDB» — це деталь реалізації.
 */
public interface CourseRepository {

    void bookCourseSeat(String courseCode, String studentId);
}
