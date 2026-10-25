package lab7.variant8.utilitybilling.legacy;

import java.math.BigDecimal;

/** ВИХІДНИЙ код завдання 1 (клас UtilityAccountData) — лише для порівняльних тестів. */
public class LegacyAccountData {
    private String accountNumber;
    private String ownerName;
    private String ownerPhone;
    private int previousElectricityMeterReading;
    private int currentElectricityMeterReading;
    private boolean hasSubsidy;
    private BigDecimal subsidyDiscountPercent;

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public String getOwnerPhone() { return ownerPhone; }
    public void setOwnerPhone(String ownerPhone) { this.ownerPhone = ownerPhone; }

    public int getPreviousElectricityMeterReading() { return previousElectricityMeterReading; }
    public void setPreviousElectricityMeterReading(int previousElectricityMeterReading) { this.previousElectricityMeterReading = previousElectricityMeterReading; }

    public int getCurrentElectricityMeterReading() { return currentElectricityMeterReading; }
    public void setCurrentElectricityMeterReading(int currentElectricityMeterReading) { this.currentElectricityMeterReading = currentElectricityMeterReading; }

    public boolean isHasSubsidy() { return hasSubsidy; }
    public void setHasSubsidy(boolean hasSubsidy) { this.hasSubsidy = hasSubsidy; }

    public BigDecimal getSubsidyDiscountPercent() { return subsidyDiscountPercent; }
    public void setSubsidyDiscountPercent(BigDecimal subsidyDiscountPercent) { this.subsidyDiscountPercent = subsidyDiscountPercent; }
}
