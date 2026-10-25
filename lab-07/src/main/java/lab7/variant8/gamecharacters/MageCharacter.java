package lab7.variant8.gamecharacters;

import java.io.PrintStream;
import java.util.Objects;

/**
 * Маг: переміщується, чаклує і лікує. Крафт і торгівля не входять до його ролей,
 * тому методів-заглушок із {@link UnsupportedOperationException} більше немає.
 * Повідомлення лишились дослівно такими, як у вихідному коді.
 */
public class MageCharacter implements Movable, SpellCaster, Healer {

    private final PrintStream out;

    public MageCharacter(PrintStream out) {
        this.out = Objects.requireNonNull(out, "out");
    }

    @Override
    public void move(int x, int y) {
        out.println("Mage moved to (" + x + ", " + y + ").");
    }

    @Override
    public void castSpell(String spellName, int manaCost) {
        // manaCost, як і у вихідному коді, лише частина контракту: пул мани в моделі відсутній.
        out.println("Mage cast spell " + spellName + ".");
    }

    @Override
    public void castHeal(int hpAmount) {
        out.println("Mage restored " + hpAmount + " HP.");
    }
}
