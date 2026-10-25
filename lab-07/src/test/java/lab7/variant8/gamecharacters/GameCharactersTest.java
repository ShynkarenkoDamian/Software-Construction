package lab7.variant8.gamecharacters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static lab7.variant8.gamecharacters.GameTestSupport.output;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameCharactersTest {

    private static final String NL = System.lineSeparator();

    @Test
    @DisplayName("Маг: переміщення, закляття і лікування")
    void mageActions() {
        String result = output(out -> {
            MageCharacter mage = new MageCharacter(out);
            mage.move(3, 4);
            mage.castSpell("Fireball", 30);
            mage.castHeal(25);
        });

        assertEquals("Mage moved to (3, 4)." + NL
                + "Mage cast spell Fireball." + NL
                + "Mage restored 25 HP." + NL, result);
    }

    @Test
    @DisplayName("Торговець: переміщення і торгівля")
    void vendorActions() {
        String result = output(out -> {
            NpcVendor vendor = new NpcVendor(out);
            vendor.move(5, 6);
            vendor.tradeWithNpc(9);
        });

        assertEquals("Vendor walked to shop stall at (5, 6)." + NL
                + "Vendor opened shop inventory for player." + NL, result);
    }

    @Test
    @DisplayName("Коваль (ілюстративний): переміщення, крафт і торгівля")
    void blacksmithActions() {
        String result = output(out -> {
            BlacksmithNpc smith = new BlacksmithNpc(out);
            smith.move(1, 1);
            smith.craftItem("iron-sword");
            smith.tradeWithNpc(2);
        });

        assertEquals("Blacksmith walked to forge at (1, 1)." + NL
                + "Blacksmith crafted item by recipe iron-sword." + NL
                + "Blacksmith opened forge catalog for player." + NL, result);
    }

    @Test
    @DisplayName("WorldNavigator працює з будь-якими Movable: маг, торговець, коваль")
    void navigatorMovesEveryMovable() {
        String result = output(out -> new WorldNavigator().moveAll(
                List.of(new MageCharacter(out), new NpcVendor(out), new BlacksmithNpc(out)), 7, 8));

        assertEquals("Mage moved to (7, 8)." + NL
                + "Vendor walked to shop stall at (7, 8)." + NL
                + "Blacksmith walked to forge at (7, 8)." + NL, result);
    }

    @Test
    @DisplayName("Клієнти делегують лише своїй ролі")
    void clientsDelegateToTheirRole() {
        String result = output(out -> {
            MageCharacter mage = new MageCharacter(out);
            new BattleSystem().castInCombat(mage, "Frostbolt", 15);
            new BattleSystem().supportAlly(mage, 10);
            new Marketplace().openShop(new NpcVendor(out), 3);
            new Workshop().craft(new BlacksmithNpc(out), "shield");
        });

        assertEquals("Mage cast spell Frostbolt." + NL
                + "Mage restored 10 HP." + NL
                + "Vendor opened shop inventory for player." + NL
                + "Blacksmith crafted item by recipe shield." + NL, result);
    }

    @Test
    @DisplayName("Guard clauses: залежності й аргументи клієнтів обов'язкові")
    void nullArgumentsFailFast() {
        assertThrows(NullPointerException.class, () -> new MageCharacter(null));
        assertThrows(NullPointerException.class, () -> new NpcVendor(null));
        assertThrows(NullPointerException.class, () -> new BlacksmithNpc(null));
        assertThrows(NullPointerException.class, () -> new WorldNavigator().moveAll(null, 0, 0));
        assertThrows(NullPointerException.class, () -> new BattleSystem().castInCombat(null, "x", 1));
        assertThrows(NullPointerException.class, () -> new BattleSystem().supportAlly(null, 1));
        assertThrows(NullPointerException.class, () -> new Marketplace().openShop(null, 1));
        assertThrows(NullPointerException.class, () -> new Workshop().craft(null, "x"));
    }
}
