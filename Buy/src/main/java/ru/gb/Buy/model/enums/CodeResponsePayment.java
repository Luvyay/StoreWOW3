package ru.gb.Buy.model.enums;

/**
 * Перечисление кодов со статусом оплаты
 */
public enum CodeResponsePayment {
    OK200,
    //Недостаточно денежных средств
    ERROR1,
    //Не найден пользователь с таким платежом
    ERROR2
}
