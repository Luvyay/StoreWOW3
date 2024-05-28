package ru.gb.FigurineStore.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.gb.FigurineStore.model.restPay.DTO.DTOPayProduct;
import ru.gb.FigurineStore.model.restPay.ResponsePay;

@FeignClient(name = "payment", url = "http://localhost:8765")
public interface PaymentProxy {
    @PostMapping("/api/pay")
    ResponsePay payProduct(@RequestBody DTOPayProduct dtoPayProduct);
}
