# ЛР16. Поведінкові шаблони: Memento, Visitor, Iterator

Предметна область: **Каршерінг «Міськавто»** (варіант 8). Мова: Java 17, збірка: Maven, сторонніх бібліотек немає.
Проєкт моделює три проблемні ситуації з умови лаби; поведінка системи демонструється консольним журналом подій у форматі `[час] [джерело] подія`, стан об'єктів виводиться до й після операції.

## Завдання

| № | Ситуація | Шаблон | Пакет | Діаграма | Виконавець |
|---|---|---|---|---|---|
| 1 | Операції над парком | Visitor | `task1_visitor` | `docs/diagrams/task1_visitor/` | я |
| 2 | Налаштування авто для клієнта | Memento | `task2_memento` | `docs/diagrams/task2_memento/` | я |
| 3 | Обхід авто для обслуговування | Iterator | `task3_iterator` | `docs/diagrams/task3_iterator/` | я |

Теорія, принципи, приклади й опис рішень — у [`docs/THEORY.md`](docs/THEORY.md).

## Структура

```
lab16-visitor-memento/
├── pom.xml
├── .gitignore
├── README.md
├── docs/
│   ├── THEORY.md                         теорія та пояснення рішень
│   └── diagrams/                         діаграми класів (Mermaid: .mmd + .svg, .png, .pdf)
│       ├── task1_visitor/                task1-visitor.*
│       ├── task2_memento/                task2-memento.*
│       └── task3_iterator/               task3-iterator.*
└── src/main/java/ua/miskavto/lab16/
    ├── Main.java                         точка входу
    ├── ScenarioRunner.java               окремий метод на кожну ситуацію
    ├── common/
    │   └── Log.java                      журнал [час] [джерело] подія
    ├── task1_visitor/                    FleetVisitor, InsuranceVisitor, MaintenanceVisitor,
    │                                     Co2EmissionsVisitor, Vehicle, PetrolCar, HybridCar,
    │                                     ElectricCar, Minibus, Fleet
    ├── task2_memento/                    Car (з вкладеними Snapshot і SettingsState), ClientProfile
    └── task3_iterator/                   Zone, ParkingLot, ParkedCar, ZoneCarIterator,
                                          FilteredCarIterator, LowChargeRouteIterator, IdleCarIterator
```

## Запуск

```
mvn package
java -jar target/lab16-visitor-memento-1.0.jar
```

Якщо в консолі Windows некоректні літери: `chcp 65001` або `java -Dfile.encoding=UTF-8 -jar ...`.
