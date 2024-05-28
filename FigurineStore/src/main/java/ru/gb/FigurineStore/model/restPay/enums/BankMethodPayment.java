package ru.gb.FigurineStore.model.restPay.enums;

/**
 * Перечисление методов оплаты
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
