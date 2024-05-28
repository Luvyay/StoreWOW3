package ru.gb.Buy.model.enums;

/**
 * Перечисление с методами оплаты
 */
public enum BankMethodPayment {
    MASTERCARD("Mastercard");

    private String title;

    BankMethodPayment(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
