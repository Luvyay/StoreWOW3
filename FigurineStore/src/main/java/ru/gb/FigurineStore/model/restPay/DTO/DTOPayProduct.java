package ru.gb.FigurineStore.model.restPay.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Класс для передачи данных насервер по оплате товаров
 * idUser - id пользователя совершающего покупку
 * amount - стоимость товара
 * bankMethodPayment - метод по совершению оплаты
 */
@Data
@AllArgsConstructor
public class DTOPayProduct {
    private Long idUser;
    private BigDecimal amount;
    private String bankMethodPayment;
}
