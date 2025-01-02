package com.Kodok.Shop.ECodok.Repository;
import com.Kodok.Shop.ECodok.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryName(String category);
    List<Product> findByBrandName(String brand);
    List<Product> findBrandAndCategoryName(String brand, String category);
    List<Product> findProductByName(String name);
    List<Product> findProductByBrandAndName(String brand, String name);
    Long countRemmantsProductByBrandAndName(String brand, String name);
}
