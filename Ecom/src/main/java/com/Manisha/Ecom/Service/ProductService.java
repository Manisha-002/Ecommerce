package com.Manisha.Ecom.Service;

import com.Manisha.Ecom.model.Product;
import com.Manisha.Ecom.repo.Productrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private Productrepo productrepo;
    public List<Product> getAllProducts() {
        return productrepo.findAll();
    }
}
