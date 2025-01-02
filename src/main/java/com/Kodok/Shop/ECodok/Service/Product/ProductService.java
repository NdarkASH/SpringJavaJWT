package com.Kodok.Shop.ECodok.Service.Product;


import com.Kodok.Shop.ECodok.Exception.ProductNotFoundException;
import com.Kodok.Shop.ECodok.Model.Category;
import com.Kodok.Shop.ECodok.Model.Product;
import com.Kodok.Shop.ECodok.Repository.CategoryRepository;
import com.Kodok.Shop.ECodok.Repository.ProductRepository;
import com.Kodok.Shop.ECodok.Request.AddProductRequest;
import com.Kodok.Shop.ECodok.Request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements InterfaceProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    /*
            konsepnya
            pertama pas datanya masuk kode ini bakal ngecek apakah ada category di database
            jika ada maka dia bakal ngesave di kategori yang ada
            jika ga ada maka dia bakal bikin kategori baru
            dan masukin datanya ke kategori barunya itu
         */
    @Override
    public Product addProduct(AddProductRequest request) {
       Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
               .orElseGet(()-> {
                   Category newCategory = new Category(request.getCategory().getName());
                  return categoryRepository.save(newCategory);
               });
       request.setCategory(category);
       return productRepository.save(createProduct(request, category));
    };
    private Product createProduct(AddProductRequest request, Category category){
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

//Mencari product melalui idnya
    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product not found!!!"));
    }
//menghapus product melalui idnya
    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete,
                ()-> {throw new ProductNotFoundException("Product not found!!!");});
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productRepository :: save).orElseThrow(()-> new ProductNotFoundException("Product not found!!!"));
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setName(request.getName());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }
//Melihat semua produk
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
//Mencari product melalui colom brand pada tabel kategori di model
    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrandName(brand);
    }
//Mencari category yang sesuai dengan yang diminta
    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }
//mencari produk melalui category dan brand E.G(menampilkan semua produk xiaomi yang memiliki kategori eletronik)
    @Override
    public List<Product> getProductsByBrandAndCategory(String brand, String category) {
        return productRepository.findBrandAndCategoryName(category, brand);
    }
//Dapetin product melalui nama
    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findProductByName(name);
    }
//Dapetin product melalui nama product dan dari brand apa
    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findProductByBrandAndName(brand, name);
    }
//Menghitung sisa produk yang ada di warehouse
    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countRemmantsProductByBrandAndName(brand, name);
    }
}
