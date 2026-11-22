# ЛР15. Поведінкові шаблони: Strategy, State, Template Method

Предметна область: **Каршерінг «Міськавто»** (варіант 8). Мова: Java 17, збірка: Maven, сторонніх бібліотек немає.
Проєкт моделює три проблемні ситуації з умови лаби; поведінка системи демонструється консольним журналом подій у форматі `[час] [джерело] подія`.

## Завдання

| № | Ситуація | Шаблон | Пакет | Діаграма | Виконавець |
|---|---|---|---|---|---|
| 1 | Розрахунок вартості поїздки | Strategy | `task1_strategy` | `docs/diagrams/task1_strategy/` | я |
| 2 | Огляд авто | Template Method | `task2_template_method` | `docs/diagrams/task2_template_method/` | я |
| 3 | Стан авто | State | `task3_state` | `docs/diagrams/task3_state/` | колега |

Теорія, принципи, приклади й опис рішень — у [`docs/THEORY.md`](docs/THEORY.md).

## Структура

```
lab15-strategy-template-method/
├── pom.xml
├── .gitignore
├── README.md
├── docs/
│   ├── THEORY.md                         теорія та пояснення рішень
│   └── diagrams/                         діаграми класів (Mermaid: .mmd + .svg, .png, .pdf)
│       ├── task1_strategy/               task1-strategy.*
│       ├── task2_template_method/        task2-template-method.*
│       └── task3_state/                  task3-state.*
└── src/main/java/ua/miskavto/lab15/
    ├── Main.java                         точка входу
    ├── ScenarioRunner.java               окремий метод на кожну ситуацію
    ├── common/
    │   └── Log.java                      журнал [час] [джерело] подія
    ├── task1_strategy/                   Tariff, PerMinuteTariff, PerKmTariff, CombinedTariff,
    │                                     CorporateFixedTariff, NightTariff, Client, Trip
    ├── task2_template_method/            CarInspection, PetrolCarInspection, HybridCarInspection,
    │                                     ElectricCarInspection, GasCarInspection, Car, CarStatus
    └── task3_state/                      CarState, SharedCar, AvailableState, ReservedState,
                                          InTripState, PausedState, MaintenanceState
```

## Запуск

```
mvn package
java -jar target/lab15-strategy-template-method-1.0.jar
```

Якщо в консолі Windows некоректні літери: `chcp 65001` або `java -Dfile.encoding=UTF-8 -jar ...`.
