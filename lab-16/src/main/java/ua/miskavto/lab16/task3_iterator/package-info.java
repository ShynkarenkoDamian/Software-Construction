/**
 * Завдання 3 (ЛР16, варіант 8): «Обхід авто для обслуговування» — шаблон Iterator.
 *
 * <p>Структура: зони (з вкладеними зонами) -> майданчики -> авто. Колекція {@link Zone} віддає
 * ітератори, а клієнти обходять авто, не знаючи внутрішньої структури:
 * <ul>
 *   <li>{@link LowChargeRouteIterator} — авто з низьким зарядом у порядку маршруту;</li>
 *   <li>{@link ZoneCarIterator} — усі авто зони, включно з вкладеними;</li>
 *   <li>{@link IdleCarIterator} — авто, що простоюють понад добу.</li>
 * </ul>
 *
 * <p>Демонстрація: {@code ScenarioRunner.situation3Iterator()}. Діаграма: {@code docs/diagrams/task3_iterator/}.
 */
package ua.miskavto.lab16.task3_iterator;
