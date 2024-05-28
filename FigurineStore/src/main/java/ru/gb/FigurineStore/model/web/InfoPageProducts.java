package ru.gb.FigurineStore.model.web;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class InfoPageProducts {
    private int countPage;
    private Integer prevPage;
    private Integer nextPage;
    private List<Product> productsInPage;
}
