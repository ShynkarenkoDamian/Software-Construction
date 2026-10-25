package lab7.variant8.gamecharacters;

/**
 * Роль «може переміщуватись». Єдиний метод, який потрібен клієнтам навігації
 * ({@link WorldNavigator}); маг, торговець і коваль реалізують цю роль.
 */
public interface Movable {

    void move(int x, int y);
}
