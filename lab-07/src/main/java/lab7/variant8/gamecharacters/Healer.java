package lab7.variant8.gamecharacters;

/**
 * Роль «лікує». Окрема від {@link SpellCaster}: лікар може не володіти бойовою магією, і навпаки.
 */
public interface Healer {

    void castHeal(int hpAmount);
}
