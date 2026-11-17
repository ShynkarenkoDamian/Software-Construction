package carsharing.access;

import carsharing.util.Logger;

/**
 * Handler: одна перевірка допуску клієнта. Знає наступну ланку і вирішує:
 * зупинити клієнта з причиною або передати запит далі.
 */
public abstract class AccessCheck {
    private AccessCheck next;

    public void setNext(AccessCheck next) {
        this.next = next;
    }

    /** Спільна поведінка ланки (Template Method): перевірила -> зупинила або передала далі. */
    public final AccessResult handle(TripRequest request) {
        String name = getClass().getSimpleName();
        Logger.log(name, "отримав запит: " + request.describe());

        String reason = verify(request);
        if (reason != null) {
            Logger.log(name, "ЗУПИНЯЄ клієнта: " + reason + "; наступні перевірки не виконуються");
            return AccessResult.denied(name, reason);
        }
        if (next == null) {
            Logger.log(name, "перевірку пройдено, це остання ланка");
            return AccessResult.granted(name);
        }
        Logger.log(name, "перевірку пройдено, передає далі -> " + next.getClass().getSimpleName());
        return next.handle(request);
    }

    /** Власна логіка перевірки: null — клієнт пройшов, інакше — причина відмови. */
    protected abstract String verify(TripRequest request);
}
