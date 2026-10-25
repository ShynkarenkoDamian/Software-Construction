package lab7.variant8.gamecharacters;

import java.util.Objects;

/**
 * Клієнт торгівлі: потребує лише {@link Trader}.
 */
public class Marketplace {

    public void openShop(Trader trader, int npcId) {
        Objects.requireNonNull(trader, "trader");
        trader.tradeWithNpc(npcId);
    }
}
