package lab7.variant8.gamecharacters;

import lab7.variant8.gamecharacters.legacy.GameCharacterSystem.IGameCharacter;
import lab7.variant8.gamecharacters.legacy.GameCharacterSystem.MageCharacter;
import lab7.variant8.gamecharacters.legacy.GameCharacterSystem.NpcVendor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Consumer;

import static lab7.variant8.gamecharacters.GameTestSupport.systemOut;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * ДОКАЗ порушення ISP у вихідному коді (для захисту): «жирний» інтерфейс змушує обидві реалізації
 * мати методи-заглушки, а клієнта, що працює з IGameCharacter, — отримувати винятки під час виконання.
 */
class LegacyIspViolationTest {

    private static final List<Consumer<IGameCharacter>> ALL_ACTIONS = List.of(
            c -> c.move(1, 2),
            c -> c.castSpell("Fireball", 10),
            c -> c.castHeal(5),
            c -> c.craftItem("sword"),
            c -> c.tradeWithNpc(1));

    private static int unsupportedCalls(IGameCharacter character) {
        int count = 0;
        for (Consumer<IGameCharacter> action : ALL_ACTIONS) {
            try {
                systemOut(() -> action.accept(character));
            } catch (UnsupportedOperationException expected) {
                count++;
            }
        }
        return count;
    }

    @Test
    @DisplayName("Маг змушений мати 2 заглушки (craftItem, tradeWithNpc)")
    void mageHasTwoStubs() {
        assertEquals(2, unsupportedCalls(new MageCharacter()));
    }

    @Test
    @DisplayName("Торговець змушений мати 3 заглушки (castSpell, castHeal, craftItem)")
    void vendorHasThreeStubs() {
        assertEquals(3, unsupportedCalls(new NpcVendor()));
    }

    @Test
    @DisplayName("Клієнт, що бачить лише IGameCharacter, падає під час виконання на «чужій» операції")
    void clientFailsAtRuntime() {
        IGameCharacter vendor = new NpcVendor();

        assertThrows(UnsupportedOperationException.class, () -> vendor.castSpell("Fireball", 10));
        // Тип нічого не підказує: компілятор дозволив цей виклик, помилку видно лише в грі.
    }
}
