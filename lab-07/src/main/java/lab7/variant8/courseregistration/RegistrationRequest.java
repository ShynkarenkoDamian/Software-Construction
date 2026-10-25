package lab7.variant8.courseregistration;

/**
 * Заявка на реєстрацію студента на курс. Незмінена сутність із вихідного коду (JavaBean).
 */
public class RegistrationRequest {

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
