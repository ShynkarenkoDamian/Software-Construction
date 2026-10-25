package lab7.variant8.datarepositories;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Аудиторський журнал лише для читання.
 *
 * Це НЕ нащадок {@link Repository}: він не обіцяє add/update/remove, тому нічого не порушує.
 * Замість успадкування — композиція: представлення (view) над джерелом записів.
 * Спроба передати його туди, де потрібен запис, не скомпілюється.
 */
public class ReadOnlyAuditRepository<T> implements ReadOnlyRepository<T> {

    private final ReadOnlyRepository<T> source;

    public ReadOnlyAuditRepository(ReadOnlyRepository<T> source) {
        this.source = Objects.requireNonNull(source, "source");
    }

    @Override
    public List<T> getAll() {
        return Collections.unmodifiableList(source.getAll());
    }
}
