package lab7.variant8.datarepositories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Повноцінне сховище в пам'яті (колишній базовий {@code DataRepository}).
 * Підтримує всі операції контракту {@link Repository}.
 */
public class InMemoryRepository<T> implements Repository<T> {

    private final List<T> items = new ArrayList<>();

    @Override
    public void add(T entity) {
        requireEntity(entity);
        items.add(entity);
        System.out.println("[Repository] Entity added.");
    }

    /**
     * Як і в оригінальному коді, лише перевіряє передумову та повідомляє про оновлення:
     * поняття ідентифікатора сутності у вихідному завданні відсутнє.
     */
    @Override
    public void update(T entity) {
        requireEntity(entity);
        System.out.println("[Repository] Entity updated.");
    }

    @Override
    public void remove(T entity) {
        requireEntity(entity);
        items.remove(entity);
        System.out.println("[Repository] Entity removed.");
    }

    @Override
    public List<T> getAll() {
        return Collections.unmodifiableList(items);
    }

    /** Єдине місце перевірки передумови (раніше була продублювана в трьох методах). */
    private static void requireEntity(Object entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
    }
}
