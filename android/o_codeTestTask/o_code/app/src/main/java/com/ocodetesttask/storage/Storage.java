package com.ocodetesttask.storage;

import com.ocodetesttask.model.Product;

import java.util.List;

/**
 * Created by Сергей on 10.07.2015.
 */
public interface Storage {

    List<Product> loadProducts();

    void saveProducts(List<Product> products);

    void saveProduct(Product product);
}
