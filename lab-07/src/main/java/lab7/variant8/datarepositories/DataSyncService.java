package lab7.variant8.datarepositories;

import java.util.List;
import java.util.Objects;

/**
 * Синхронізація даних. Вимагає саме {@link Repository} (з підтримкою запису),
 * тож «сховище лише для читання» тут неможливо підставити — компілятор не дозволить.
 */
public class DataSyncService<T> {

    public void syncEntities(Repository<T> targetRepository, List<T> incomingData) {
        Objects.requireNonNull(targetRepository, "targetRepository");
        Objects.requireNonNull(incomingData, "incomingData");

        for (T item : incomingData) {
            targetRepository.add(item);
        }
    }
}
