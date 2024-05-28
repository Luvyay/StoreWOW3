package ru.gb.FigurineStore.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.gb.FigurineStore.model.web.Product;
import ru.gb.FigurineStore.service.web.ProductService;

@Controller
@AllArgsConstructor
public class AdminController {
    private ProductService productService;

    /**
     * Метод по отображению страницы для администратора с формой для добавления нового товара
     * @param product
     * @return
     */
    @GetMapping("/admin-panel")
    public String userList(Product product) {

        return "admin-panel";
    }

    /**
     * Метод по добавлению товара с дальнейшей переадресацией на страницу /admin-panel
     * @param product
     * @return
     */
    @PostMapping("/admin-panel")
    public String addProduct(Product product) {
        productService.createProduct(product);

        return "redirect:/admin-panel";
    }
}
