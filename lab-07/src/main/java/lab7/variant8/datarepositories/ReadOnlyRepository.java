package lab7.variant8.datarepositories;

import java.util.List;

/**
 * Контракт ЧИТАННЯ — спільний для будь-якого сховища, зокрема й аудиторського.
 *
 * Постумова: повертає список, який не можна змінити через сам список
 * (будь-яка спроба змінити його ззовні — програмна помилка).
 */
public interface ReadOnlyRepository<T> {

    List<T> getAll();
}
