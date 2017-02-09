package com.ocodetesttask.storage.xml;

import android.content.Context;

import com.ocodetesttask.model.Product;
import com.ocodetesttask.storage.Storage;

import java.util.List;

/**
 * Created by Сергей on 13.07.2015.
 * stub
 */
public class XMLStorage implements Storage {

    private Context context;

    public XMLStorage(Context context) {
        this.context = context;
    }

    @Override
    public List<Product> loadProducts() {
        return null;
    }

    @Override
    public void saveProducts(List<Product> products) {

    }

    @Override
    public void saveProduct(Product product) {

    }
}
