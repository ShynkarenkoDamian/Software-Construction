package lab7.variant8.gamecharacters;

import lab7.variant8.gamecharacters.legacy.GameCharacterSystem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static lab7.variant8.gamecharacters.GameTestSupport.output;
import static lab7.variant8.gamecharacters.GameTestSupport.systemOut;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Characterization-тест: усе, що вихідні класи вміли (не кидали виняток), нові класи роблять
 * з ТИМ САМИМ виводом. Усунуто лише заглушки.
 */
class GameCharactersLegacyEquivalenceTest {

    private static final List<int[]> POINTS = List.of(new int[]{0, 0}, new int[]{-5, 7}, new int[]{100, 250});
    private static final List<String> SPELLS = List.of("Fireball", "Frostbolt", "");
    private static final List<Integer> NUMBERS = List.of(0, 1, 50, -3);

    @Test
    @DisplayName("Маг: move/castSpell/castHeal дають той самий вивід, що й вихідний MageCharacter")
    void mageMatchesLegacy() {
        GameCharacterSystem.MageCharacter legacy = new GameCharacterSystem.MageCharacter();

        for (int[] p : POINTS) {
            assertEquals(systemOut(() -> legacy.move(p[0], p[1])),
                    output(out -> new MageCharacter(out).move(p[0], p[1])));
        }
        for (String spell : SPELLS) {
            for (int mana : NUMBERS) {
                assertEquals(systemOut(() -> legacy.castSpell(spell, mana)),
                        output(out -> new MageCharacter(out).castSpell(spell, mana)));
            }
        }
        for (int hp : NUMBERS) {
            assertEquals(systemOut(() -> legacy.castHeal(hp)),
                    output(out -> new MageCharacter(out).castHeal(hp)));
        }
    }

    @Test
    @DisplayName("Торговець: move/tradeWithNpc дають той самий вивід, що й вихідний NpcVendor")
    void vendorMatchesLegacy() {
        GameCharacterSystem.NpcVendor legacy = new GameCharacterSystem.NpcVendor();

        for (int[] p : POINTS) {
            assertEquals(systemOut(() -> legacy.move(p[0], p[1])),
                    output(out -> new NpcVendor(out).move(p[0], p[1])));
        }
        for (int id : NUMBERS) {
            assertEquals(systemOut(() -> legacy.tradeWithNpc(id)),
                    output(out -> new NpcVendor(out).tradeWithNpc(id)));
        }
    }
}
