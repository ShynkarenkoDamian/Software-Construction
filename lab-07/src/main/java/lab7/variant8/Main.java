package lab7.variant8;

import lab7.variant8.courseregistration.CourseRegistrationService;
import lab7.variant8.courseregistration.InMemoryCourseRepository;
import lab7.variant8.courseregistration.InMemoryEmailGateway;
import lab7.variant8.courseregistration.InMemoryRegistrationSlipGenerator;
import lab7.variant8.courseregistration.MariadbCourseRepository;
import lab7.variant8.courseregistration.PdfInvoiceGenerator;
import lab7.variant8.courseregistration.RegistrationRequest;
import lab7.variant8.courseregistration.UniversityEmailGateway;
import lab7.variant8.datarepositories.DataSyncService;
import lab7.variant8.datarepositories.InMemoryRepository;
import lab7.variant8.datarepositories.ReadOnlyAuditRepository;
import lab7.variant8.gamecharacters.BattleSystem;
import lab7.variant8.gamecharacters.BlacksmithNpc;
import lab7.variant8.gamecharacters.MageCharacter;
import lab7.variant8.gamecharacters.Marketplace;
import lab7.variant8.gamecharacters.NpcVendor;
import lab7.variant8.gamecharacters.WorldNavigator;
import lab7.variant8.gamecharacters.Workshop;
import lab7.variant8.loaninterest.InterestRateStrategy;
import lab7.variant8.loaninterest.LoanApplicant;
import lab7.variant8.loaninterest.LoanInterestCalculator;
import lab7.variant8.loaninterest.strategy.AutoLoanStrategy;
import lab7.variant8.loaninterest.strategy.BusinessLoanStrategy;
import lab7.variant8.loaninterest.strategy.ConsumerLoanStrategy;
import lab7.variant8.loaninterest.strategy.MortgageStrategy;
import lab7.variant8.loaninterest.strategy.StudentLoanStrategy;
import lab7.variant8.utilitybilling.AccountValidator;
import lab7.variant8.utilitybilling.BillCalculator;
import lab7.variant8.utilitybilling.PaymentRegistryWriter;
import lab7.variant8.utilitybilling.ReceiptFormatter;
import lab7.variant8.utilitybilling.SmsNotifier;
import lab7.variant8.utilitybilling.SubsidyCalculator;
import lab7.variant8.utilitybilling.TariffCalculator;
import lab7.variant8.utilitybilling.UtilityAccountData;
import lab7.variant8.utilitybilling.UtilityBillingService;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Clock;
import java.util.List;

/**
 * Демонстраційний запуск завдань 1–3 (варіант 8).
 */
public class Main {

    public static void main(String[] args) throws IOException {
        demoSrp();
        demoOcp();
        demoLsp();
        demoIsp();
        demoDip();
    }

    private static void demoSrp() throws IOException {
        System.out.println("===== Завдання 1. SRP: комунальні платежі =====");

        Path registry = Files.createTempFile("utility_payments_db", ".csv");
        UtilityBillingService service = new UtilityBillingService(
                new AccountValidator(),
                new BillCalculator(new TariffCalculator(), new SubsidyCalculator()),
                new ReceiptFormatter(),
                new PaymentRegistryWriter(registry, Clock.systemDefaultZone()),
                new SmsNotifier(System.out));

        UtilityAccountData account = new UtilityAccountData();
        account.setAccountNumber("ACC-001");
        account.setOwnerName("Іваненко Іван Іванович");
        account.setOwnerPhone("+380501234567");
        account.setPreviousElectricityMeterReading(1000);
        account.setCurrentElectricityMeterReading(1250);
        account.setHasSubsidy(true);
        account.setSubsidyDiscountPercent(new BigDecimal("20"));

        System.out.print(service.processMonthlyBill(account));
        System.out.println("Реєстр (" + registry + "):");
        System.out.print(Files.readString(registry));
        Files.deleteIfExists(registry);
    }

    private static void demoOcp() {
        System.out.println();
        System.out.println("===== Завдання 2. OCP: відсоткові ставки кредитів =====");

        LoanInterestCalculator calculator = new LoanInterestCalculator();
        LoanApplicant applicant = new LoanApplicant("APP-42", 720, new BigDecimal("3500.00"), new BigDecimal("10000.00"));

        List<InterestRateStrategy> products = List.of(
                new MortgageStrategy(),
                new ConsumerLoanStrategy(),
                new AutoLoanStrategy(),
                new StudentLoanStrategy(),
                new BusinessLoanStrategy()); // новий продукт: LoanInterestCalculator не змінювався

        for (InterestRateStrategy product : products) {
            calculator.calculateInterestRate(applicant, product);
        }
    }

    private static void demoLsp() {
        System.out.println();
        System.out.println("===== Завдання 3. LSP: репозиторії =====");

        InMemoryRepository<String> repository = new InMemoryRepository<>();
        new DataSyncService<String>().syncEntities(repository, List.of("запис-1", "запис-2"));

        ReadOnlyAuditRepository<String> audit = new ReadOnlyAuditRepository<>(repository);
        System.out.println("Аудит (лише читання): " + audit.getAll());

        // new DataSyncService<String>().syncEntities(audit, List.of("x"));
        //   ^ НЕ КОМПІЛЮЄТЬСЯ: ReadOnlyAuditRepository не є Repository — порушення виявляється на етапі компіляції.
    }

    private static void demoIsp() {
        System.out.println();
        System.out.println("===== Завдання 4. ISP: ролі ігрових персонажів =====");

        MageCharacter mage = new MageCharacter(System.out);
        NpcVendor vendor = new NpcVendor(System.out);
        BlacksmithNpc blacksmith = new BlacksmithNpc(System.out); // ілюстративний: комбінація Movable + Crafter + Trader

        // Навігатор залежить лише від Movable — приймає всіх трьох.
        new WorldNavigator().moveAll(List.of(mage, vendor, blacksmith), 10, 20);

        // Кожен клієнт бачить лише свою роль.
        BattleSystem battle = new BattleSystem();
        battle.castInCombat(mage, "Fireball", 30);
        battle.supportAlly(mage, 50);
        new Marketplace().openShop(vendor, 7);
        new Workshop().craft(blacksmith, "iron-sword");

        // new BattleSystem().castInCombat(vendor, "Fireball", 30);
        //   ^ НЕ КОМПІЛЮЄТЬСЯ: NpcVendor не є SpellCaster — раніше це був UnsupportedOperationException під час гри.
    }

    private static void demoDip() {
        System.out.println();
        System.out.println("===== Завдання 5. DIP: реєстрація на курс (Main = Composition Root) =====");

        RegistrationRequest request = new RegistrationRequest();
        request.setStudentId("S-1001");
        request.setStudentEmail("student@university.local");
        request.setCourseCode("CS-101");

        // Збірка №1: «продакшн» — реальні (симульовані) MariaDB, PDF і поштовий шлюз.
        CourseRegistrationService production = new CourseRegistrationService(
                new MariadbCourseRepository(),
                new PdfInvoiceGenerator(),
                new UniversityEmailGateway());
        production.registerStudent(request);

        // Збірка №2: підміна реалізацій без жодної зміни CourseRegistrationService.
        InMemoryCourseRepository repository = new InMemoryCourseRepository();
        InMemoryRegistrationSlipGenerator slips = new InMemoryRegistrationSlipGenerator();
        InMemoryEmailGateway mail = new InMemoryEmailGateway();
        new CourseRegistrationService(repository, slips, mail).registerStudent(request);

        System.out.println("In-memory збірка (без консольного виводу інфраструктури):");
        System.out.println("  бронювання: " + repository.bookings());
        System.out.println("  квитанції:  " + slips.slips());
        System.out.println("  листи:      " + mail.outbox());
    }
}
