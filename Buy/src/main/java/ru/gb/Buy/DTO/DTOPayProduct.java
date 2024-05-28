package ru.gb.Buy.DTO;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Класс с передаваемой информацией для совершения оплаты
 */
@Data
public class DTOPayProduct {
    private Long idUser;
    private BigDecimal amount;
    private String bankMethodPayment;
}
