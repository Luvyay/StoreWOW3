package ru.gb.FigurineStore.service.web;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.FigurineStore.aspect.TrackUserAction;
import ru.gb.FigurineStore.model.web.InfoPageProducts;
import ru.gb.FigurineStore.model.web.Product;
import ru.gb.FigurineStore.repository.ProductRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepository productRepository;
    @PersistenceContext
    private EntityManager em;
    // Количество товаров на странице "Главная"
    private static final int countProductInPage = 8;

    /**
     * Метод по получению всех товаров
     * @return
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Метод по получению всех товаров у которых количество (quantity) больше 0
     * @return
     */
    public List<Product> getAllProductsWhereQuantityMoreZero() {
        return productRepository.findAllWhereQuantityMoreZero();
    }

    /**
     * Метод по получению 1 страницы с товарами
     * @return
     */
    public InfoPageProducts getPage() {
        return getPageById(1);
    }

    /**
     * Метод по получению конкретной страницы с товарами (у которых количество (quantity) больше 0)
     * На методе есть аннотация @TrackUserAction. Она выявляет время выполнения и логирует
     * @param id
     * @return
     */
    @TrackUserAction
    public InfoPageProducts getPageById(int id) {
        int countPage = (int) Math.ceil((double) getAllProductsWhereQuantityMoreZero().size() / countProductInPage);

        if (countPage == 0) countPage = 1;

        if (id > countPage) id = countPage;
        if (id < 0) id = 1;

        Integer nextPage = id + 1;

        if (nextPage > countPage) nextPage = null;

        Integer prevPage = id - 1;

        if (prevPage <= 0) prevPage = null;

        int limitValue = countProductInPage;
        int offsetValue = (id - 1) * limitValue;

        List<Product> products = em.createQuery("SELECT u FROM Product u WHERE u.quantity > 0 ORDER BY u.id ASC LIMIT :paramLimit OFFSET :paramOffset",
                Product.class)
                .setParameter("paramLimit", limitValue)
                .setParameter("paramOffset", offsetValue)
                .getResultList();

        return new InfoPageProducts(countPage, prevPage, nextPage, products);
    }

    /**
     * Метод по получению конкретного товара по id
     * На методе есть аннотация @TrackUserAction. Она выявляет время выполнения и логирует
     * @param id
     * @return
     */
    @TrackUserAction
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    /**
     * Метод по созданию нового товара
     * @param product
     * @return
     */
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Метод по обновлению информации о товаре
     * @param product
     * @return
     */
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Метод по удалению товара по id
     * @param id
     */
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    /**
     * Метод по уменьшению количества (quantity) у конкретного товара по id
     * @param id
     */
    public void decreaseQuantityById(Long id) {
        Product findProduct = productRepository.findById(id).orElse(null);

        if (findProduct != null) {
            findProduct.setQuantity(findProduct.getQuantity() - 1);
            updateProduct(findProduct);
        }
    }
}
