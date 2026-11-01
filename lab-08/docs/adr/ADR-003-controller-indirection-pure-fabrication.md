# ADR-003: Тонкий CLI, Controller, Indirection та Pure Fabrication

## Статус
Прийнято (Accepted)

## Контекст
Один метод у legacy-коді одночасно читав консоль, рахував гроші, «зберігав» у реєстр
і «надсилав» email.

## Questions, Options, Criteria (QOC)

**Question:** Хто координує сценарій «оформити відправку» і як відокремити його від консолі та сховища?

**Options:**
* A: Залишити все в `ShipmentConsole` (монолітний Main).
* B: Один `ShipmentController`, який робить усе сам: створення, збереження, email, накладна.
* C: Тонкий CLI -> тонкий `ShipmentController` -> `ShipmentFactory` + `ShipmentDispatchService`,
  а сховище та email сховані за інтерфейсами.

**Criteria:**
* Controller: перший об'єкт після UI не повинен містити ні консолі, ні розрахунків.
* Low Coupling: CBO контролера ≤ 4.
* Indirection / Protected Variations: заміна реєстру чи email не повинна чіпати застосунок.

**Conclusion:** Option C. У варіанті B контролер мав би 6 залежностей (CBO = 6 > 4).

## Рішення
| Патерн | Клас | Що робить |
|--------|------|-----------|
| Controller | `ShipmentController` | приймає `CreateShipmentCommand`, делегує фабриці та сервісу |
| Thin UI | `ShipmentConsole` | лише `Scanner` + `println`, без логіки |
| Indirection | `ShipmentRepository`, `TrackingNotifier` | інтерфейси між застосунком та інфраструктурою |
| Pure Fabrication | `ShipmentFactory`, `ShipmentDispatchService`, `WaybillFormatter`, `TariffRegistry`, `IdSequence`, `ConsoleCommandParser`, `InMemoryShipmentRepository`, `ConsoleEmailNotifier` | технічні класи, яких немає в бізнес-домені, але які розвантажують сутності |

Контролер повертає текст накладної як `String`, а консоль його друкує.

## Наслідки
* **Позитивні:** CLI, застосунок і домен незалежні; сховище або email можна замінити без змін
  контролера; контролер має CBO = 3.
* **Негативні / компроміси:** більше класів; `ShipmentFactory` має CBO = 7 (це клас-збирач,
  норма ≤ 4 стосується сутностей і контролерів).
