package ru.gb.FigurineStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.FigurineStore.model.web.BoughtProduct;

@Repository
public interface BoughtProductRepository extends JpaRepository<BoughtProduct, Long> {
}
