# Лабораторна робота №9 — Породжувальні шаблони (варіант 8, «Каршерінг «Міськавто»»)

Мова: Java 17+. Збірка: Maven (`pom.xml`).

| Завдання | Шаблон | Пакет | Статус |
|---|---|---|---|
| 1. Видача автомобілів парку | Object Pool | `lab9.task1_objectpool` | виконано |
| 2. Корпоративні тарифні плани | Prototype | `lab9.task2_prototype` | виконано |
| 3. Карта парку автомобілів | Singleton | `lab9.task3_singleton` | виконано |
| Діаграми 1–3 | — | `docs/diagrams/` | виконано |
| Крос-рев'ю | — | `docs/` (розділ 7) | чернетка готова, потрібне підтвердження обох |

## Запуск
- IntelliJ IDEA: *File → Open* → вибрати теку проєкту (підхопиться `pom.xml`) → запустити `lab9.Main`.
- Maven: `mvn compile exec:java`.

## Структура
```
src/main/java/lab9/
  Main.java                  раннер: окремий метод на кожну ситуацію
  common/ConsoleLogger.java  журнал [час] [джерело] подія
  task1_objectpool/          Object Pool
  task2_prototype/           Prototype
  task3_singleton/           Singleton (FleetMap, FleetCar, CarStatus, Dispatcher, MobileApp)
docs/
  Підготовка_до_захисту.md   теорія, що зроблено і як, що розповідати
  sample_output.txt          еталонний вивід раннера
  diagrams/                  .mmd (джерело), .svg і .png
```
Повна підготовка до захисту — у `docs/Підготовка_до_захисту.md`.
