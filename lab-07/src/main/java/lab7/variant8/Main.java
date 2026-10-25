package lab7.variant8;

import lab7.variant8.datarepositories.DataSyncService;
import lab7.variant8.datarepositories.InMemoryRepository;
import lab7.variant8.datarepositories.ReadOnlyAuditRepository;
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
}
