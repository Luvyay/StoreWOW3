package ru.gb.Buy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.gb.Buy.model.enums.CodeResponsePayment;

@Data
@AllArgsConstructor
public class ResponsePay {
    private String status;
    private CodeResponsePayment codeResponsePayment;
}
