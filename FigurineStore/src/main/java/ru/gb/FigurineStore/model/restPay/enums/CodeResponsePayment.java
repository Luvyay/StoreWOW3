package ru.gb.FigurineStore.model.restPay.enums;

/**
 * Перечисление кодов ответа от сервера оплаты
 */
public enum CodeResponsePayment {
    OK200,
    //Недостаточно денежных средств
    ERROR1,
    //Не найден пользователь с таким платежом
    ERROR2
}
