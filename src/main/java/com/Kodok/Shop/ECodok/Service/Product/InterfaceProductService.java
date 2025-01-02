package com.Kodok.Shop.ECodok.Service.Product;

import com.Kodok.Shop.ECodok.Model.Product;
import com.Kodok.Shop.ECodok.Request.AddProductRequest;
import com.Kodok.Shop.ECodok.Request.ProductUpdateRequest;

import java.util.List;

public interface InterfaceProductService {


    Product addProduct(AddProductRequest request);
    Product getProductById(Long id);
    void deleteProductById(Long id);

    Product updateProduct(ProductUpdateRequest product, Long productId);

    List<Product> getAllProducts();
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrandAndCategory(String brand, String category);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);
}
