package lab7.variant8.courseregistration;

/**
 * Низькорівнева реалізація: «MariaDB». Вивід симулює роботу з БД, як у вихідному коді.
 */
public class MariadbCourseRepository implements CourseRepository {

    @Override
    public void bookCourseSeat(String courseCode, String studentId) {
        System.out.println("[MariaDB Storage] Студента " + studentId + " зараховано на курс '" + courseCode + "'. Кількість місць зменшено.");
    }
}
