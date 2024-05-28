package ru.gb.Buy.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.Buy.DTO.DTOPayProduct;
import ru.gb.Buy.model.ResponsePay;
import ru.gb.Buy.model.UserBank;
import ru.gb.Buy.model.enums.BankMethodPayment;
import ru.gb.Buy.model.enums.CodeResponsePayment;
import ru.gb.Buy.service.MethodPayment;
import ru.gb.Buy.service.UserBankService;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class MastercardPayServiceImplement implements MethodPayment {
    private UserBankService userBankService;

    /**
     * Метод по совершению оплаты
     * @param dtoPyaProduct - объект содержащий информацию необходимую для оплаты
     * @return
     */
    @Override
    public ResponsePay pay(DTOPayProduct dtoPyaProduct) {

        // Находим пользователя в импровизированном банке по id пользователя и способе оплаты
        UserBank userBank = userBankService.getUserBankByUserIdAndByMethodPayment(dtoPyaProduct.getIdUser(),
                BankMethodPayment.valueOf(dtoPyaProduct.getBankMethodPayment()));

        // Если не нашли пользователя, то возвращаем ошибку ERROR2
        if (userBank == null) return new ResponsePay("Error", CodeResponsePayment.ERROR2);

        // Если у пользователя хватает средств для оплаты товара, то совершаем оплату
        // иначе возвращаем ошибку ERROR2
        if (userBank.getAmount().subtract(dtoPyaProduct.getAmount()).compareTo(new BigDecimal(0)) >= 0 ) {
            BigDecimal newAmount = userBank.getAmount().subtract(dtoPyaProduct.getAmount());
            userBank.setAmount(newAmount);

            return new ResponsePay("Ok", CodeResponsePayment.OK200);
        } else {
            return new ResponsePay("Error", CodeResponsePayment.ERROR1);
        }
    }
}
