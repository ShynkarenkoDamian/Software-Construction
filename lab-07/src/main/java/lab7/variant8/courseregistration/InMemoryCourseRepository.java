package lab7.variant8.courseregistration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Підміна для демо й тестів («MockRepository»): нічого не друкує і не звертається до БД,
 * лише запам'ятовує бронювання у вигляді «студент → курс».
 */
public class InMemoryCourseRepository implements CourseRepository {

    private final List<String> bookings = new ArrayList<>();

    @Override
    public void bookCourseSeat(String courseCode, String studentId) {
        bookings.add(studentId + " -> " + courseCode);
    }

    public List<String> bookings() {
        return Collections.unmodifiableList(bookings);
    }
}
