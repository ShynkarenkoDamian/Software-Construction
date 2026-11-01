# ADR-002: Перенесення логіки в сутності (Information Expert) та конструювання (Creator)

## Статус
Прийнято (Accepted)

## Контекст
У legacy-коді вартість перевезення і страховки рахувалась у `processShipmentConsole()`,
хоча дані (вага, заявлена вартість, тип доставки) належать вантажу. Сутності створювались
порожніми через `new X()` і заповнювались полями, без жодних перевірок.

## Questions, Options, Criteria (QOC)

**Question:** Хто має рахувати вартість перевезення та страховки?

**Options:**
* A: Залишити в сервісі/`Main`.
* B: Покласти на `Shipment`.
* C: Покласти на `Cargo`, а `Shipment` лише викликає `Cargo` під час створення.

**Criteria:**
* Information Expert: хто має потрібні дані? `Cargo` має вагу, вартість, тип доставки.
  `Shipment` лише посилається на `Cargo`.
* Low Coupling: сервіс не повинен знати про внутрішні поля вантажу.
* High Cohesion: розрахунок ціни вантажу — частина «життя» вантажу.

**Conclusion:** Option C. `Cargo` має методи `transportFee`, `insuranceFee`, `totalCost`.

## Рішення
1. **Information Expert:** розрахунки — у `Cargo`; керування статусом (`dispatch()`) — у `Shipment`.
   Поля стали `private final` (`Shipment.status` змінюється лише методом `dispatch()`).
   `Sender` — `record` (незмінний).
2. **Creator:** `Shipment` створюється лише через `Shipment.create(...)` (приватний конструктор).
   Фабрика одразу рахує вартість і ставить статус `CREATED`. Конструктори `Cargo` та `Sender`
   перевіряють дані (вага > 0, email містить `@` тощо) — невалідний об'єкт створити неможливо.
   Складання з даних команди робить `ShipmentFactory`.
3. Статус — `enum ShipmentStatus` замість рядка.
4. Закон Деметри: `Shipment` має методи `senderEmail()`, `senderCompanyName()`, `deliveryType()`.

## Наслідки
* **Позитивні:** неможливий невалідний стан; логіка ціни в одному місці; немає `public`-полів.
* **Негативні / компроміси:** `Shipment` залежить від `Cargo`, `Sender`, `PricingPolicy`,
  `ShipmentStatus` (CBO = 4, на межі норми).
