package lab7.variant8.courseregistration;

/**
 * Низькорівнева реалізація: PDF-квитанція. Вивід симулює генерацію файлу, як у вихідному коді.
 */
public class PdfInvoiceGenerator implements RegistrationSlipGenerator {

    @Override
    public void generateRegistrationSlip(String studentId, String courseCode) {
        System.out.println("[PDF Engine] Згенеровано PDF-повідомлення 'registration_" + studentId + "_" + courseCode + ".pdf'.");
    }
}
