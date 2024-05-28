package ru.gb.FigurineStore;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.gb.FigurineStore.model.web.Product;
import ru.gb.FigurineStore.repository.ProductRepository;
import ru.gb.FigurineStore.service.web.ProductService;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

@SpringBootTest
public class IntegrationServiceTest {
    @Autowired
    private ProductService productService;
    @MockBean
    private ProductRepository productRepository;

    @Test
    public void checkDecreaseQuantityById() {
        Product product = new Product();
        product.setId(1L);
        product.setName("TestName");
        product.setDescription("Test description");
        product.setUrlPhoto("Test url photo");
        product.setPrice(3000);
        product.setQuantity(5);

        given(productRepository.findById(product.getId())).willReturn(Optional.of(product));


        productService.decreaseQuantityById(1L);

        Assertions.assertEquals(4, product.getQuantity());
    }
}
