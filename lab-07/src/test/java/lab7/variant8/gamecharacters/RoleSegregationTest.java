package lab7.variant8.gamecharacters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Перевіряє саму структуру рішення: рольові інтерфейси, склад ролей, залежності клієнтів, відсутність заглушок. */
class RoleSegregationTest {

    private static final List<Class<?>> ROLES = List.of(Movable.class, SpellCaster.class, Healer.class, Crafter.class, Trader.class);

    private static Set<Class<?>> interfacesOf(Class<?> type) {
        return Arrays.stream(type.getInterfaces()).collect(Collectors.toSet());
    }

    @Test
    @DisplayName("Кожна роль — вузький інтерфейс з рівно одним методом")
    void everyRoleDeclaresExactlyOneMethod() {
        for (Class<?> role : ROLES) {
            assertTrue(role.isInterface(), role.getSimpleName() + " має бути інтерфейсом");
            assertEquals(1, role.getDeclaredMethods().length, role.getSimpleName() + ": кількість методів");
        }
    }

    @Test
    @DisplayName("Склад ролей: маг, торговець і коваль реалізують лише потрібні їм ролі")
    void charactersImplementOnlyTheirRoles() {
        assertEquals(Set.of(Movable.class, SpellCaster.class, Healer.class), interfacesOf(MageCharacter.class));
        assertEquals(Set.of(Movable.class, Trader.class), interfacesOf(NpcVendor.class));
        assertEquals(Set.of(Movable.class, Crafter.class, Trader.class), interfacesOf(BlacksmithNpc.class));
    }

    @Test
    @DisplayName("«Чужа» роль недоступна: торговець не SpellCaster/Healer/Crafter, маг не Crafter/Trader")
    void foreignRolesAreNotAvailable() {
        assertFalse(SpellCaster.class.isAssignableFrom(NpcVendor.class));
        assertFalse(Healer.class.isAssignableFrom(NpcVendor.class));
        assertFalse(Crafter.class.isAssignableFrom(NpcVendor.class));
        assertFalse(Crafter.class.isAssignableFrom(MageCharacter.class));
        assertFalse(Trader.class.isAssignableFrom(MageCharacter.class));
    }

    @Test
    @DisplayName("Клієнти залежать лише від тієї ролі, яку викликають (тип параметра = роль)")
    void clientsDependOnSingleRoles() throws NoSuchMethodException {
        assertEquals(SpellCaster.class, BattleSystem.class.getMethod("castInCombat", SpellCaster.class, String.class, int.class).getParameterTypes()[0]);
        assertEquals(Healer.class, BattleSystem.class.getMethod("supportAlly", Healer.class, int.class).getParameterTypes()[0]);
        assertEquals(Trader.class, Marketplace.class.getMethod("openShop", Trader.class, int.class).getParameterTypes()[0]);
        assertEquals(Crafter.class, Workshop.class.getMethod("craft", Crafter.class, String.class).getParameterTypes()[0]);
        assertEquals(List.class, WorldNavigator.class.getMethod("moveAll", List.class, int.class, int.class).getParameterTypes()[0]);

        // Жоден клієнт не приймає конкретного персонажа.
        for (Class<?> client : List.of(BattleSystem.class, Marketplace.class, Workshop.class, WorldNavigator.class)) {
            for (Method method : client.getDeclaredMethods()) {
                for (Class<?> parameter : method.getParameterTypes()) {
                    assertFalse(Set.of(MageCharacter.class, NpcVendor.class, BlacksmithNpc.class).contains(parameter),
                            client.getSimpleName() + "." + method.getName() + " залежить від конкретного класу");
                }
            }
        }
    }

    @Test
    @DisplayName("YAGNI/LSP: у src/main немає заглушок UnsupportedOperationException / NotImplementedException")
    void noStubsInMainSources() throws IOException {
        Path root = Path.of("src", "main", "java", "lab7", "variant8");
        assertTrue(Files.isDirectory(root), "тест очікує запуск з кореня проєкту");

        try (Stream<Path> files = Files.walk(root)) {
            for (Path file : files.filter(f -> f.toString().endsWith(".java")).toList()) {
                String source = Files.readString(file);
                assertFalse(source.contains("new UnsupportedOperationException"), file + " містить заглушку");
                assertFalse(source.contains("NotImplemented"), file + " містить заглушку");
            }
        }
    }
}
