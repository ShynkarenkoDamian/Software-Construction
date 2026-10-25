package lab7.variant8.datarepositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DataSyncServiceTest {

    private final DataSyncService<String> service = new DataSyncService<>();

    @Test
    @DisplayName("Усі вхідні записи потрапляють у цільове сховище")
    void syncsAllEntities() {
        InMemoryRepository<String> target = new InMemoryRepository<>();

        service.syncEntities(target, List.of("a", "b", "c"));

        assertEquals(List.of("a", "b", "c"), target.getAll());
    }

    @Test
    @DisplayName("Порожній список нічого не змінює")
    void emptyIncomingDataIsNoOp() {
        InMemoryRepository<String> target = new InMemoryRepository<>();

        service.syncEntities(target, List.of());

        assertEquals(0, target.getAll().size());
    }

    @Test
    @DisplayName("Guard clauses: сховище і дані обов'язкові")
    void nullArgumentsFailFast() {
        assertThrows(NullPointerException.class, () -> service.syncEntities(null, List.of("a")));
        assertThrows(NullPointerException.class, () -> service.syncEntities(new InMemoryRepository<>(), null));
    }
}
