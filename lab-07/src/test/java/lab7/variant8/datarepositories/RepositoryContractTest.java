package lab7.variant8.datarepositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * КОНТРАКТНИЙ ТЕСТ для LSP: описує поведінку, яку обіцяє інтерфейс {@link Repository}.
 * Кожна нова реалізація зобов'язана мати нащадка цього класу — якщо вона його не проходить,
 * підстановка її замість {@code Repository} зламає клієнтський код.
 */
abstract class RepositoryContractTest {

    protected abstract Repository<String> createRepository();

    protected Repository<String> repository;

    @BeforeEach
    void createFreshRepository() {
        repository = createRepository();
    }

    @Test
    @DisplayName("Передумова: add(null) → IllegalArgumentException")
    void addNullIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> repository.add(null));
    }

    @Test
    @DisplayName("Передумова: update(null) → IllegalArgumentException")
    void updateNullIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> repository.update(null));
    }

    @Test
    @DisplayName("Передумова: remove(null) → IllegalArgumentException")
    void removeNullIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> repository.remove(null));
    }

    @Test
    @DisplayName("Постумова add: getAll() містить доданий елемент")
    void addMakesEntityVisible() {
        repository.add("a");
        repository.add("b");

        assertEquals(2, repository.getAll().size());
        assertTrue(repository.getAll().contains("a"));
        assertTrue(repository.getAll().contains("b"));
    }

    @Test
    @DisplayName("Постумова remove: getAll() більше не містить елемента")
    void removeMakesEntityDisappear() {
        repository.add("a");
        repository.remove("a");

        assertFalse(repository.getAll().contains("a"));
    }

    @Test
    @DisplayName("remove неіснуючого елемента не змінює стан і не кидає виняток")
    void removeMissingEntityIsNoOp() {
        repository.add("a");
        repository.remove("zzz");

        assertEquals(1, repository.getAll().size());
    }

    @Test
    @DisplayName("update коректної сутності не кидає виняток")
    void updateValidEntityDoesNotThrow() {
        repository.add("a");
        repository.update("a");

        assertEquals(1, repository.getAll().size());
    }

    @Test
    @DisplayName("Постумова getAll: список не можна змінити ззовні")
    void getAllIsUnmodifiable() {
        repository.add("a");

        assertThrows(UnsupportedOperationException.class, () -> repository.getAll().add("hack"));
    }
}
