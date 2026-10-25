package lab7.variant8.gamecharacters;

import java.util.Objects;

/**
 * Клієнт бойової системи: кожен метод залежить лише від тієї ролі, яку викликає.
 * Передати сюди торговця неможливо — помилка виявляється під час компіляції, а не в бою.
 */
public class BattleSystem {

    public void castInCombat(SpellCaster caster, String spellName, int manaCost) {
        Objects.requireNonNull(caster, "caster");
        caster.castSpell(spellName, manaCost);
    }

    public void supportAlly(Healer healer, int hpAmount) {
        Objects.requireNonNull(healer, "healer");
        healer.castHeal(hpAmount);
    }
}
