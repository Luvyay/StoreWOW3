package ru.gb.Buy.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.Buy.DTO.DTOPayProduct;
import ru.gb.Buy.model.ResponsePay;
import ru.gb.Buy.model.UserBank;
import ru.gb.Buy.service.MethodPayment;
import ru.gb.Buy.service.UserBankService;

@RestController
@AllArgsConstructor
public class PayController {
    private MethodPayment methodPayment;
    private UserBankService userBankService;

    /**
     * Метод по совершению оплаты
     * @param dtoPyaProduct - передаваемый объект содержащий нужную информацию для оплаты
     * @return
     */
    @PostMapping(value = "/api/pay", consumes = {"*/*"})
    public ResponseEntity<ResponsePay> payProduct(@RequestBody DTOPayProduct dtoPyaProduct) {
        return ResponseEntity.status(HttpStatus.OK).body(methodPayment.pay(dtoPyaProduct));
    }

    /**
     * Метод по добавлению нового пользователя в банк
     * @param userBank
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<UserBank> createUserBank(@RequestBody UserBank userBank) {
        return new ResponseEntity<>(userBankService.createUserBank(userBank), HttpStatus.CREATED);
    }

    /**
     * Метод по получению информации о пользователе в банке по id
     * @param id
     * @return
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<UserBank> getUserBankById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(userBankService.getUserBankById(id), HttpStatus.OK);
    }
}
