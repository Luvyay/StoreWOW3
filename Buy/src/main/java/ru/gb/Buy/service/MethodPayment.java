package ru.gb.Buy.service;

import org.springframework.transaction.annotation.Transactional;
import ru.gb.Buy.DTO.DTOPayProduct;
import ru.gb.Buy.model.ResponsePay;

public interface MethodPayment {
    @Transactional
    ResponsePay pay(DTOPayProduct dtoPyaProduct);
}
