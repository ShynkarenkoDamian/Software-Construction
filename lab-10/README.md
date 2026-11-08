# Лабораторна робота №10 — Породжувальні шаблони, частина 2 (варіант 8, «Каршерінг «Міськавто»»)

Мова: Java 17+. Збірка: Maven (`pom.xml`).

| Завдання | Шаблон | Пакет | Статус |
|---|---|---|---|
| 1. Обслуговування авто різних типів | Abstract Factory | `lab10.task1_abstractfactory` | виконано |
| 2. Види оренди | Factory Method | `lab10.task2_factorymethod` | виконано |
| 3. Бронювання авто | Builder | `lab10.task3_builder` | виконано |
| Діаграми 1–3 | — | `docs/diagrams/` | виконано |
| Крос-рев'ю | — | `docs/` (розділ 7) | чернетка готова, потрібне підтвердження обох |

## Запуск
- IntelliJ IDEA: *File → Open* → вибрати теку проєкту (підхопиться `pom.xml`) → запустити `lab10.Main`.
- Maven: `mvn compile exec:java`.

## Структура
```
src/main/java/lab10/
  Main.java                    раннер: окремий метод на кожну ситуацію
  common/ConsoleLogger.java    журнал [час] [джерело] подія
  domain/                      Car, Client — спільні класи домену (для завдань 2 і 3)
  task1_abstractfactory/       Abstract Factory (+ підпакети petrol, hybrid, electric, gas)
  task2_factorymethod/         Factory Method (+ підпакет weekend)
  task3_builder/               Builder (Booking з вкладеним Booking.Builder, BookingDirector)
docs/
  Підготовка_до_захисту_ЛР10.md   теорія, що зроблено і як, що розповідати
  sample_output.txt               еталонний вивід раннера
  diagrams/                       .mmd (джерело), .svg і .png
```
Повна підготовка до захисту — у `docs/Підготовка_до_захисту_ЛР10.md`.
