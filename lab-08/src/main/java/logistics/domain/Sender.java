package logistics.domain;

/**
 * Відправник вантажу.
 * Record: усі поля незмінні, а об'єкт з невалідними даними створити неможливо
 * (перевірки у компактному конструкторі) — це вимога Rich Domain Model.
 */
public record Sender(int id, String companyName, String contactPerson, String email) {

    public Sender {
        if (companyName == null || companyName.isBlank()) {
            throw new IllegalArgumentException("Назва компанії-відправника не може бути порожньою");
        }
        if (contactPerson == null || contactPerson.isBlank()) {
            throw new IllegalArgumentException("Контактна особа не може бути порожньою");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Некоректний email відправника");
        }
    }
}
