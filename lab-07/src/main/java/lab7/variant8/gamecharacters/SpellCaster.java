package lab7.variant8.gamecharacters;

/**
 * Роль «виконує магічні дії». Реалізують лише ті персонажі, які справді вміють чаклувати.
 */
public interface SpellCaster {

    void castSpell(String spellName, int manaCost);
}
