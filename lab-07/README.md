# Лабораторна робота №7 — SOLID, DRY, KISS, YAGNI

**Варіант 8 · Java · Maven · Завдання 1–3 (SRP, OCP, LSP)**

| Завдання | Принцип | Предметна область | Пакет |
|---|---|---|---|
| 1 | SRP | Комунальні платежі (`UtilityBillCalculator`) | `lab7.variant8.utilitybilling` |
| 2 | OCP | Відсоткові ставки кредитів (`LoanInterestCalculator`) | `lab7.variant8.loaninterest` |
| 3 | LSP | Репозиторії даних (`DataRepository`) | `lab7.variant8.datarepositories` |

Документи:
- [`ANALYSIS.md`](ANALYSIS.md) — аналіз вихідного коду та опис рефакторингу по кожному завданню (причини для зміни, точка розширення, порушення контракту, метрики).
- [`THEORY.md`](THEORY.md) — теорія для захисту й питання з відповідями.

## Запуск

Потрібен **JDK 17 або новіший** (у `pom.xml`: `maven.compiler.release = 17`).

**IntelliJ IDEA:** `File → Open…` → вибрати `pom.xml` → *Open as Project*. Maven підтягне залежності сам.

**Командний рядок** (якщо Maven встановлений):

```bash
mvn test                                        # усі тести
mvn compile exec:java -Dexec.mainClass=lab7.variant8.Main   # демо (потрібен exec-плагін, завантажиться автоматично)
```

Демо також можна запустити з IntelliJ: відкрити `Main.java` → зелений трикутник біля `main`.
Якщо в консолі замість кирилиці знаки питання, додайте у *Run Configuration → VM options*: `-Dfile.encoding=UTF-8`.

## Структура

```
src/main/java/lab7/variant8
├── Main.java                         демо завдань 1–3
├── utilitybilling/                   ЗАВДАННЯ 1 (SRP)
│   ├── UtilityAccountData            сутність (стан + похідне споживання)
│   ├── BillingResult                 незмінний результат розрахунку (record)
│   ├── AccountValidator              причина зміни: правила валідації
│   ├── TariffCalculator              причина зміни: тариф
│   ├── SubsidyCalculator             причина зміни: субсидії/пільги
│   ├── BillCalculator                поєднує тариф і субсидію
│   ├── ReceiptFormatter              причина зміни: вигляд квитанції
│   ├── PaymentRegistryWriter         причина зміни: збереження в реєстр (CSV)
│   ├── SmsNotifier                   причина зміни: SMS з посиланням на оплату
│   └── UtilityBillingService         оркестратор
├── loaninterest/                     ЗАВДАННЯ 2 (OCP)
│   ├── InterestRateStrategy          ТОЧКА РОЗШИРЕННЯ
│   ├── LoanInterestCalculator        контекст Strategy (без switch)
│   ├── LoanApplicant                 сутність
│   └── strategy/                     Mortgage, ConsumerLoan, AutoLoan, StudentLoan
│                                     + BusinessLoanStrategy (демонстрація розширення)
└── datarepositories/                 ЗАВДАННЯ 3 (LSP)
    ├── ReadOnlyRepository<T>         контракт читання
    ├── Repository<T>                 контракт запису (extends ReadOnlyRepository)
    ├── InMemoryRepository<T>         повноцінне сховище
    ├── ReadOnlyAuditRepository<T>    аудит лише для читання (композиція, не наслідування)
    └── DataSyncService<T>            клієнт, що потребує саме Repository
```

Тести (`src/test/java`) лежать у тих самих пакетах. У підпакетах `legacy/` збережено **вихідний код із завдання** — він
потрібен лише тестам: порівняльним (*characterization*) для завдань 1–2 та тесту, що доводить збій при підстановці
нащадка в завданні 3. У `src/main` вихідного «поганого» коду немає.

## Що саме змінено відносно вихідного коду (важливо знати на захисті)

Рефакторинг має зберігати поведінку, тому ці відхилення зібрано в одному місці:

1. **Завдання 1.** У вихідному коді квитанція (`billInvoice`) формувалась, але **нікуди не виводилась і не зберігалась**.
   Тепер `processMonthlyBill` повертає її текст (`void` → `String`). Інакше крок «генерація квитанції» був би мертвим кодом.
2. **Завдання 1.** Блок `try { … } catch (Exception)` навколо SMS прибрано: усередині немає операцій, що кидають
   перевірені винятки, а `String.format` зі статичними форматами не падає на коректних даних.
3. **Завдання 1.** Тарифну формулу переписано без `if/else` (`min`/`max` по ступенях) — результат той самий, перевірено тестом.
   Додано захисну перевірку від'ємного споживання в `TariffCalculator` (через валідатор недосяжна).
4. **Завдання 1.** Для тестованості конструктори приймають `Path` (файл реєстру), `Clock` (дата) і `PrintStream` (вивід SMS).
   Форматування `%.2f` залишено залежним від `Locale` за замовчуванням, **як в оригіналі**; тести фіксують `Locale.US`.
5. **Завдання 2.** Enum `LoanType` **прибрано**: його роль виконує сама стратегія. Гілка `default → IllegalArgumentException`
   зникла, бо «невідомий тип» тепер неможливий за конструкцією (замість нього — `null`-перевірка).
   Рядок журналу `[LOAN] …` залишився в калькуляторі без змін (це окрема відповідальність, але не предмет завдання 2).
6. **Завдання 2.** В описі завдання згадано «максимально дозволену суму кредиту», але у вихідному коді її розрахунку **немає** —
   тому я її **не вигадував**. Числа в `BusinessLoanStrategy` (13%, знижка 1% при скорингу ≥ 700) **ілюстративні** й придумані
   лише для демонстрації OCP; замініть на свої або приберіть клас.
7. **Завдання 3.** Метод `update` лишився таким, як у вихідному коді (перевіряє `null` і друкує повідомлення): у моделі немає
   ідентифікатора сутності. Виняток при `null` — як і раніше `IllegalArgumentException`.
   Додано `Objects.requireNonNull` у `DataSyncService` і `ReadOnlyAuditRepository` (fail-fast).
   Записи в `ReadOnlyAuditRepository` потрапляють із джерела (view над іншим сховищем): прямого `add` там не було й у вихідному коді.

## Як перевірено

- Основний код компілювався JDK 21 з `-source 17`, демо запускалось.
- Тести: 56 тестів пройшли в оточенні, де Maven і JUnit недоступні, тому я запускав їх через мінімальну заглушку JUnit-API
  (`@Test`, `@BeforeEach`, `@AfterEach`, `@TempDir`, `Assertions`). **Справжній `mvn test` я не запускав** — запустіть його один раз у себе.

---

## Доповнення: завдання 4–5 (ISP, DIP)

> Текст вище описує завдання 1–3 і не змінювався. Нижче — що додано для завдань 4 і 5. Заголовок цього файла й назва в `pom.xml` («tasks 1-3») лишились із першої версії.

| Завдання | Принцип | Предметна область | Пакет |
|---|---|---|---|
| 4 | ISP | Ігрові персонажі (`IGameCharacter`) | `lab7.variant8.gamecharacters` |
| 5 | DIP | Реєстрація на курс (`CourseRegistrationService`) | `lab7.variant8.courseregistration` |

Аналіз і пояснення рефакторингу — у [`ANALYSIS.md`](ANALYSIS.md) (розділи «Завдання 4», «Завдання 5»), питання для захисту — у [`THEORY.md`](THEORY.md) (розділ 10).

### Структура (нове)

```
src/main/java/lab7/variant8
├── Main.java                         + demoIsp(), demoDip(); Main = Composition Root
├── gamecharacters/                   ЗАВДАННЯ 4 (ISP)
│   ├── Movable, SpellCaster,         рольові інтерфейси (по одному методу)
│   │   Healer, Crafter, Trader
│   ├── MageCharacter                 Movable + SpellCaster + Healer
│   ├── NpcVendor                     Movable + Trader
│   ├── BlacksmithNpc                 Movable + Crafter + Trader (ілюстративний)
│   └── WorldNavigator, BattleSystem, клієнти, що залежать лише від потрібної ролі
│       Marketplace, Workshop
└── courseregistration/               ЗАВДАННЯ 5 (DIP)
    ├── CourseRepository              абстракції (належать високому рівню)
    ├── RegistrationSlipGenerator
    ├── EmailGateway
    ├── CourseRegistrationService     залежить лише від абстракцій, Constructor Injection
    ├── RegistrationRequest           сутність (без змін)
    ├── MariadbCourseRepository       «продакшн»-реалізації (вихідні класи, тепер implements ...)
    ├── PdfInvoiceGenerator
    ├── UniversityEmailGateway
    └── InMemory{CourseRepository,    підміни для демо й тестів («mock»)
        RegistrationSlipGenerator,
        EmailGateway}
```

Вихідний код: `src/test/java/lab7/variant8/gamecharacters/legacy/GameCharacterSystem.java` і
`src/test/java/lab7/variant8/courseregistration/legacy/Variant8_CourseRegistrationService.java` (змінено лише рядок `package`).

### Що змінено відносно вихідного коду (завдання 4–5)

1. **Завдання 4.** Додано чотири клієнтські класи та ілюстративний `BlacksmithNpc` — їх немає у вихідному коді (причини в `ANALYSIS.md` §4.4).
   Повідомлення мага й торговця збережено дослівно. Параметр `manaCost` лишився не використаним, як в оригіналі.
2. **Завдання 4.** Персонажі приймають `PrintStream` у конструкторі (для тестів); `Main` передає `System.out`.
3. **Завдання 5.** Конкретні класи (`MariadbCourseRepository`, `PdfInvoiceGenerator`, `UniversityEmailGateway`) зберегли імена та вивід, додано лише `implements`.
   Вони, як і раніше, друкують у `System.out`.
4. **Завдання 5.** Сервіс і сутність винесено з вкладених класів у окремі файли. Умову валідації розкладено на guard clauses без зміни змісту;
   поведінку, яка може здатись помилкою (e-mail не перевіряється, пробіли не вважаються порожнім рядком), **не виправляли** — вона зафіксована тестами.
5. **Завдання 5.** `InMemory*` класи — додані мною підміни (їх немає у вихідному коді): потрібні для демонстрації заміни реалізацій і для тестів.
6. **`UtilityBillingService` (завдання 1) не змінювався.** Раніше в ANALYSIS/THEORY було припущення, що DIP торкнеться й його; у варіанті 8 завдання 5 стосується реєстрації на курс.

### Як перевірено (завдання 4–5)

- Основний код компілювався JDK 21 з `-source 17`, `Main` запускався (демо завдань 1–5 відпрацювало).
- Тести: усі 87 пройшли (56 попередніх + 31 нових: 16 для завдання 4, 15 для завдання 5), але знову через мінімальну заглушку JUnit-API,
  а не через справжній `mvn test` — Maven у тому оточенні недоступний. **Запустіть `mvn test` у себе один раз.**
- Якщо в консолі замість кирилиці знаки питання, додайте до VM options `-Dfile.encoding=UTF-8` (для JDK 19+ також `-Dstdout.encoding=UTF-8`).
