package lab7.variant8.datarepositories.legacy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ВИХІДНИЙ код завдання 3 з порушенням LSP, без змін.
 * Збережений у тестах лише для демонстрації збою клієнтського коду.
 */
public class Variant8_DataRepositories {

    public static class DataRepository<T> {
        protected final List<T> items = new ArrayList<>();

        public void add(T entity) {
            if (entity == null) throw new IllegalArgumentException("Entity cannot be null");
            items.add(entity);
            System.out.println("[Repository] Entity added.");
        }

        public void update(T entity) {
            if (entity == null) throw new IllegalArgumentException("Entity cannot be null");
            System.out.println("[Repository] Entity updated.");
        }

        public void remove(T entity) {
            if (entity == null) throw new IllegalArgumentException("Entity cannot be null");
            items.remove(entity);
            System.out.println("[Repository] Entity removed.");
        }

        public List<T> getAll() {
            return Collections.unmodifiableList(items);
        }
    }

    public static class ReadOnlyAuditRepository<T> extends DataRepository<T> {
        @Override
        public void add(T entity) {
            throw new UnsupportedOperationException("Direct addition is disabled for ReadOnlyAuditRepository.");
        }

        @Override
        public void update(T entity) {
            throw new IllegalStateException("Audit records are immutable and cannot be updated.");
        }

        @Override
        public void remove(T entity) {
            throw new IllegalStateException("Audit records cannot be deleted.");
        }
    }

    public static class DataSyncService<T> {
        public void syncEntities(DataRepository<T> targetRepository, List<T> incomingData) {
            for (T item : incomingData) {
                targetRepository.add(item);
            }
        }
    }
}
