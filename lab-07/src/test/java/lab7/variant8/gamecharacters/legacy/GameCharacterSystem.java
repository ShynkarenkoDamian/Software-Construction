package lab7.variant8.gamecharacters.legacy;

/**
 * ВИХІДНИЙ код завдання 4 з порушенням ISP, без змін (змінено лише рядок package).
 * Збережений у тестах для демонстрації проблеми «жирного» інтерфейсу.
 */

// Опис предметної області: Рольова комп'ютерна гра (RPG) та ігрова механіка.
// Завдання коду: Обслуговування дій ігрових сутностей — переміщення, виконання магічних дій, лікування, крафтинг та торгівля.

public class GameCharacterSystem {

    public interface IGameCharacter {
        void move(int x, int y);
        void castSpell(String spellName, int manaCost);
        void castHeal(int hpAmount);
        void craftItem(String recipe);
        void tradeWithNpc(int npcId);
    }

    public static class MageCharacter implements IGameCharacter {
        @Override public void move(int x, int y) { System.out.println("Mage moved to (" + x + ", " + y + ")."); }
        @Override public void castSpell(String spellName, int manaCost) { System.out.println("Mage cast spell " + spellName + "."); }
        @Override public void castHeal(int hpAmount) { System.out.println("Mage restored " + hpAmount + " HP."); }

        @Override public void craftItem(String recipe) { throw new UnsupportedOperationException("Mage cannot craft physical items."); }
        @Override public void tradeWithNpc(int npcId) { throw new UnsupportedOperationException("Mage is in battle mode."); }
    }

    public static class NpcVendor implements IGameCharacter {
        @Override public void move(int x, int y) { System.out.println("Vendor walked to shop stall at (" + x + ", " + y + ")."); }
        @Override public void tradeWithNpc(int npcId) { System.out.println("Vendor opened shop inventory for player."); }

        @Override public void castSpell(String spellName, int manaCost) { throw new UnsupportedOperationException("Vendor NPC cannot cast spells."); }
        @Override public void castHeal(int hpAmount) { throw new UnsupportedOperationException("Vendor NPC cannot heal entities."); }
        @Override public void craftItem(String recipe) { throw new UnsupportedOperationException("Vendor NPC does not craft items on demand."); }
    }
}
