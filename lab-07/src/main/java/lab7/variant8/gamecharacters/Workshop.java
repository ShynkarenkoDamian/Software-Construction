package lab7.variant8.gamecharacters;

import java.util.Objects;

/**
 * Клієнт майстерні: потребує лише {@link Crafter}.
 */
public class Workshop {

    public void craft(Crafter crafter, String recipe) {
        Objects.requireNonNull(crafter, "crafter");
        crafter.craftItem(recipe);
    }
}
