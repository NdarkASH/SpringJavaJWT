package com.Kodok.Shop.ECodok.Service.Category;

import com.Kodok.Shop.ECodok.Exception.AlreadyExistException;
import com.Kodok.Shop.ECodok.Exception.ResourceNotFoundException;
import com.Kodok.Shop.ECodok.Model.Category;
import com.Kodok.Shop.ECodok.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.channels.AlreadyConnectedException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements InterfaceCategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.ofNullable(category).filter(c -> !categoryRepository.existByName(c.getName()))
                .map(categoryRepository :: save).orElseThrow(() -> new AlreadyExistException(category.getName()+ "already exist"));
    }
    /*
    ini buat update category
    pertama dia ambil map data old kategori
    terus check apakah ada
    jika ada maka ambil data lama terus diambil data lamanya
    dan ditimpa data baru
    jika ga ada maka keluarin exeption
     */
    @Override
    public Category updateCategory(Category category, Long id) {
        return Optional.ofNullable(getCategoryById(id))
                .map(oldCategory -> {
                    oldCategory.setName(category.getName());
                    return categoryRepository.save(oldCategory);
                }).orElseThrow(()-> new ResourceNotFoundException("Category not found"));
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.findById(id).ifPresentOrElse(categoryRepository::delete, ()->{
            throw new ResourceNotFoundException("Category not found");
        });

    }
}
