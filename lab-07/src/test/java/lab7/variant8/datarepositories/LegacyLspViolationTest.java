package lab7.variant8.datarepositories;

import lab7.variant8.datarepositories.legacy.Variant8_DataRepositories.DataRepository;
import lab7.variant8.datarepositories.legacy.Variant8_DataRepositories.DataSyncService;
import lab7.variant8.datarepositories.legacy.Variant8_DataRepositories.ReadOnlyAuditRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * ДОКАЗ порушення LSP у вихідному коді (для захисту): клієнт працює з базовим типом,
 * а підстановка нащадка ламає його під час виконання.
 */
class LegacyLspViolationTest {

    @Test
    @DisplayName("Базовий DataRepository: синхронізація працює")
    void baseTypeWorksWithClient() {
        DataRepository<String> base = new DataRepository<>();

        new DataSyncService<String>().syncEntities(base, List.of("a", "b"));

        assertEquals(2, base.getAll().size());
    }

    @Test
    @DisplayName("Підстановка ReadOnlyAuditRepository замість DataRepository ламає клієнта (UnsupportedOperationException)")
    void substitutionBreaksClient() {
        DataRepository<String> substitute = new ReadOnlyAuditRepository<>();

        assertThrows(UnsupportedOperationException.class,
                () -> new DataSyncService<String>().syncEntities(substitute, List.of("a")));
    }

    @Test
    @DisplayName("Нащадок кидає різні, не задекларовані базою винятки — контракт непослідовний")
    void inconsistentFailures() {
        DataRepository<String> substitute = new ReadOnlyAuditRepository<>();

        assertThrows(UnsupportedOperationException.class, () -> substitute.add("a"));
        assertThrows(IllegalStateException.class, () -> substitute.update("a"));
        assertThrows(IllegalStateException.class, () -> substitute.remove("a"));
    }
}
