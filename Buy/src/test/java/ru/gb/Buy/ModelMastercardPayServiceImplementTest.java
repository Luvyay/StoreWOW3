package ru.gb.Buy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gb.Buy.DTO.DTOPayProduct;
import ru.gb.Buy.model.UserBank;
import ru.gb.Buy.model.enums.BankMethodPayment;
import ru.gb.Buy.service.UserBankService;
import ru.gb.Buy.service.impl.MastercardPayServiceImplement;

import java.math.BigDecimal;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ModelMastercardPayServiceImplementTest {
    @Mock
    private UserBankService userBankService;
    @InjectMocks
    private MastercardPayServiceImplement methodPayment;

    @Test
    public void checkPay() {
        UserBank userBank = new UserBank();
        userBank.setId(1L);
        userBank.setIdUser(1L);
        userBank.setBankMethodPayment(BankMethodPayment.MASTERCARD);
        userBank.setAmount(new BigDecimal(3000));

        DTOPayProduct dtoPayProduct = new DTOPayProduct();
        dtoPayProduct.setIdUser(1L);
        dtoPayProduct.setAmount(new BigDecimal(2000));
        dtoPayProduct.setBankMethodPayment("MASTERCARD");

        given(userBankService.getUserBankByUserIdAndByMethodPayment(userBank.getIdUser(),
                userBank.getBankMethodPayment())).willReturn(userBank);

        methodPayment.pay(dtoPayProduct);

        Assertions.assertEquals(new BigDecimal(1000), userBank.getAmount());
    }
}
