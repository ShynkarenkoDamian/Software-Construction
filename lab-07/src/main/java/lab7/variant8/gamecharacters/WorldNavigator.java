package lab7.variant8.gamecharacters;

import java.util.List;
import java.util.Objects;

/**
 * Клієнт-навігатор: викликає лише {@code move}, тому залежить виключно від {@link Movable}.
 * Працює однаково з магом, торговцем і ковалем.
 */
public class WorldNavigator {

    public void moveAll(List<? extends Movable> units, int x, int y) {
        Objects.requireNonNull(units, "units");

        for (Movable unit : units) {
            unit.move(x, y);
        }
    }
}
