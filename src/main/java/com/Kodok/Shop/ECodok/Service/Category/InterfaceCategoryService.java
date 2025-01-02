package com.Kodok.Shop.ECodok.Service.Category;

import com.Kodok.Shop.ECodok.Model.Category;

import java.util.List;

public interface InterfaceCategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category, Long id);
    void deleteCategoryById(Long id);
}
