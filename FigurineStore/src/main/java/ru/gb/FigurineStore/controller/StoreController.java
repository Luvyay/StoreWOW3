package ru.gb.FigurineStore.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb.FigurineStore.model.restPay.DTO.DTOPayProduct;
import ru.gb.FigurineStore.model.restPay.ResponsePay;
import ru.gb.FigurineStore.model.restPay.enums.BankMethodPayment;
import ru.gb.FigurineStore.model.restPay.enums.CodeResponsePayment;
import ru.gb.FigurineStore.model.web.BoughtProduct;
import ru.gb.FigurineStore.model.web.InfoPageBoughtProducts;
import ru.gb.FigurineStore.model.web.InfoPageProducts;
import ru.gb.FigurineStore.model.web.Product;
import ru.gb.FigurineStore.proxy.PaymentProxy;
import ru.gb.FigurineStore.service.security.UserService;
import ru.gb.FigurineStore.service.web.BoughtProductService;
import ru.gb.FigurineStore.service.web.ProductService;

import java.math.BigDecimal;
import java.util.List;

@Controller
@AllArgsConstructor
public class StoreController {
    private ProductService productService;
    private BoughtProductService boughtProductService;
    private final PaymentProxy paymentProxy;
    private UserService userService;

    /**
     * Метод по отображению главной страницы.
     * Главная страница содержит список товаров
     * @param model
     * @return
     */
    @GetMapping("/")
    public String getHomePageWithProducts(Model model) {
        InfoPageProducts infoPageProducts = productService.getPage();

        model.addAttribute("products", infoPageProducts.getProductsInPage());
        model.addAttribute("prev", infoPageProducts.getPrevPage());
        model.addAttribute("next", infoPageProducts.getNextPage());

        return "home";
    }

    /**
     * Метод по отображению конкретной страницы с товарами
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/page/{id}")
    public String getPageWithProducts(Model model, @PathVariable(name = "id") int id) {
        InfoPageProducts infoPageProducts = productService.getPageById(id);

        model.addAttribute("products", infoPageProducts.getProductsInPage());
        model.addAttribute("prev", infoPageProducts.getPrevPage());
        model.addAttribute("next", infoPageProducts.getNextPage());

        return "home";
    }

    /**
     * Метод по отображению страницы с конкретным товаром (по id)
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/product/{id}")
    public String getProductPageById(Model model, @PathVariable(name = "id") Long id) {

        model.addAttribute("product", productService.getProductById(id));

        return "product";
    }

    /**
     * Метод для совершения оплаты
     * @param idProduct
     * @return - перенаправление на страницу со статусом оплаты
     */
    @GetMapping("/buy")
    public String payProduct(@RequestParam() Long idProduct) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        Long idUser = userService.findUserByName(userDetails.getUsername()).getId();

        BigDecimal priceProduct = new BigDecimal(productService.getProductById(idProduct).getPrice());
        String methodPayment = BankMethodPayment.MASTERCARD.name();

        ResponsePay responsePay = paymentProxy.payProduct(new DTOPayProduct(idUser, priceProduct, methodPayment));


        // Если код ответа от сервера с оплатой OK200, то уменьшаем количество товара
        // и добавляем в таблицу с купленными товарами для конкретного пользователя
        if (responsePay.getCodeResponsePayment() == CodeResponsePayment.OK200) {
            productService.decreaseQuantityById(idProduct);

            boughtProductService.addBoughtProducts(new BoughtProduct(idUser, idProduct));
        }

        return "redirect:/pay?status=" + responsePay.getCodeResponsePayment().name() + "&idProduct=" + idProduct;
//        return "redirect:/pay?status=test&idProduct=1";
    }

    /**
     * Метод для отображения статуса оплаты
     * @param status - может быть: OK200 (успешно), ERROR1 (Недостаточно денежных средств)
     *               и ERROR2 (Не найден пользователь с таким платежом)
     * @param idProduct
     * @param model
     * @return
     */
    @GetMapping("/pay")
    public String getStatusPageOfPay(@RequestParam() String status, @RequestParam() Long idProduct, Model model) {

        model.addAttribute("status", status);
        model.addAttribute("idProduct", idProduct);

        return "status-page";
    }

    /**
     * Метод для отображения профиля пользователя
     * @return - перенаправляет на профиль с 1 страницей купленных товаров
     */
    @GetMapping("/profile")
    public String getProfileWithBoughtProducts() {
        return "redirect:/profile/1";
    }

    /**
     * Мето для отображения профиля со списком купленных товаров
     * Купленные товары предоставляются постранично
     * @param model
     * @param id - id страницы с купленными товарами
     * @return
     */
    @GetMapping("/profile/{idPage}")
    public String getPageWithBoughtProducts(Model model, @PathVariable(name = "idPage") int id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        model.addAttribute("username", userDetails.getUsername());

        Long idUser = userService.findUserByName(userDetails.getUsername()).getId();

        // Получение информации по страницам купленных товаров
        InfoPageBoughtProducts infoPageBoughtProducts = boughtProductService.getPageById(id, idUser);

        // infoPageBoughtProducts.getIdBoughtProductsInPage() предоставляет список с id товаров
        // и необходимо это перевести в список самих товаров
        List<Product> boughtProductsInPage = infoPageBoughtProducts.getIdBoughtProductsInPage().stream()
                        .map(idProduct -> productService.getProductById(idProduct))
                                .toList();

        model.addAttribute("products", boughtProductsInPage);
        model.addAttribute("prev", infoPageBoughtProducts.getPrevPage());
        model.addAttribute("next", infoPageBoughtProducts.getNextPage());

        return "profile";
    }
}
