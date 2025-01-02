package com.Kodok.Shop.ECodok.Repository;

import com.Kodok.Shop.ECodok.Model.Category;
import com.Kodok.Shop.ECodok.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
    Boolean existByName(String name);
}
