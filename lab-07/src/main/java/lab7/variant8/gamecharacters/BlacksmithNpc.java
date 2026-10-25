package lab7.variant8.gamecharacters;

import java.io.PrintStream;
import java.util.Objects;

/**
 * ІЛЮСТРАТИВНИЙ клас (у вихідному коді його немає): коваль, що переміщується, кує і торгує.
 * Потрібен, щоб роль {@link Crafter} мала реалізацію та клієнта ({@link Workshop}),
 * і щоб показати комбінацію ролей, яка з «жирним» інтерфейсом вимагала б двох заглушок
 * (castSpell, castHeal). Повідомлення вигадані для демонстрації.
 */
public class BlacksmithNpc implements Movable, Crafter, Trader {

    private final PrintStream out;

    public BlacksmithNpc(PrintStream out) {
        this.out = Objects.requireNonNull(out, "out");
    }

    @Override
    public void move(int x, int y) {
        out.println("Blacksmith walked to forge at (" + x + ", " + y + ").");
    }

    @Override
    public void craftItem(String recipe) {
        out.println("Blacksmith crafted item by recipe " + recipe + ".");
    }

    @Override
    public void tradeWithNpc(int npcId) {
        out.println("Blacksmith opened forge catalog for player.");
    }
}
