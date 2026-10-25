package lab7.variant8.gamecharacters;

import java.io.PrintStream;
import java.util.Objects;

/**
 * NPC-торговець: переміщується і торгує. Чаклунство, лікування й крафт відсутні в його ролях.
 * Повідомлення лишились дослівно такими, як у вихідному коді.
 */
public class NpcVendor implements Movable, Trader {

    private final PrintStream out;

    public NpcVendor(PrintStream out) {
        this.out = Objects.requireNonNull(out, "out");
    }

    @Override
    public void move(int x, int y) {
        out.println("Vendor walked to shop stall at (" + x + ", " + y + ").");
    }

    @Override
    public void tradeWithNpc(int npcId) {
        out.println("Vendor opened shop inventory for player.");
    }
}
