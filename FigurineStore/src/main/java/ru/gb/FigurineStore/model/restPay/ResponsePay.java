package ru.gb.FigurineStore.model.restPay;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.gb.FigurineStore.model.restPay.enums.CodeResponsePayment;

/**
 * Класс с ответом от сервера с оплатой
 */
@Data
@AllArgsConstructor
public class ResponsePay {
    private String status;
    private CodeResponsePayment codeResponsePayment;
}
