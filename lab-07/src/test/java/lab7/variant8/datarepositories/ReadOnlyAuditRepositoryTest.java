package lab7.variant8.datarepositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReadOnlyAuditRepositoryTest {

    @Test
    @DisplayName("Показує записи джерела (view), зокрема ті, що з'явились пізніше")
    void reflectsSource() {
        InMemoryRepository<String> source = new InMemoryRepository<>();
        ReadOnlyAuditRepository<String> audit = new ReadOnlyAuditRepository<>(source);

        source.add("event-1");
        source.add("event-2");

        assertEquals(source.getAll(), audit.getAll());
    }

    @Test
    @DisplayName("Список записів не можна змінити через аудиторське представлення")
    void viewIsUnmodifiable() {
        InMemoryRepository<String> source = new InMemoryRepository<>();
        source.add("event-1");
        ReadOnlyAuditRepository<String> audit = new ReadOnlyAuditRepository<>(source);

        assertThrows(UnsupportedOperationException.class, () -> audit.getAll().add("forged"));
        assertThrows(UnsupportedOperationException.class, () -> audit.getAll().remove("event-1"));
    }

    @Test
    @DisplayName("LSP: це не Repository, тому його не можна підставити туди, де потрібен запис")
    void isNotAWritableRepository() {
        assertTrue(ReadOnlyRepository.class.isAssignableFrom(ReadOnlyAuditRepository.class));
        assertFalse(Repository.class.isAssignableFrom(ReadOnlyAuditRepository.class));
    }

    @Test
    @DisplayName("Guard clause: джерело обов'язкове")
    void sourceIsRequired() {
        assertThrows(NullPointerException.class, () -> new ReadOnlyAuditRepository<String>(null));
    }
}
