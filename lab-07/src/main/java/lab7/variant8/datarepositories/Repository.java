package lab7.variant8.datarepositories;

/**
 * Контракт ЗАПИСУ поверх читання. Реалізація зобов'язана підтримувати ВСІ операції,
 * тому клієнт, що отримав {@code Repository}, може безпечно викликати будь-яку з них.
 *
 * Передумова для add/update/remove: entity != null, інакше {@link IllegalArgumentException}.
 * Постумова add: після успішного виклику {@link #getAll()} містить entity.
 * Постумова remove: після успішного виклику {@link #getAll()} не містить entity
 * (якщо її не було — стан не змінюється).
 */
public interface Repository<T> extends ReadOnlyRepository<T> {

    void add(T entity);

    void update(T entity);

    void remove(T entity);
}
